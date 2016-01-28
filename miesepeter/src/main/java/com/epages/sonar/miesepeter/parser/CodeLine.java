package com.epages.sonar.miesepeter.parser;

import static com.google.common.base.Preconditions.checkNotNull;

public class CodeLine {

	private final int lineNumber;

	private final String text;

	public CodeLine(Integer lineNumber, String text) {
		this.lineNumber = checkNotNull(lineNumber);
		this.text = checkNotNull(text);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getText() {
		return text;
	}
}
