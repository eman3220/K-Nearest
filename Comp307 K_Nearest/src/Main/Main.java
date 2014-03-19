package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.sound.midi.SysexMessage;
import javax.swing.JOptionPane;

public class Main {

	private File training;
	private File test;

	private Classifier irisClassifier;
	
	private ArrayList<Iris> testData;

	public Main(String training, String test) {
		try {
			this.training = new File(training);
			this.test = new File(test);
		} catch (Exception e) {
			System.err.println("Cant read file");
			System.exit(0);
		}
		
		this.irisClassifier = new Classifier(this.training);
		
		readTestData();
		
		for(Iris i : this.testData){
			this.irisClassifier.classify(i);
		}
	}

	private void readTestData() {
		this.testData = new ArrayList<Iris>();
		
		 try {
			BufferedReader br = new BufferedReader(new FileReader(this.test));
			String line;
			while((line=br.readLine())!=null){
				Iris i = parseLine(line);
				this.testData.add(i);
			}
		} catch (Exception e) {
			System.err.println("Failed reading Test file");
		}
	}

	private Iris parseLine(String line) {
		String[] s = line.split("  ");
		Iris i = new Iris(Double.parseDouble(s[0]), Double.parseDouble(s[1]),
				Double.parseDouble(s[2]), Double.parseDouble(s[3]), s[4]);
		return i;
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			JOptionPane
					.showMessageDialog(null,
							"You must provide the training set file and the test set file");
			System.exit(0);
		}
		String s1 = args[0];
		String s2 = args[1];
		new Main(s1, s2);
	}
}
