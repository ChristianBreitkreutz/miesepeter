package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;

import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.TleParser;

public class GenericTle implements TleParser{

	@Override
	public ArrayList<IssueLine> parse(List<String> linedFile) {
		String[] tleList = {"IF", "ELSIF", "ELSE", "LOCAL", "SET", "BLOCK", "WITH", "WITH_ERROR", "FUNCTION", "MENU", "CALCULATE", "PROGRESS", "REM", "OR", "AND"};
		int lineNumber = 0;
		ArrayList<IssueLine> lineIssues = new ArrayList<>();
		for (String line : linedFile) {
			for (String tle : tleList) {
				if (line.matches(".*#"+tle+".*")) {
					lineIssues.add(new IssueLine(lineNumber,tle));
				}
			}
			lineNumber++;
		}
		return lineIssues;
	}

}
