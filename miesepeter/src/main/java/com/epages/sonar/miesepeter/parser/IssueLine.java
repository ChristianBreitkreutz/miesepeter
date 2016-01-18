package com.epages.sonar.miesepeter.parser;

public class IssueLine {
	public int lineNumber;
	public String type;
	public IssueLine(int lineNumber,String Type) {
		this.lineNumber = lineNumber;
		this.type = Type;
	}
}
