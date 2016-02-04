package com.epages.sonar.miesepeter.parser;

public class IssueLine {
	private final int lineNumber;
	private final String type;

	public IssueLine(int lineNumber, String type) {
		this.lineNumber = lineNumber;
		this.type = type;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getType() {
		return type;
	}

}
