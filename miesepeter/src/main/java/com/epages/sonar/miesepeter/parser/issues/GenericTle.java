package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;

import com.epages.sonar.miesepeter.parser.CodeLine;
import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.TleParser;

public class GenericTle implements TleParser{

	@Override
	public ArrayList<IssueLine> parse(List<CodeLine> linedFile) {
		String[] tleList = {"#IF", "#ELSIF", "#ELSE", "#LOCAL", "#SET", "#BLOCK", "#WITH", "#WITH_ERROR", "#FUNCTION", "#MENU", "#CALCULATE", "#PROGRESS", "#REM", "OR", "AND"};
		ArrayList<IssueLine> lineIssues = new ArrayList<>();
		for (CodeLine line : linedFile) {
			for (String tle : tleList) {
				if (line.getText().matches(".*"+tle+".*")) {
					lineIssues.add(new IssueLine(line.getLineNumber(),tle));
				}
			}
		}
		return lineIssues;
	}

}
