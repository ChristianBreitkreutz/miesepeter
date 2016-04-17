package com.epages.sonar.miesepeter.sslr;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;

public class ConditionTest {

	@Test
	public void test_conditionBoolVar() {
		File file = new File("src/test/resources/TLE/condition.html");
		AstNode astOfFile = MieseParser.parseFile(file);
		List<AstNode> allConditions = astOfFile.getChildren(MieseGrammar.TLECondition);
		AstNode firstCondtion = allConditions.get(0);

		AstNode conditionalExpression = firstCondtion.getFirstDescendant(MieseGrammar.EXPRESSION);
		AstNode firstElementInExpression = conditionalExpression.getFirstDescendant(MieseGrammar.TLEVar);
		assertEquals( //
				"test if the ast found the correct var", //
				"BoolVar", //
				getNameOfVar(firstElementInExpression) //
		);
		assertEquals( //
				"test if the ast found the correct var in the coorrect line", //
				1, //
				getLineNumberOfVar(firstElementInExpression) //
		);

		AstNode firstBodyElement = firstCondtion.getChildren(MieseGrammar.TLE).get(0);

		assertEquals("line number first body element", 2, getLineNumberOfVar(firstBodyElement));
		assertEquals("test first var name", "InnerVar1", getNameOfVar(firstBodyElement));

	}

	private String getNameOfVar(AstNode varElement) {
		AstNode identifiereOfVar = getIdentifierOfVar(varElement);
		return identifiereOfVar.getTokenValue();
	}

	private int getLineNumberOfVar(AstNode varElement) {
		AstNode identifiereOfVar = getIdentifierOfVar(varElement);
		return identifiereOfVar.getTokenLine();
	}

	private AstNode getIdentifierOfVar(AstNode varElement) {
		return varElement.getFirstDescendant(GenericTokenType.IDENTIFIER);
	}
}
