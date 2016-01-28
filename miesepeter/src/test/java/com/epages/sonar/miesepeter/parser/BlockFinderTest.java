
package com.epages.sonar.miesepeter.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.BlockFinder;
import com.epages.sonar.miesepeter.parser.CodeBlock;
import com.epages.sonar.miesepeter.parser.CodeLine;

public class BlockFinderTest {

	@Test
	public void testoneBlockLine() {
		List<CodeLine> incommingList = new ArrayList<>();
		incommingList.add(new CodeLine(3,"before#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOPafter"));

		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<CodeBlock> blocks = blockfinder.getCodeBlocks("LOOP");

		CodeBlock block1 = blocks.get(0);
		assertEquals("get from codeblock the lineNumber of element 1" ,3, block1.getBlock().get(0).getLineNumber());
		assertEquals("#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP", block1.getBlock().get(0).getText());
		assertEquals("Complexity for a codeblock with 2 elements", 2, block1.getComplexity());
		assertEquals("lines of code in codeblock", 1, block1.getLenght());

	}
	@Test
	public void testMultiblocksInLine() {
		List<CodeLine> incommingList = new ArrayList<>();
		incommingList.add(new CodeLine(3,"_before_#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP_after_before_#LOOP(#two)loop2#ENDLOOP_after"));
		
		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<CodeBlock> blocks = blockfinder.getCodeBlocks("LOOP");

		CodeBlock block1 =  blocks.get(0);
		assertEquals("get from codeblock the lineNumber of element 1" , 3, block1.getBlock().get(0).getLineNumber());
		assertEquals("#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP",block1.getBlock().get(0).getText());
		assertEquals("Complexity for a codeblock with 2 elements", 2, block1.getComplexity());
		assertEquals("lines of code in codeblock", 1, block1.getLenght());

		CodeBlock block2 =  blocks.get(1);
		assertEquals("get from codeblock the lineNumber of element 1" ,3, block2.getBlock().get(0).getLineNumber());
		assertEquals("#LOOP(#two)loop2#ENDLOOP",block2.getBlock().get(0).getText());
		assertEquals("Complexity for a codeblock with 1 elements", 1, block2.getComplexity());
		assertEquals("lines of code in codeblock", 1, block2.getLenght());

	}
	@Test
	public void testMultiblocks() {

		List<CodeLine> incommingList = new ArrayList<>();
		incommingList.add(new CodeLine(1,"a extra line"));
		incommingList.add(new CodeLine(2,"before #LOOP(#One) #LOOP(#Two) sogehet #ENDLOOP"));
		incommingList.add(new CodeLine(3,"#LOOP(#three)"));
		incommingList.add(new CodeLine(4,"inner content"));
		incommingList.add(new CodeLine(5,"#ENDLOOP#ENDLOOP after"));
		incommingList.add(new CodeLine(6,"a second extra line"));
		incommingList.add(new CodeLine(7,"blabla#LOOP(#Four)"));
		incommingList.add(new CodeLine(8,"inner content"));
		incommingList.add(new CodeLine(9,"#ENDLOOPblabla"));
		incommingList.add(new CodeLine(10,"a trird extra line"));

		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<CodeBlock> blocks = blockfinder.getCodeBlocks("LOOP");

		CodeBlock codeblock =  blocks.get(0);
		List<CodeLine> block1 = codeblock.getBlock();
		assertEquals("get from codeblock1 the lineNumber of element 1" , 2, block1.get(0).getLineNumber());
		assertEquals("get from codeblock1 the text of element 1", "#LOOP(#One) #LOOP(#Two) sogehet #ENDLOOP", block1.get(0).getText());
		assertEquals("get from codeblock1 the lineNumber of element 2", 3, block1.get(1).getLineNumber());
		assertEquals("get from codeblock1 the text of element 2", "#LOOP(#three)", block1.get(1).getText());
		assertEquals("get from codeblock1 the lineNumber of element 3", 4, block1.get(2).getLineNumber());
		assertEquals("get from codeblock1 the text of element 3", "inner content", block1.get(2).getText());
		assertEquals("get from codeblock1 the lineNumber of element 4", 5, block1.get(3).getLineNumber());
		assertEquals("get from codeblock1 the text of element 4", "#ENDLOOP#ENDLOOP", block1.get(3).getText());
		assertEquals("Complexity for a codeblock with 3 elements", 3, codeblock.getComplexity());
		assertEquals("lines of code in codeblock",4, codeblock.getLenght());
		
		CodeBlock codeblock2 =  blocks.get(1);
		List<CodeLine> block2 = codeblock2.getBlock();
		assertEquals("get from codeblock2 the lineNumber of element 1", 7, block2.get(0).getLineNumber());
		assertEquals("get from codeblock2 the text of element 1", "#LOOP(#Four)", block2.get(0).getText());
		assertEquals("get from codeblock2 the lineNumber of element 2", 8, block2.get(1).getLineNumber());
		assertEquals("get from codeblock2 the text of element 2", "inner content", block2.get(1).getText());
		assertEquals("get from codeblock2 the lineNumber of element 3", 9, block2.get(2).getLineNumber());
		assertEquals("get from codeblock2 the text of element 3", "#ENDLOOP", block2.get(2).getText());
		assertEquals("Complexity for a codeblock with 3 elements", 1, codeblock2.getComplexity());
		assertEquals("lines of code in codeblock", 3, codeblock2.getLenght());
	}

}