package com.epages.sonar.miesepeter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.TleLine;
import com.epages.sonar.miesepeter.parser.ParseResult;
import com.epages.sonar.miesepeter.parser.Parser;

public class test_tleparser {

	@Test
	public void loadFile() {
		File file = new File("src/test/resources/dummyTLE.html");
		Parser parser = new Parser();
		ParseResult result = parser.parseFile(file);
		ArrayList<TleLine> lineIssues = result.getGenericTle();
		lineIssues.get(0);
		assertEquals("IF", lineIssues.get(0).type);
		assertEquals(1, lineIssues.get(0).lineNumber);

		assertEquals("LOCAL", lineIssues.get(1).type);
		assertEquals(5, lineIssues.get(1).lineNumber); // local in line 5

		assertEquals("IF", lineIssues.get(2).type);
		assertEquals(6, lineIssues.get(2).lineNumber); // if in line 6
	}

}
