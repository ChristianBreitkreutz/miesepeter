package com.epages.sonar.miesepeter.parser;

import java.util.List;

public interface TleParser {
	List<IssueLine> parse(List<CodeLine> linedFile);
}