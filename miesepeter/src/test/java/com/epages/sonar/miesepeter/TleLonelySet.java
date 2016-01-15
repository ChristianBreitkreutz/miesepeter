package com.epages.sonar.miesepeter;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.LineIssue;
import com.epages.sonar.miesepeter.parser.Parser;

public class TleLonelySet {

	@Test
	public void test() {
		File file = new File("src/test/resources/LonelySet.html");
		Parser parser = new Parser();
		ArrayList<LineIssue> lineIssues = parser.parseFile(file);
		assertEquals("amount of issues,", 1, lineIssues.size());
		assertEquals("issue type" ,"LonelySet", lineIssues.get(0).type);
		assertEquals("line number" ,3, lineIssues.get(0).lineNumber);
	}

}
