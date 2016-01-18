package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;

import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.TleParser;

public class LoopWithSet implements TleParser{

	@Override
	public ArrayList<IssueLine> parse(List<String> linedFile) {
		int lineNumber = 0;
		ArrayList<IssueLine> lineIssues = new ArrayList<>();
		for (String line : linedFile) {
				if (line.matches(".*#LOOP.*")) {
					lineIssues.add(new IssueLine(lineNumber,"kjkj"));
				}
			lineNumber++;
		}
		return lineIssues;
	}

}
