package com.epages.sonar.miesepeter.parser;

public class LineIssue {
	public int lineNumber;
	public String type;
	public LineIssue(int lineNumber,String Type) {
		this.lineNumber = lineNumber;
		this.type = Type;
	}
}
