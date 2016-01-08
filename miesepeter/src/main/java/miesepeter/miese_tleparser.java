package miesepeter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;


public class miese_tleparser {
	public ArrayList<Integer> parseFile(File file) {
		System.out.println(file.getName());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			int lineNumber = 1;
			ArrayList<Integer> output = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				if (line.matches(".*#IF.*")) {
					output.add(lineNumber);
				}
				lineNumber++;
			}
			reader.close();
			return output;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", file.getName());
			e.printStackTrace();
		}
		return null;
	}
}
