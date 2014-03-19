package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Classifier {

	private ArrayList<Iris> training;

	public Classifier(File file) {
		readFile(file);
	}

	private void readFile(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				parseLine(line);
			}
		} catch (Exception e) {
			System.err.println("Failed to read training file");
			System.exit(0);
		}
	}

	/**
	 * Reads line, constructs iris, adds to training set
	 * 
	 * @param line
	 */
	private void parseLine(String line) {
		String[] s = line.split("  ");
		Iris i = new Iris(Double.parseDouble(s[0]), Double.parseDouble(s[1]),
				Double.parseDouble(s[2]), Double.parseDouble(s[3]), s[4]);
		this.training.add(i);
	}
	
	public String classify(Iris i){
		String type = "";
		
		
		
		return type;
	}
}
