package com.epages.sonar.miesepeter.parser;

import java.io.File;
import java.util.List;

import com.epages.sonar.miesepeter.parser.issues.GenericTle;
import com.epages.sonar.miesepeter.parser.issues.JavaScriptInTemplate;
import com.epages.sonar.miesepeter.parser.issues.LonelySet;
import com.epages.sonar.miesepeter.parser.issues.LoopIssues;


public class Parser {
	public ParseResult parseFile(File file) {
		List<CodeLine> linedFile = FileRunner.loadFile(file);

		ParseResult result = new ParseResult();
		result.setLonelySet( new LonelySet().parse(linedFile) );
		result.setGenericTle(new GenericTle().parse(linedFile));
		result.setLoopIssues(new LoopIssues().parse(linedFile));
		result.setJavascript(new JavaScriptInTemplate().parse(linedFile));
		return result;
	}
}
