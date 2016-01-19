package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BlockFinder {

	private List<CodeLine> completeFile;

	public BlockFinder(List<CodeLine> completeFile) {
		this.completeFile = completeFile;
	}

	public List<List<CodeLine>> getCodeBlocks(String blocktype) {
		List<List<CodeLine>> codeBlocks = new ArrayList<>();
		List<CodeLine> codeBlock = new ArrayList<>();
		int blockLength = 0; // TODO: waere ne coole metric
		int blockComplexity = 0; // TODO: waere ne coole metric
		int openBlocks = 0;

		for (CodeLine codeLine : completeFile) {
			List<Integer> startElements = findElementsInLine(codeLine.text, "#" + blocktype);
			List<Integer> endElements = findElementsInLine(codeLine.text, "#END" + blocktype);
			openBlocks += (startElements.size() - endElements.size());

			if (startElements.size() > 0 || endElements.size() > 0 || openBlocks > 0) {
				codeBlock.add(codeLine); // add next line
				if (openBlocks == 0) {
					if (startElements.size() == endElements.size()) {
						TreeMap<Integer, String> startsAndEnds = mergeStartAndEndElements(startElements, endElements);
						List<List<CodeLine>> blocksInLine = catchInlineBlocks(blocktype, codeLine, startsAndEnds);
						codeBlocks.addAll(blocksInLine);
					}else {
						codeBlock = cleanUpCodeBlock(codeBlock, blocktype);
						codeBlocks.add(codeBlock);
						codeBlock = new ArrayList<>();
					}
				}
			}
		}
		return codeBlocks;
	}

	private List<List<CodeLine>> catchInlineBlocks(String blocktype, CodeLine codeLine, TreeMap<Integer, String> startsAndEnds) {
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
				List<CodeLine> inlineCodeBlock = createInlineCodeBlock( codeLine, oldSplitPosition, splitPosition);
				inlineCodeBlock = cleanUpCodeBlock(inlineCodeBlock, blocktype);
				blocksInLine.add( inlineCodeBlock);
				oldSplitPosition = splitPosition;
			}
		}
		return blocksInLine;
	}

	private List<CodeLine> createInlineCodeBlock(CodeLine codeLine, int oldSplitPosition, int splitPosition) {
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

	private List<CodeLine> cleanUpCodeBlock(List<CodeLine> codeBlock, String blocktype) {
		String firstLine = codeBlock.get(0).text;
		int firstLineNumber = codeBlock.get(0).lineNumber;
		int positionBeforeFirstElement = firstLine.indexOf("#" + blocktype);
		String trimmedFirstLine = firstLine.substring(positionBeforeFirstElement);
		codeBlock.set(0, new CodeLine(firstLineNumber, trimmedFirstLine));

		int lastIndex = codeBlock.size() - 1;
		String lastLine = codeBlock.get(lastIndex).text;
		int lastLineNumber = codeBlock.get(lastIndex).lineNumber;
		String endElement = "#END" + blocktype;
		int positionAfterLastElement = lastLine.lastIndexOf(endElement) + endElement.length();
		String trimmedLastLine = lastLine.substring(0, positionAfterLastElement);
		codeBlock.set(lastIndex, new CodeLine(lastLineNumber, trimmedLastLine));
		return codeBlock;
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
