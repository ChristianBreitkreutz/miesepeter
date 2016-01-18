package com.epages.sonar.miesepeter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.epages.sonar.miesepeter.parser.BlockFinder;
import com.epages.sonar.miesepeter.parser.TleLine;

public class TestBlockFinder {

	@Test
	public void testoneBlockLine() {
		List<TleLine> incommingList = new ArrayList<>();
		incommingList.add(new TleLine(3,"before#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOPafter"));

		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<List<TleLine>> blocks = blockfinder.getBlocks("LOOP");

		List<TleLine> block1 =  blocks.get(0);
		assertEquals(block1.get(0).lineNumber, 3);
		assertEquals(block1.get(0).type, "#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP");

	}
	@Test
	public void testMultiblocksInLine() {
		List<TleLine> incommingList = new ArrayList<>();
		incommingList.add(new TleLine(3,"_before_#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP_after_before_#LOOP(#two)loop2#ENDLOOP_after"));
		
		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<List<TleLine>> blocks = blockfinder.getBlocks("LOOP");
		
		List<TleLine> block1 =  blocks.get(0);
		assertEquals(block1.get(0).lineNumber, 3);
		assertEquals("#LOOP(#one)#LOOP(#inner)loop1#ENDLOOP#ENDLOOP",block1.get(0).type);
		
		List<TleLine> block2 =  blocks.get(1);
		assertEquals(block2.get(0).lineNumber, 3);
		assertEquals("#LOOP(#two)loop2#ENDLOOP",block2.get(0).type);

	}
	@Test
	public void testMultiblocks() {

		List<TleLine> incommingList = new ArrayList<>();
		incommingList.add(new TleLine(1,"a extra line"));
		incommingList.add(new TleLine(2,"before #LOOP(#One) #LOOP(#Two) sogehet #ENDLOOP"));
		incommingList.add(new TleLine(3,"#LOOP(#three)"));
		incommingList.add(new TleLine(4,"inner content"));
		incommingList.add(new TleLine(5,"#ENDLOOP#ENDLOOP after"));
		incommingList.add(new TleLine(6,"a second extra line"));
		incommingList.add(new TleLine(7,"blabla#LOOP(#Four)"));
		incommingList.add(new TleLine(8,"inner content"));
		incommingList.add(new TleLine(9,"#ENDLOOPblabla"));
		incommingList.add(new TleLine(10,"a trird extra line"));

		BlockFinder blockfinder = new BlockFinder(incommingList);
		List<List<TleLine>> blocks = blockfinder.getBlocks("LOOP");

		List<TleLine> block1 =  blocks.get(0);
		assertEquals(block1.get(0).lineNumber, 2);
		assertEquals(block1.get(0).type, "#LOOP(#One) #LOOP(#Two) sogehet #ENDLOOP");
		assertEquals(block1.get(1).lineNumber, 3);
		assertEquals(block1.get(1).type, "#LOOP(#three)");
		assertEquals(block1.get(2).lineNumber, 4);
		assertEquals(block1.get(2).type, "inner content");
		assertEquals(block1.get(3).lineNumber, 5);
		assertEquals(block1.get(3).type, "#ENDLOOP#ENDLOOP");

		List<TleLine> block2 =  blocks.get(1);
		assertEquals(block2.get(0).lineNumber, 7);
		assertEquals(block2.get(0).type, "#LOOP(#Four)");
		assertEquals(block2.get(1).lineNumber, 8);
		assertEquals(block2.get(1).type, "inner content");
		assertEquals(block2.get(2).lineNumber, 9);
		assertEquals(block2.get(2).type, "#ENDLOOP");
	}

}