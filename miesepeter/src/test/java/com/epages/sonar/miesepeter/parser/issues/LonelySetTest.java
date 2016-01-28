package com.epages.sonar.miesepeter.parser.issues;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.ParseResult;
import com.epages.sonar.miesepeter.parser.Parser;

public class LonelySetTest {

	@Test
	public void test() {
		File file = new File("src/test/resources/LonelySet.html");
		Parser parser = new Parser();

		ParseResult result = parser.parseFile(file);

		List<IssueLine> lineIssues = result.getLonelySet();
		assertEquals("amount of issues,", 1, lineIssues.size());
		assertEquals("issue type" ,"LonelySet", lineIssues.get(0).type);
		assertEquals("line number" ,3, lineIssues.get(0).lineNumber);
	}

}
