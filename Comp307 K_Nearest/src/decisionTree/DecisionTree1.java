package decisionTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DecisionTree1 {

	public static void main(String[] args) {
		new DecisionTree1(args);
	}

	// Fields
	private File trainingFile;
	private File testFile;

	private HashMap<String, Integer> classes;
	private HashMap<String, Integer> attributes;

	private ArrayList<Instance> instances;

	private Node root;
	
	private String majorityClass;

	public DecisionTree1(String[] args) {
		this.trainingFile = new File(args[0]);
		this.testFile = new File(args[1]);

		this.classes = new HashMap<String, Integer>();
		this.attributes = new HashMap<String, Integer>();
		this.instances = new ArrayList<Instance>();

		readTraining();
		
		// find majority class
		findMajorityClass();

		beginBuildTree();

		readTest();
		
		System.out.println("\n\n===== BUILDING TREE =====\n\n");
		this.root.printNode("");
		System.out.println("\n\n===== FINISHED TREE =====\n\n");
	}

	private void findMajorityClass() {
		int liveCount = 0;
		int dieCount = 0;
		for(Instance i : this.instances){
			if(i.getClassType().equals("live")){
				liveCount++;
			}else{
				dieCount++;
			}
		}
		if(liveCount<dieCount){
			this.majorityClass = "die";
		}else{
			this.majorityClass = "live";
		}
	}

	private void readTest() {
		System.out.println("Reading test data from file "
				+ this.testFile.getAbsolutePath());
		ArrayList<Instance> testInstances = new ArrayList<Instance>();

		try {
			BufferedReader br = new BufferedReader(
					new FileReader(this.testFile));
			br.readLine();// class line
			br.readLine();// attr line

			String line;
			while ((line = br.readLine()) != null) {
				parseInstance(line, testInstances);
			}

			System.out.println("Read " + testInstances.size() + " instances");

			classifyInstances(testInstances);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void classifyInstances(ArrayList<Instance> testInstances) {
		int liveTotal = 0;
		int dieTotal = 0;
		
		int liveCorrect = 0;
		int dieCorrect = 0;
		
		for(Instance test : testInstances){
			String currentClassType = test.getClassType();
			
			if(test.getClassType().equals("live")){
				liveTotal++;
			}else{
				dieTotal++;
			}
			
			// i want to know what it has been classified as 
			String classifiedAs = this.root.classify(test,this.attributes);
			//System.out.println(classifiedAs);
			
			if(currentClassType.equals(classifiedAs)){
				if(currentClassType.equals("live")){
					liveCorrect++;
				}else{
					dieCorrect++;
				}
			}
		}
		
		System.out.println("live: "+liveCorrect+" correct out of "+liveTotal);
		System.out.println("die: "+dieCorrect+" correct out of "+dieTotal);
		
		int correct = liveCorrect+dieCorrect;
		int total = liveTotal+dieTotal;
		float accuracy = ((float)correct / total) * 100;
		System.out.println("\nAccuracy:");
		System.out.println("Decision Tree Accuracy: "+accuracy+"%");
		
		// find baseline accuracy
		//this.majorityClass;
		int baseLineCorrect = 0;
		int baseLineTotal = 0;
		
		for(Instance inst : this.instances){
			if(inst.getClassType().equals(this.majorityClass)){
				baseLineCorrect++;
			}
			baseLineTotal++;
		}
		float baselineAccuracy = ((float)baseLineCorrect/baseLineTotal) * 100;
		System.out.println("Baseline Accuracy ("+this.majorityClass+") : "+baselineAccuracy+"%");
	}

	private void parseInstance(String line, ArrayList<Instance> testInstances) {
		Scanner scan = new Scanner(line);
		String classType = scan.next();
		ArrayList<String> instAttributes = new ArrayList<String>();
		while (scan.hasNext()) {
			instAttributes.add(scan.next());
		}
		testInstances.add(new Instance(classType, instAttributes));
	}

	private void beginBuildTree() {
		ArrayList<String> attr = new ArrayList<String>();
		for (String s : this.attributes.keySet()) {
			attr.add(s);
		}
		this.root = buildTree(this.instances, attr);
	}

	private Node buildTree(ArrayList<Instance> instances, ArrayList<String> attr) {
		// if instances is empty
		// return a leaf node containing the name and probability of the overall
		// most probable class
		if (instances.isEmpty()) {
			return new Node("live", null, null, true, 0.5f);
		}

		// if instances are pure
		// return a leaf node containing the name of the class of the instances
		// in the node and probability 1
		String pure = isPure(instances);
		if (!pure.equals("not pure")) {
			return new Node(pure, null, null, true, 1f);
		}

		// if attributes is empty
		// return a leaf node containing the name and probability of the
		// majority class of the instances in the node (choose randomly if
		// classes are equal)
		if (attr.isEmpty()) {
			Wrapper_ClassType_Majority m = getMajority(instances);
			return new Node(m.getClassType(), null, null, true, m.getMajority());
		}

		// else find the best attribute
		String bestAttr = "";
		ArrayList<Instance> bestTrues = new ArrayList<Instance>();
		ArrayList<Instance> bestFalses = new ArrayList<Instance>(); 
		float bestPurity = Float.MAX_VALUE;
		
		// for each attribute
		for (String attribute : attr) {
			// separate instances into two sets
			// - instances where attribute is true
			// - instances where attribute is false
			ArrayList<Instance> trues = new ArrayList<Instance>();
			ArrayList<Instance> falses = new ArrayList<Instance>();
			
			int attrNum = this.attributes.get(attribute);
			for(Instance i : instances){
				String T_Or_F = i.getValues().get(attrNum);
				if(T_Or_F.equals("true")){
					trues.add(i);
				}else{
					falses.add(i);
				}
			}
			
			// compute purity of each set
			float purity = calcWeightedImpurity(trues,falses);
			// if weighted average purity of these sets is best so far
			// - set bestAttr
			// - set bestTrueInstances
			// - set bestFalseInstances
			if(purity<bestPurity){
				bestAttr = attribute;
				bestTrues = trues;
				bestFalses = falses;
				bestPurity = purity;
			}
		}
		
		// build left branch
		ArrayList<String> newLeftAttributes = new ArrayList<String>();
		for(String at : attr){
			if(at.equals(bestAttr))continue;
			newLeftAttributes.add(at);
		}
		Node left = buildTree(bestTrues,newLeftAttributes);
		
		// build right branch
		ArrayList<String> newRightAttributes = new ArrayList<String>();
		for(String at : attr){
			if(at.equals(bestAttr))continue;
			newRightAttributes.add(at);
		}
		Node right = buildTree(bestFalses,newRightAttributes);
		
		// return new node with left and right branch
		return new Node(bestAttr, left, right, false, 1.0f - bestPurity);
	}

	/**
	 * Uses the weighted impurities calculation in slide 10-12 in lecture 6
	 * 
	 * @param groupOne
	 * @param groupTwo
	 * @return
	 */
	private float calcWeightedImpurity(ArrayList<Instance> groupOne,
			ArrayList<Instance> groupTwo) {

		// get node fractions
		int totSize = groupOne.size() + groupTwo.size();
		// System.out.println(groupOne.size()+" "+totSize);
		float f1 = ((float) groupOne.size()) / totSize;
		float f2 = ((float) groupTwo.size()) / totSize;

		// get first subnode fractions
		HashMap<String, Integer> classToCountOne = new HashMap<String, Integer>();

		for (Instance als : groupOne) {
			String s = als.getClassType();
			if (!classToCountOne.containsKey(s)) {
				classToCountOne.put(s, 1);
			}
			if (classToCountOne.containsKey(s)) {
				int i = classToCountOne.get(s);
				i++;
				classToCountOne.remove(s);
				classToCountOne.put(s, i);
			}
		}
		float sn1 = 1;
		float totSizeOne = 0;
		for (Integer i : classToCountOne.values()) {
			totSizeOne += i;
		}
		for (Map.Entry<String, Integer> e : classToCountOne.entrySet()) {
			float temp = ((float) e.getValue()) / totSizeOne;
			sn1 = ((float) sn1 * temp);
		}

		// get second subnode fractions
		HashMap<String, Integer> classToCountTwo = new HashMap<String, Integer>();

		for (Instance als : groupTwo) {
			String s = als.getClassType();
			if (!classToCountTwo.containsKey(s)) {
				classToCountTwo.put(s, 1);
			}
			if (classToCountTwo.containsKey(s)) {
				int i = classToCountTwo.get(s);
				i++;
				classToCountTwo.remove(s);
				classToCountTwo.put(s, i);
			}
		}
		float sn2 = 1;
		float totSizeTwo = 0;
		for (Integer i : classToCountTwo.values()) {
			totSizeTwo += i;
		}
		for (Map.Entry<String, Integer> e : classToCountTwo.entrySet()) {
			float temp = ((float) e.getValue()) / totSizeTwo;
			sn2 = ((float) sn2 * temp);
		}

		//System.out.println("(" + f1 + " * " + sn1 + ")  +  (" + f1 + " * "
			//	+ sn2 + ")");
		return (f1 * sn1) + (f2 * sn2);
	}

	private Wrapper_ClassType_Majority getMajority(
			ArrayList<Instance> allInstances) {
		HashMap<String, Float> majority = new HashMap<String, Float>();
		int total = allInstances.size();
		for (Instance i : allInstances) {
			String s = i.getClassType();
			if (!majority.containsKey(s)) {
				majority.put(s, 1f);
			}
			if (majority.containsKey(s)) {
				float f = majority.get(s);
				f++;
				majority.remove(s);
				majority.put(s, f);
			}
		}

		String classMajority = "";
		float biggestMajority = 0f;
		// assume there is only two
		HashMap<String, Float> newMajority = new HashMap<String, Float>();
		// divide each value in map by total
		for (Map.Entry<String, Float> e : majority.entrySet()) {
			String s = e.getKey();
			float f = e.getValue() / total;
			System.out.println(s + " " + f);

			// this should be alright...
			newMajority.put(s, f);

			if (biggestMajority < f) {
				biggestMajority = f;
				classMajority = s;
			}
		}

		// return majority of people going to live
		return new Wrapper_ClassType_Majority(classMajority, biggestMajority);
	}

	private String isPure(ArrayList<Instance> inst) {
		String previous = "";
		String current = "";
		for (Instance als : inst) {
			current = als.getClassType();
			if (!current.equals(previous) && !previous.equals("")) {
				return "not pure";
			}
			previous = als.getClassType();
		}
		return current;
	}

	private void readTraining() {
		System.out.println("Reading training data from file "
				+ this.trainingFile.getAbsolutePath());
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					this.trainingFile));
			parseClass(br.readLine());
			System.out.println(this.classes.size() + " classes");

			parseAttribute(br.readLine());
			System.out.println(this.attributes.size() + " attributes");

			String line;
			while ((line = br.readLine()) != null) {
				parseInstance(line);
			}

			System.out.println(this.instances.size() + " instances");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseInstance(String line) {
		Scanner scan = new Scanner(line);
		// int count = 0;
		String classType = scan.next();
		ArrayList<String> instAttributes = new ArrayList<String>();
		while (scan.hasNext()) {
			instAttributes.add(scan.next());
		}
		this.instances.add(new Instance(classType, instAttributes));
	}

	private void parseAttribute(String readLine) {
		Scanner scan = new Scanner(readLine);
		int count = 0;
		while (scan.hasNext()) {
			this.attributes.put(scan.next(), count++);
		}
	}

	private void parseClass(String readLine) {
		Scanner scan = new Scanner(readLine);
		int count = 0;
		while (scan.hasNext()) {
			this.classes.put(scan.next(), count++);
		}
	}
}
