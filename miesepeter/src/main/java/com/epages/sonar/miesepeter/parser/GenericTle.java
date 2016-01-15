package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;

public class GenericTle implements TleParser{

	@Override
	public ArrayList<LineIssue> parse(List<String> linedFile) {
		String[] tleList = {"IF", "ELSIF", "ELSE", "LOCAL", "SET", "BLOCK", "WITH", "WITH_ERROR", "FUNCTION", "MENU", "CALCULATE", "PROGRESS", "REM", "OR", "AND"};
		int lineNumber = 0;
		ArrayList<LineIssue> lineIssues = new ArrayList<>();
		for (String line : linedFile) {
			for (String tle : tleList) {
				if (line.matches(".*#"+tle+".*")) {
					lineIssues.add(new LineIssue(lineNumber,tle));
				}
			}
			lineNumber++;
		}
		return lineIssues;
	}

}
