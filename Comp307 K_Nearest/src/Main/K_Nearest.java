package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.SysexMessage;
import javax.swing.JOptionPane;

public class K_Nearest {

	private File training;
	private File test;

	private Classifier irisClassifier;
	
	private ArrayList<Iris> testData;

	public K_Nearest(String training, String test) {
		try {
			this.training = new File(training);
			this.test = new File(test);
		} catch (Exception e) {
			System.err.println("Cant read file");
			System.exit(0);
		}
		
		// remove these later
		//this.training = new File("part1/iris-training.txt");
		//this.test = new File("part1/iris-test.txt");
		
		this.irisClassifier = new Classifier(this.training);
		
		readTestData();
		
		float correct = 0f;
		float total = 0f;
		
		for(Iris i : this.testData){
			String s = this.irisClassifier.classify(i);
			System.out.println(i.getType()+" - Classified as "+s);
			if(s.equals(i.getType())){
				correct++;
			}
			total++;
		}
		System.out.println("-------");
		System.out.println(correct+"/"+total);
		
		// end
	}

	private void readTestData() {
		this.testData = new ArrayList<Iris>();
		
		 try {
			BufferedReader br = new BufferedReader(new FileReader(this.test));
			String line;
			while((line=br.readLine())!=null){
				Iris i = parseLine(line);
				if(i!=null){
					this.testData.add(i);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Failed reading Test file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Iris parseLine(String line) {
		String[] s = line.split("  ");
		if (s.length != 5) {
			//System.err.println("Line is not parsable... skipping line...");
			return null;
		}
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
		new K_Nearest(s1, s2);
	}
}
