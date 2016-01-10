package miesepeter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;


public class miese_tleparser {
	private static String[] tleList = {"IF", "ELSIF", "ELSE", "LOCAL", "SET", "BLOCK", "WITH", "WITH_ERROR", "FUNCTION", "MENU", "CALCULATE", "PROGRESS", "REM"};
	public ArrayList<miese_tleDTO> parseFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			int lineNumber = 1;
			ArrayList<miese_tleDTO> output = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				for (String tle : tleList) {
					if (line.matches(".*#"+tle+".*")) {
						miese_tleDTO dto = new miese_tleDTO();
						dto.setLineNumber(lineNumber);
						dto.setTleType(tle);
						output.add(dto);
					}
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
