package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;

public interface TleParser {
	ArrayList<IssueLine> parse(List<CodeLine> linedFile);
}