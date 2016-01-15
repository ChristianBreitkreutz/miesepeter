package com.epages.sonar.miesepeter.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRunner {

	public static List<String> loadFile (File file) {
		BufferedReader reader;
		List<String> fileRunner = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			fileRunner.add(file.getName());
			while ((line = reader.readLine()) != null) {
				fileRunner.add(line);
			}
			reader.close();
		} catch (IOException e) {
			// TODO logging
			e.printStackTrace();
			return null;
		}
		return fileRunner;
	}
}
