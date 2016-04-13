package com.epages.sonar.miesepeter.sslr;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.sonar.sslr.api.AstNode;

public class MieseParserTest {

	@Test
	public void test() {
		final String filePath = "src/test/resources/MieseParserTest.py";
		
		
		File file = new File(filePath);
		
		final AstNode parser = MieseParser.parseFile(file);
		List<AstNode> childen = parser.getChildren();
		System.out.println(childen.toString());
//		assertThat(parser);
		return;
	}

}
