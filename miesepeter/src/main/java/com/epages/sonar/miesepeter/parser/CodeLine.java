package com.epages.sonar.miesepeter.parser;

public class CodeLine {
	public int lineNumber;
	public String text;
	public CodeLine(Integer lineNumber, String text) {
		this.lineNumber = lineNumber;
		this.text = text;
	}
}
