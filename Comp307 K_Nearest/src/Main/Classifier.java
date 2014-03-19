package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Classifier {

	private ArrayList<Iris> trainingSet;

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

		for (Iris trainingIris : this.trainingSet) {
			// get distance from each iris in training set
			double d = calculateDistance(trainingIris, testIris);
			System.out.println(d);
		}
		return type;
	}

	private double calculateDistance(Iris trainingIris, Iris testIris) {
		double sumOf = 0;
		for (int i = 0; i < 4; i++) {
			double a = trainingIris.getData().get(i);
			double b = testIris.getData().get(i);
			double r = i+1;
			double numerator = Math.pow((a-b), 2);
			double denominator = Math.pow(r, 2);
			sumOf = sumOf + (numerator/denominator);
		}
		return Math.sqrt(sumOf);
	}
}
