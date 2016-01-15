package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;

public interface TleParser {
	ArrayList<LineIssue> parse(List<String> linedFile);
}