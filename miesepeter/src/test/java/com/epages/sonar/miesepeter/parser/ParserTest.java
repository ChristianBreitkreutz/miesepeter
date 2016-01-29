package com.epages.sonar.miesepeter.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.ParseResult;
import com.epages.sonar.miesepeter.parser.Parser;

public class ParserTest {

	@Test
	public void loadFile() {
		File file = new File("src/test/resources/dummyTLE.html");
		Parser parser = new Parser();
		ParseResult result = parser.parseFile(file);
		List<IssueLine> lineIssues = result.getGenericTle();
		lineIssues.get(0);
		assertEquals("#IF", lineIssues.get(0).getType());
		assertEquals(1, lineIssues.get(0).getLineNumber());

		assertEquals("#LOCAL", lineIssues.get(1).getType());
		assertEquals(5, lineIssues.get(1).getLineNumber()); // local in line 5

		assertEquals("#IF", lineIssues.get(2).getType());
		assertEquals(6, lineIssues.get(2).getLineNumber()); // if in line 6
	}

	@Test
	public void testNonExistantFile() {
		File file = new File("non_existant_file.html");
		Parser parser = new Parser();
		ParseResult result = parser.parseFile(file);
		List<IssueLine> lineIssues = result.getGenericTle();
		assertNotNull(lineIssues);
	}

}
