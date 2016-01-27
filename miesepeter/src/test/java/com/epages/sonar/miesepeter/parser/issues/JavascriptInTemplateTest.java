package com.epages.sonar.miesepeter.parser.issues;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.CodeLine;
import com.epages.sonar.miesepeter.parser.FileRunner;
import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.issues.JavaScriptInTemplate;

public class JavascriptInTemplateTest {

	@Test
	public void loadFile() {
		File file = new File("src/test/resources/JavascriptInTemplate.html");
		List<CodeLine> linedFile = FileRunner.loadFile(file);
		ArrayList<IssueLine> lineIssues = new JavaScriptInTemplate().parse(linedFile);
		
		lineIssues.get(0);
		assertEquals("JavaScript", lineIssues.get(0).type);
		assertEquals(3, lineIssues.get(0).lineNumber);
		lineIssues.get(1);
		assertEquals("JavaScript", lineIssues.get(1).type);
		assertEquals(5, lineIssues.get(1).lineNumber);
	}
}
