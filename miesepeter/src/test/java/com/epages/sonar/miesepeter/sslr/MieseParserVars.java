package com.epages.sonar.miesepeter.sslr;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;

public class MieseParserVars {

	@Test
	public void test() {
		File file = new File("src/test/resources/TLE/vars.html");
		AstNode astOfFile = MieseParser.parseFile(file);
		List<AstNode> allTles = astOfFile.getChildren(MieseGrammar.TLE);

		AstNode listItemOne = allTles.get(0);
		assertEquals("variable1 is in line ", 1, listItemOne.getTokenLine());
		assertEquals("test token type", MieseGrammar.TLE, listItemOne.getType());
		assertEquals("test token type", MieseGrammar.TLEVar, listItemOne.getFirstChild().getType());

		AstNode listItemTwo = allTles.get(1);
		assertEquals("variable1 is in line ", 3, listItemTwo.getTokenLine());
		assertEquals("test token type", MieseGrammar.TLE, listItemTwo.getType());
		assertEquals("test token type", MieseGrammar.TLEVar, listItemTwo.getFirstChild().getType());
	}

}
