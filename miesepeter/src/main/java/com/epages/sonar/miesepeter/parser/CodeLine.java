package com.epages.sonar.miesepeter.parser;

public class CodeLine {

	private final int lineNumber;

	private final String text;

	public CodeLine(Integer lineNumber, String text) {
		this.lineNumber = lineNumber;
		this.text = text;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getText() {
		return text;
	}
}
