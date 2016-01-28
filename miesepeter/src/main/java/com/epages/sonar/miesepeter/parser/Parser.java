package com.epages.sonar.miesepeter.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epages.sonar.miesepeter.parser.issues.GenericTle;
import com.epages.sonar.miesepeter.parser.issues.JavaScriptInTemplate;
import com.epages.sonar.miesepeter.parser.issues.LonelySet;
import com.epages.sonar.miesepeter.parser.issues.LoopIssues;


public class Parser {

	private static final Logger log = LoggerFactory.getLogger(Parser.class);

	public ParseResult parseFile(File file) {
		try {
			log.info("parsing file {}", file);
			List<CodeLine> linedFile = FileRunner.loadFile(file);
			return parseCodeLines(linedFile);
		} catch (IOException e) {
			log.error("cannot parse file {}", file, e);
		}
		log.warn("returning empty parse result for file {}", file);
		return new ParseResult();
	}

	private ParseResult parseCodeLines(List<CodeLine> linedFile) {
		ParseResult result = new ParseResult();
		result.setLonelySet( new LonelySet().parse(linedFile) );
		result.setGenericTle(new GenericTle().parse(linedFile));
		result.setLoopIssues(new LoopIssues().parse(linedFile));
		result.setJavascript(new JavaScriptInTemplate().parse(linedFile));
		return result;
	}
}
