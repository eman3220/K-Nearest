package decisionTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class DecisionTree {

	private File trainingFile;
	private File testFile;

	private ArrayList<String> classes; // always two
	private ArrayList<String> attributes; // more than one
	private ArrayList<ArrayList<String>> trainingData;
	private ArrayList<ArrayList<String>> testData;

	public DecisionTree(String training, String test) {
		this.trainingFile = new File(training);
		this.testFile = new File(test);
		
		this.classes = new ArrayList<String>();
		this.attributes = new ArrayList<String>();
		
		this.trainingData = new ArrayList<ArrayList<String>>();
		this.testData = new ArrayList<ArrayList<String>>();

		readTraining();
		buildClassifier()
		
		readTest();
	}

	private void readTest() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(trainingFile));
			String line;
			line = br.readLine();
			String[] s1 = line.split("\t");

			line = br.readLine();
			String[] s2 = line.split("\t");

			while ((line = br.readLine()) != null) {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readTraining() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(trainingFile));
			String line;
			line = br.readLine();// classes
			String[] s1 = line.split("\t");
			for (String s : s1) {
				this.classes.add(s);
			}

			line = br.readLine();// attributes
			String[] s2 = line.split("\t");
			for (String s : s1) {
				this.attributes.add(s);
			}
			int count = 0;
			while ((line = br.readLine()) != null) {
				// data
				this.trainingData.add(parseLine(line));
				count++;
			}
			
			System.out.println(count+"=110");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<String> parseLine(String line) {
		String[] sa = line.split("\t");
		ArrayList<String> list = new ArrayList<String>();
		
		// the first element in list is the class
		for(String s : sa){
			list.add(s);
		}
		return list;
	}

	public static void main(String[] args) {
		String training = args[0];
		String test = args[1];

		new DecisionTree(training, test);
	}
}
