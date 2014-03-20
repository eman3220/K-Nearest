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
	private int k = 1;

	public Classifier(File file) {
		this.trainingSet = new ArrayList<Iris>();
		readFile(file);
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
			System.err.println("Line is not parsable... skipping line...");
			return;
		}
		Iris i = new Iris(Double.parseDouble(s[0]), Double.parseDouble(s[1]),
				Double.parseDouble(s[2]), Double.parseDouble(s[3]), s[4]);
		this.trainingSet.add(i);
	}

	public String classify(Iris testIris) {
		String type = "";
		double d = Double.MAX_VALUE;
		// double d = calculateDistance(this.trainingSet.get(0), testIris);
		// System.out.println(d);
		PriorityQueue<IrisDistance> pq = new PriorityQueue<IrisDistance>(75,
				new DistanceComparator());

		for (Iris trainingIris : this.trainingSet) {
			// get distance from each iris in training set
			double distance = calculateDistance(trainingIris, testIris);

			// add to the priority queue, sorts the distance by small to large
			pq.add(new IrisDistance(distance, trainingIris));
		}
		
		//System.out.println("---");
		int setosa = 0;
		int versicolor = 0;
		int virginica = 0;
		for(int i=0;i<k;i++){
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
			double r = i + 1;
			double numerator = Math.pow((a - b), 2);
			double denominator = Math.pow(r, 2);
			sumOf = sumOf + (numerator / denominator);
		}
		return Math.sqrt(sumOf);
	}
}
