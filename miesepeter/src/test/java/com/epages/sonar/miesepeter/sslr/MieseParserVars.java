package com.epages.sonar.miesepeter.sslr;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.sonar.sslr.api.AstNode;

public class MieseParserVars {

	@Test
	public void test() {
		File file = new File("src/test/resources/TLE/vars.html");
		AstNode astOfFile = MieseParser.parseFile(file);
		List<AstNode> allVars = astOfFile.getChildren(MieseGrammar.TLEVar);

		AstNode listItemOne = allVars.get(0);
		assertEquals("variable VARIABLE is in line ", 1, AstTools.getLineNumberOfVar(listItemOne));
		assertEquals("test var name", AstTools.getNameOfVar(listItemOne), "VARIABLE");

		AstNode listItemTwo = allVars.get(1);
		assertEquals("variable VARIABLE1 is in line ", 3, AstTools.getLineNumberOfVar(listItemTwo));
		assertEquals("test var name", AstTools.getNameOfVar(listItemTwo), "VARIABLE1");

		AstNode listItemThree = allVars.get(2);
		assertEquals("variable MODIFIER is in line ", 4, AstTools.getLineNumberOfVar(listItemThree));
		assertEquals("test var name", AstTools.getNameOfVar(listItemThree), "MODIFIER");
		assertEquals("test var modifierName", AstTools.getModifierOfVar(listItemThree), "Money");
		
		//MODIFIER_NUMBER
		AstNode listItemFour = allVars.get(3);
		assertEquals("variable MODIFIER is in line ", 5, AstTools.getLineNumberOfVar(listItemFour));
		assertEquals("test var name", AstTools.getNameOfVar(listItemFour), "MODIFIER_NUMBER");
		assertEquals("test var modifierName", AstTools.getModifierOfVar(listItemFour), "689");
		
	}

}
