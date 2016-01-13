package com.epages.sonar.miesepeter.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;


public class Parser {
	private static String[] tleList = {"IF", "ELSIF", "ELSE", "LOCAL", "SET", "BLOCK", "WITH", "WITH_ERROR", "FUNCTION", "MENU", "CALCULATE", "PROGRESS", "REM"};
	public ArrayList<TleLine> parseFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			int lineNumber = 1;
			ArrayList<TleLine> output = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				for (String tle : tleList) {
					if (line.matches(".*#"+tle+".*")) {
						TleLine dto = new TleLine();
						dto.setLineNumber(lineNumber);
						dto.setType(tle);
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
