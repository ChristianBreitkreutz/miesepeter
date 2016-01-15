package com.epages.sonar.miesepeter.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Parser {
	public ParseResult parseFile(File file) {
		List<String> linedFile = FileRunner.loadFile(file);

		ParseResult result = new ParseResult();
		result.setLonelySet( new LonelySet().parse(linedFile) );
		result.setGenericTle(new GenericTle().parse(linedFile));

		return result;
	}
}
