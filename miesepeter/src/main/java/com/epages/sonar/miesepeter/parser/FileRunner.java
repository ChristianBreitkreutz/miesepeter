package com.epages.sonar.miesepeter.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRunner {

	public static List<CodeLine> loadFile (File file) throws IOException {
		List<CodeLine> codeLines = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			int lineNumber = 1;
			while ((line = reader.readLine()) != null) {
				codeLines.add(new CodeLine(lineNumber, line));
				lineNumber++;
			}
		}
		return codeLines;
	}
}
