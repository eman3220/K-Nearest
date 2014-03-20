package clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class K_Means {
	
	public K_Means(String filepath) {
		File file = new File(filepath);
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line=br.readLine())!=null){
				System.out.println(line);
				count++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[]args){
		if(args.length!=1){
			System.exit(0);
		}
		new K_Means(args[0]);
	}
}
