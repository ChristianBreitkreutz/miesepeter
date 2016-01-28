package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;

import com.epages.sonar.miesepeter.parser.CodeLine;
import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.TleParser;

public class JavaScriptInTemplate implements TleParser{

	@Override
	public List<IssueLine> parse(List<CodeLine> linedFile) {
		ArrayList<IssueLine> lineIssues = new ArrayList<>();
		for (CodeLine line : linedFile) {
			if (line.getText().matches(".*<script\\s*type=\"text/javascript\".*")) {
					lineIssues.add(new IssueLine(line.getLineNumber(),"JavaScript"));
			}
		}
		return lineIssues;
	}

}
