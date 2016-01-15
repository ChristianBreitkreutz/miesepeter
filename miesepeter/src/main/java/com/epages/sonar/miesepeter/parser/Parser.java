package com.epages.sonar.miesepeter.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.RegExp;


public class Parser {
	public ArrayList<LineIssue> parseFile(File file) {
		List<String> linedFile = FileRunner.loadFile(file);

//		ArrayList<LineIssue> lala = parseKeyWords(linedFile);
		ArrayList<LineIssue> lala = parseForLonelySet(linedFile);

		return lala;
	}
	private ArrayList<LineIssue> parseKeyWords (List<String> linedFile){
		String[] tleList = {"IF", "ELSIF", "ELSE", "LOCAL", "SET", "BLOCK", "WITH", "WITH_ERROR", "FUNCTION", "MENU", "CALCULATE", "PROGRESS", "REM", "NEQ" , "EQ", "OR", "AND", "DEFINED"};
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

	private ArrayList<LineIssue> parseForLonelySet(List<String> linedFile){
		int lineNumber = 0;
		ArrayList<LineIssue> lineIssues = new ArrayList<>();
				Pattern patternSET = Pattern.compile(
						".*#SET"
						+ Regex.OPENPARENTHESIS
						+ Regex.UNLIMITEDWHITESPACE
						+ Regex.DOUBLEQUOTE
						+ "(" // open group parenthesis
							+ "[^" + Regex.DOUBLEQUOTE +"|.]*"// [^"|.]* all symbols except a double quote
						+ ")" // end group parenthesis
						+ Regex.DOUBLEQUOTE
						+ ".*"
				);
				for (String line : linedFile) {
				Matcher matcher = patternSET.matcher(line);
				if (matcher.find()) {
					if (!hasDefinitionInSameFile(lineNumber,matcher.group(1),linedFile)) {
						lineIssues.add(new LineIssue(lineNumber,"LonelySet"));
					}
				}
			lineNumber++;
		}
		return lineIssues;
	}
	private boolean hasDefinitionInSameFile (int startingLineNumber, String varName, List<String> linedFile){
		boolean hasDefinition = false;
		Pattern patternLOCALE = Pattern.compile(
				".*#LOCALE"
				+ Regex.OPENPARENTHESIS
				+ Regex.UNLIMITEDWHITESPACE
				+ Regex.DOUBLEQUOTE
				+ varName
				+ Regex.DOUBLEQUOTE
				+ ".*"
		);
		Matcher matcher;
		int firstLine = 1;// line 0 contains the filename
		for (int i = startingLineNumber; i>=firstLine; i--) {
			matcher = patternLOCALE.matcher(linedFile.get(i));
			if (matcher.matches()) {
				hasDefinition = true;
				break;
			}
		}
		return hasDefinition;
		
	}
}
