package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BlockFinder {

	private List<TleLine> completeFile;

	public BlockFinder(List<TleLine> completeFile) {
		this.completeFile = completeFile;
	}

	public List<List<TleLine>> getBlocks(String blocktype) {
		List<List<TleLine>> blocks = new ArrayList<>();
		List<TleLine> elementblocks = new ArrayList<>();
		int blockLength = 0; // TODO: waere ne coole metric
		int blockComplexity = 0; // TODO: waere ne coole metric
		int openBlocks = 0;

		for (TleLine tleLine : completeFile) {
			List<Integer> startElements = findElementsInLine(tleLine.type, "#" + blocktype);
			List<Integer> endElements = findElementsInLine(tleLine.type, "#END" + blocktype);
			openBlocks += (startElements.size() - endElements.size());

			if (startElements.size() > 0 || endElements.size() > 0 || openBlocks > 0) {
				elementblocks.add(tleLine); // add next line
				if (openBlocks == 0) {
					elementblocks = cleanUpBlock(elementblocks, blocktype);
					blocks.add(elementblocks);
					elementblocks = new ArrayList<>();
				}
				if (openBlocks == 0 && startElements.size() == endElements.size()) {
					TreeMap<Integer, String> startsAndEnds = mergeStartAndEndElements(startElements, endElements);
					List<List<TleLine>> blocksInLine = catchInlineBlocks(blocktype, tleLine, startsAndEnds);
					blocks.addAll(blocksInLine);
				}
			}
		}
		return blocks;
	}

	private List<List<TleLine>> catchInlineBlocks(String blocktype, TleLine tleLine, TreeMap<Integer, String> startsAndEnds) {
		List<List<TleLine>> blocksInLine = new ArrayList<>();
		int elementBalance = 0;
		int oldSplitPosition = 0;
		for (int startOrEndElement : startsAndEnds.keySet()) {
			if (startsAndEnds.get(startOrEndElement) == "Start") {
				elementBalance++;
			}
			if (startsAndEnds.get(startOrEndElement) == "End") {
				elementBalance--;
			}
			if (elementBalance == 0) {
				int splitPosition = startOrEndElement + ("#END" + blocktype).length();
				List<TleLine> OneLine = createSubBlock( tleLine, oldSplitPosition, splitPosition);
				OneLine = cleanUpBlock(OneLine, blocktype);
				oldSplitPosition = splitPosition;
				blocksInLine.add( OneLine);
			}
		}
		return blocksInLine;
	}

	private List<TleLine> createSubBlock(TleLine tleLine, int oldSplitPosition, int splitPosition) {
		String splitLine = tleLine.type;
		TleLine newLine = new TleLine(tleLine.lineNumber, splitLine.substring(oldSplitPosition, splitPosition));
		List<TleLine> OneLine = new ArrayList<>();
		OneLine.add(newLine);
		return OneLine;
	}

	private TreeMap<Integer, String> mergeStartAndEndElements(List<Integer> startElements, List<Integer> endElements) {
		TreeMap<Integer, String> startsAndEnds = new TreeMap<>();
		for (Integer integer : startElements) {
			startsAndEnds.put(integer, "Start");
		}
		for (Integer integer : endElements) {
			startsAndEnds.put(integer, "End");
		}
		return startsAndEnds;
	}

	private List<TleLine> cleanUpBlock(List<TleLine> block, String blocktype) {
		String firstLine = block.get(0).type;
		int firstLineNumber = block.get(0).lineNumber;
		int firstLetter = firstLine.indexOf("#" + blocktype);
		String newLine = firstLine.substring(firstLetter);
		block.set(0, new TleLine(firstLineNumber, newLine));

		int lastIndex = block.size() - 1;
		String lastLine = block.get(lastIndex).type;
		int lastLineNumber = block.get(lastIndex).lineNumber;
		String endElement = "#END" + blocktype;
		int lastLetter = lastLine.lastIndexOf(endElement) + endElement.length();
		newLine = lastLine.substring(0, lastLetter);
		block.set(lastIndex, new TleLine(lastLineNumber, newLine));
		return block;
	}

	private List<Integer> findElementsInLine(String line, String searchTerm) {
		List<Integer> letterPosition = new ArrayList<>();
		int lenghtSearchTerm = searchTerm.length();
		int index = 0;
		while (true) {
			index = line.indexOf(searchTerm, index);
			if (index == -1) { // -1 == nothing found
				break;
			}
			letterPosition.add(index);
			index += lenghtSearchTerm;
		}
		return letterPosition;
	}
}
