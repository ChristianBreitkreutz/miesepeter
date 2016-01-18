package com.epages.sonar.miesepeter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.BlockFinder;
import com.epages.sonar.miesepeter.parser.CodeLine;

public class TestBlockFinder {

	@Test
	public void testoneBlockLine() {
		List<CodeLine> incommingList = new ArrayList<>();
		incommingList.add(new CodeLine(3,"before#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOPafter"));

		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<List<CodeLine>> blocks = blockfinder.getBlocks("LOOP");

		List<CodeLine> block1 =  blocks.get(0);
		assertEquals(block1.get(0).lineNumber, 3);
		assertEquals(block1.get(0).text, "#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP");

	}
	@Test
	public void testMultiblocksInLine() {
		List<CodeLine> incommingList = new ArrayList<>();
		incommingList.add(new CodeLine(3,"_before_#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP_after_before_#LOOP(#two)loop2#ENDLOOP_after"));
		
		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<List<CodeLine>> blocks = blockfinder.getBlocks("LOOP");
		
		List<CodeLine> block1 =  blocks.get(0);
		assertEquals(block1.get(0).lineNumber, 3);
		assertEquals("#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP",block1.get(0).text);
		
		List<CodeLine> block2 =  blocks.get(1);
		assertEquals(block2.get(0).lineNumber, 3);
		assertEquals("#LOOP(#two)loop2#ENDLOOP",block2.get(0).text);

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
		List<List<CodeLine>> blocks = blockfinder.getBlocks("LOOP");

		List<CodeLine> block1 =  blocks.get(0);
		assertEquals(block1.get(0).lineNumber, 2);
		assertEquals(block1.get(0).text, "#LOOP(#One) #LOOP(#Two) sogehet #ENDLOOP");
		assertEquals(block1.get(1).lineNumber, 3);
		assertEquals(block1.get(1).text, "#LOOP(#three)");
		assertEquals(block1.get(2).lineNumber, 4);
		assertEquals(block1.get(2).text, "inner content");
		assertEquals(block1.get(3).lineNumber, 5);
		assertEquals(block1.get(3).text, "#ENDLOOP#ENDLOOP");

		List<CodeLine> block2 =  blocks.get(1);
		assertEquals(block2.get(0).lineNumber, 7);
		assertEquals(block2.get(0).text, "#LOOP(#Four)");
		assertEquals(block2.get(1).lineNumber, 8);
		assertEquals(block2.get(1).text, "inner content");
		assertEquals(block2.get(2).lineNumber, 9);
		assertEquals(block2.get(2).text, "#ENDLOOP");
	}

}