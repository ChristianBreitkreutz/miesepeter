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
		List<TleLine> incommingList = new ArrayList<>();
		int blockLength = 0; // TODO: waere ne coole metric
		int blockComplexity = 0; // TODO: waere ne coole metric
		int openBlocks = 0;

		for (TleLine tleLine : completeFile) {
			List<Integer> startElements = findElements(tleLine.type, "#" + blocktype);
			List<Integer> endElements = findElements(tleLine.type, "#END" + blocktype);
			openBlocks += (startElements.size() - endElements.size());

			if (startElements.size() > 0 || endElements.size() > 0 || openBlocks > 0) {
				incommingList.add(tleLine); // add next line
				if (openBlocks == 0) {
					incommingList = cleanUpBlock(incommingList, blocktype);
					blocks.add(incommingList);
					incommingList = new ArrayList<>();
					if (startElements.size() == endElements.size()) {
						blocks.remove(blocks.size()-1);// bug somewhere upwards so override last element
						TreeMap<Integer, String> startsAndEnds = new TreeMap<>();
						for (Integer integer : startElements) {
							startsAndEnds.put(integer, "Start");
						}
						for (Integer integer : endElements) {
							startsAndEnds.put(integer, "End");
						}
						int innerBlock = 0;
						int oldSplitPosition = 0;
						for (int elem : startsAndEnds.keySet()) {
							if (startsAndEnds.get(elem) == "Start") {
								innerBlock++;
							}
							if (startsAndEnds.get(elem) == "End") {
								innerBlock--;
							}
							if (innerBlock == 0) {
								int splitPosition = elem + ("#END" + blocktype).length();
								String splitLine = tleLine.type;
								TleLine newLine = new TleLine(tleLine.lineNumber,
										splitLine.substring(oldSplitPosition, splitPosition));
								List<TleLine> OneLine = new ArrayList<>();
								OneLine.add(newLine);
								OneLine = cleanUpBlock(OneLine, blocktype);
								blocks.add( OneLine);
								incommingList = new ArrayList<>();
								oldSplitPosition = splitPosition;
							}
						}
					}
				}
			}
		}
		return blocks;
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

	private List<Integer> findElements(String line, String searchTerm) {
		List<Integer> letterPosition = new ArrayList<>();
		int lenghtSearchTerm = searchTerm.length();
		int index = 0;
		while (true) {
			index = line.indexOf(searchTerm, index);
			if (index == -1) {
				break;
			}
			letterPosition.add(index);
			index += lenghtSearchTerm;
		}
		return letterPosition;
	}
}
