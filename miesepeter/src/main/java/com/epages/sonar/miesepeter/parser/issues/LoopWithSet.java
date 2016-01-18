package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;

import com.epages.sonar.miesepeter.parser.TleLine;
import com.epages.sonar.miesepeter.parser.TleParser;

public class LoopWithSet implements TleParser{

	@Override
	public ArrayList<TleLine> parse(List<String> linedFile) {
		int lineNumber = 0;
		ArrayList<TleLine> lineIssues = new ArrayList<>();
		for (String line : linedFile) {
				if (line.matches(".*#LOOP.*")) {
					lineIssues.add(new TleLine(lineNumber,"kjkj"));
				}
			lineNumber++;
		}
		return lineIssues;
	}

}
