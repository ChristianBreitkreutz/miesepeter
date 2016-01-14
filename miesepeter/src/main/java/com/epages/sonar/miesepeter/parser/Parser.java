package com.epages.sonar.miesepeter.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
	public ArrayList<LineIssue> parseFile(File file) {
		FileRunner fileRunner = new FileRunner();
		fileRunner.loadFile(file);
		List<String> linedFile = fileRunner.getFileRunner();

		ArrayList<LineIssue> lala = parseKeyWords(linedFile);

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
		for (String line : linedFile) {
//    #SET("Delimiter", 1)
//#LOCAL("Delimiter", 0)
			if (line.matches(".*#SET.*")) {
				Pattern pattern = Pattern.compile("'(.*?)'");
				Matcher matcher = pattern.matcher(line);
				String varname = matcher.group(0);
				boolean foundVarDefinition = false;
				int innerLineNumber = 0;
				for (String innerLine : linedFile) {
					if(innerLineNumber > lineNumber){
						break;
					}
					if (innerLine.matches(".*#LOCALE(\""+varname+"\".*") ) {
						foundVarDefinition = true;
					}
					innerLineNumber++;
				}
				if (! foundVarDefinition){
					lineIssues.add(new LineIssue(lineNumber,"LonelySet"));
					
				}
			}
			lineNumber++;
		}
		return lineIssues;
	}
}
