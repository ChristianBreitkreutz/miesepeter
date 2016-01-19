package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BlockFinder {

	private List<CodeLine> completeFile;

	public BlockFinder(List<CodeLine> completeFile) {
		this.completeFile = completeFile;
	}

	public List<List<CodeLine>> getBlocks(String blocktype) {
		List<List<CodeLine>> blocks = new ArrayList<>();
		List<CodeLine> elementblocks = new ArrayList<>();
		int blockLength = 0; // TODO: waere ne coole metric
		int blockComplexity = 0; // TODO: waere ne coole metric
		int openBlocks = 0;

		for (CodeLine tleLine : completeFile) {
			List<Integer> startElements = findElementsInLine(tleLine.text, "#" + blocktype);
			List<Integer> endElements = findElementsInLine(tleLine.text, "#END" + blocktype);
			openBlocks += (startElements.size() - endElements.size());

			if (startElements.size() > 0 || endElements.size() > 0 || openBlocks > 0) {
				elementblocks.add(tleLine); // add next line
				if (openBlocks == 0) {
					if (startElements.size() == endElements.size()) {
						TreeMap<Integer, String> startsAndEnds = mergeStartAndEndElements(startElements, endElements);
						List<List<CodeLine>> blocksInLine = catchInlineBlocks(blocktype, tleLine, startsAndEnds);
						blocks.addAll(blocksInLine);
					}else {
						elementblocks = cleanUpBlock(elementblocks, blocktype);
						blocks.add(elementblocks);
						elementblocks = new ArrayList<>();
					}
				}
			}
		}
		return blocks;
	}

	private List<List<CodeLine>> catchInlineBlocks(String blocktype, CodeLine codeLine, TreeMap<Integer, String> startsAndEnds) {
		System.out.println(codeLine.text);
		List<List<CodeLine>> blocksInLine = new ArrayList<>();
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
				List<CodeLine> OneLine = createSubBlock( codeLine, oldSplitPosition, splitPosition);
				OneLine = cleanUpBlock(OneLine, blocktype);
				oldSplitPosition = splitPosition;
				blocksInLine.add( OneLine);
			}
		}
		return blocksInLine;
	}

	private List<CodeLine> createSubBlock(CodeLine codeLine, int oldSplitPosition, int splitPosition) {
		String splitLine = codeLine.text;
		CodeLine newLine = new CodeLine(codeLine.lineNumber, splitLine.substring(oldSplitPosition, splitPosition));
		List<CodeLine> OneLine = new ArrayList<>();
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

	private List<CodeLine> cleanUpBlock(List<CodeLine> block, String blocktype) {
		String firstLine = block.get(0).text;
		int firstLineNumber = block.get(0).lineNumber;
		int firstLetter = firstLine.indexOf("#" + blocktype);
		String newLine = firstLine.substring(firstLetter);
		block.set(0, new CodeLine(firstLineNumber, newLine));

		int lastIndex = block.size() - 1;
		String lastLine = block.get(lastIndex).text;
		int lastLineNumber = block.get(lastIndex).lineNumber;
		String endElement = "#END" + blocktype;
		int lastLetter = lastLine.lastIndexOf(endElement) + endElement.length();
		newLine = lastLine.substring(0, lastLetter);
		block.set(lastIndex, new CodeLine(lastLineNumber, newLine));
		return block;
	}

	private List<Integer> findElementsInLine(String line, String searchTerm) {
		List<Integer> indices = new ArrayList<>();
		int lenghtSearchTerm = searchTerm.length();
		int index = 0;
		while (true) {
			index = line.indexOf(searchTerm, index);
			if (index == -1) { // -1 == nothing found
				break;
			}
			indices.add(index);
			index += lenghtSearchTerm;
		}
		return indices;
	}
}
