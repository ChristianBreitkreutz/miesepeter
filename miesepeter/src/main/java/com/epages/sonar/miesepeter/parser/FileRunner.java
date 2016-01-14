package com.epages.sonar.miesepeter.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRunner {
	private ArrayList<String> fileRunner;

	public boolean loadFile (File file) {
		BufferedReader reader;
		fileRunner = new ArrayList<>();
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
			return false;
		}
		return true;
	}

	public List<String> getFileRunner (){
		return fileRunner;
	}
}
