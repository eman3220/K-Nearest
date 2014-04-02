package perceptron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PerceptronMain {
	public static void main(String[] args) {
		new PerceptronMain(args[0]);
	}

	private File imageFile;
	private ArrayList<Image> features;
	private int featureCount = 0;

	public PerceptronMain(String filepath) {
		this.imageFile = new File(filepath);
		this.features = new ArrayList<Image>();
		readFile();

	}

	private void readFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					this.imageFile));
			String line;
			while ((line = br.readLine())!=null) {
				// p1
				line = br.readLine();// comment

				line = br.readLine();// row col
				Scanner scan = new Scanner(line);
				int row = Integer.parseInt(scan.next());
				int col = Integer.parseInt(scan.next());

				int[] ints = new int[row * col];
				int count = 0;

				line = br.readLine();
				String[] sa = line.split("");
				for (String s : sa) {
					if (s.equals(""))
						continue;
					ints[count++] = Integer.parseInt(s);
				}

				line = br.readLine();
				sa = line.split("");
				for (String s : sa) {
					if (s.equals(""))
						continue;
					ints[count++] = Integer.parseInt(s);
				}

				this.features.add(new Image(row, col, ints));
				featureCount++;
			}
			///System.out.println(featureCount);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
