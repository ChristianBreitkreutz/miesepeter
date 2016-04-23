package com.epages.sonar.miesepeter.sslr;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;

public class AstTools {

	public static String getNameOfVar(AstNode varElement) {
		AstNode identifiereOfVar = getIdentifierOfVar(varElement);
		return identifiereOfVar.getTokenValue();
	}

	public static int getLineNumberOfVar(AstNode varElement) {
		AstNode identifiereOfVar = getIdentifierOfVar(varElement);
		return identifiereOfVar.getTokenLine();
	}
	public static String getFormaterOfVar(AstNode varElement) {
		AstNode modifierOfVar = varElement.getFirstDescendant(MieseGrammar.MODIFIER);
		AstNode formater = varElement.getFirstDescendant(MieseGrammar.FORMATER);
		return formater.getTokenValue().toString();
	}

	private static AstNode getIdentifierOfVar(AstNode varElement) {
		return varElement.getFirstDescendant(GenericTokenType.IDENTIFIER);
	}
}
