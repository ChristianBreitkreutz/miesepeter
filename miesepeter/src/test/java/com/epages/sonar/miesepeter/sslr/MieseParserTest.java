package com.epages.sonar.miesepeter.sslr;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

public class MieseParserTest {

	@Test
	public void test() {
		final String filePath = "src/test/resources/MieseParserTest.py";
		
		
		File file = new File(filePath);
		
		final AstNode AstOfFile = MieseParser.parseFile(file);
//		AstOfFile.
		AstNodeType var = MieseGrammar.TLEs;
		List<AstNode> childrenTree = AstOfFile.getChildren(var);

		System.out.println(childrenTree.size());

		for (AstNode astNode : childrenTree) {
			System.out.println(astNode.getTokenValue());
			lukeChild(astNode.getChildren(MieseGrammar.TLECondtion));
			System.out.println(astNode.getTokenLine());
		}
//		System.out.println(parser.getChildren(var).toString());
//		System.out.println(parser.toString());
//		System.out.println(parser.getTokenLine());
		System.out.println(AstOfFile.getType());
//		assertThat(parser);
		return;
	}

	private void lukeChild(List<AstNode> astnode ) {
		for (AstNode astNode2 : astnode) {
			System.out.println(astNode2.getChildren().toString());
		}
		
	}
	
}

