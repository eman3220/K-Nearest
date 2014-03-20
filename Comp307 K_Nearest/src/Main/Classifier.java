package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Classifier {

	private ArrayList<Iris> trainingSet;
	private int k = 3;

	private double maxSL = 0;
	private double minSL = Double.MAX_VALUE;

	private double maxSW = 0;
	private double minSW = Double.MAX_VALUE;

	private double maxPL = 0;
	private double minPL = Double.MAX_VALUE;

	private double maxPW = 0;
	private double minPW = Double.MAX_VALUE;

	private double[] ranges = new double[4];

	public Classifier(File file) {
		this.trainingSet = new ArrayList<Iris>();
		readFile(file);
		
		// we have max and mins, work out range
		double slRange = maxSL - minSL;
		double swRange = maxSW - minSW;
		double plRange = maxPL - minPL;
		double pwRange = maxPW - minPW;
		
		ranges[0] = slRange;
		ranges[1] = swRange;
		ranges[2] = plRange;
		ranges[3] = pwRange;
		
		System.out.println("Sepal Length Range: "+ranges[0]);
		System.out.println("Sepal Width Range: "+ranges[1]);
		System.out.println("Petal Length Range: "+ranges[2]);
		System.out.println("Petal Width Range: "+ranges[3]);
		System.out.println("K = "+k);
	}

	private void readFile(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				parseLine(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Failed to read training file");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Reads line, constructs iris, adds to training set
	 * 
	 * @param line
	 */
	private void parseLine(String line) {
		String[] s = line.split("  ");
		if (s.length != 5) {
			//System.err.println("Line is not parsable... skipping line...");
			return;
		}
		double sl = Double.parseDouble(s[0]);
		double sw = Double.parseDouble(s[1]);
		double pl = Double.parseDouble(s[2]);
		double pw = Double.parseDouble(s[3]);
		
		checkSepalRange(sl,sw);
		checkPetalRange(pl,pw);
		
		Iris i = new Iris(sl, sw, pl, pw, s[4]);
		this.trainingSet.add(i);
	}

	private void checkSepalRange(double sl, double sw) {
		if(sl>this.maxSL){
			this.maxSL = sl;
		}
		if(sl<this.minSL){
			this.minSL = sl;
		}
		
		if(sw>this.maxSW){
			this.maxSW = sw;
		}
		if(sw<this.minSW){
			this.minSW = sw;
		}
	}
	
	private void checkPetalRange(double pl, double pw) {
		if(pl>this.maxPL){
			this.maxPL = pl;
		}
		if(pl<this.minPL){
			this.minPL = pl;
		}
		
		if(pw>this.maxPW){
			this.maxPW = pw;
		}
		if(pw<this.minPW){
			this.minPW = pw;
		}
	}

	/**
	 * Use priority queue to order training irises by distance from test iris
	 * Poll from priority queue "k" times
	 * 
	 * @param testIris
	 * @return
	 */
	public String classify(Iris testIris) {
		PriorityQueue<IrisDistance> pq = new PriorityQueue<IrisDistance>(75,
				new DistanceComparator());

		for (Iris trainingIris : this.trainingSet) {
			// get distance from each iris in training set
			double distance = calculateDistance(trainingIris, testIris);

			// add to the priority queue, sorts the distance by small to large
			pq.add(new IrisDistance(distance, trainingIris));
		}

		int setosa = 0;
		int versicolor = 0;
		int virginica = 0;
		for (int i = 0; i < k; i++) {
			Iris iris = pq.poll().getIris();
			switch (iris.getType()) {
			case "Iris-setosa":
				setosa++;
				break;
			case "Iris-versicolor":
				versicolor++;
				break;
			case "Iris-virginica":
				virginica++;
				break;
			}
		}
		if (setosa > versicolor && setosa > virginica) {
			return "Iris-setosa";
		} else if (versicolor > setosa && versicolor > virginica) {
			return "Iris-versicolor";
		} else if (virginica > setosa && virginica > versicolor) {
			return "Iris-virginica";
		} else {
			return "I don't know";
		}
	}

	private double calculateDistance(Iris trainingIris, Iris testIris) {
		double sumOf = 0;
		for (int i = 0; i < 4; i++) {
			double a = trainingIris.getData().get(i);
			double b = testIris.getData().get(i);
			double r = this.ranges[i];
			double numerator = Math.pow((a - b), 2);
			double denominator = Math.pow(r, 2);
			sumOf = sumOf + (numerator / denominator);
		}
		return Math.sqrt(sumOf);
	}
}
