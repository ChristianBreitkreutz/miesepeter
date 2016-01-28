package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class BlockFinder {

	private List<CodeLine> completeFile;

	public BlockFinder(List<CodeLine> completeFile) {
		this.completeFile = completeFile;
	}

	public List<CodeBlock> getCodeBlocks(String blocktype) {
		List<CodeBlock> codeBlocksInFile = new ArrayList<>();
		List<CodeLine> subCodeBlock = new ArrayList<>();
		int blockComplexity = 0;
		int openBlocks = 0;

		for (CodeLine codeLine : completeFile) {
			List<Integer> startElements = findElementsInLine(codeLine.getText(), "#" + blocktype);
			List<Integer> endElements = findElementsInLine(codeLine.getText(), "#END" + blocktype);
			openBlocks += (startElements.size() - endElements.size());

			if (startElements.size() > 0 || endElements.size() > 0 || openBlocks > 0) {
				blockComplexity += startElements.size();
				subCodeBlock.add(codeLine); // add next line
				if (openBlocks == 0) {
					if (startElements.size() == endElements.size()) {
						TreeMap<Integer, String> startsAndEnds = mergeStartAndEndElements(startElements, endElements);
						List<CodeBlock> blocksInLine = catchInlineBlocks(blocktype, codeLine, startsAndEnds);
						codeBlocksInFile.addAll(blocksInLine);
					}else {
						subCodeBlock = cleanUpInlineCodeBlock(subCodeBlock, blocktype);
						CodeBlock codeBlock = new CodeBlock();
						codeBlock.setBlock(subCodeBlock);
						codeBlock.setLenght(subCodeBlock.size());
						codeBlock.setComplexity(blockComplexity);
						codeBlocksInFile.add(codeBlock);
						blockComplexity = 0;
						subCodeBlock = new ArrayList<>();
					}
				}
			}
		}
		return codeBlocksInFile;
	}

	private List<CodeBlock> catchInlineBlocks(String blocktype, CodeLine codeLine, TreeMap<Integer, String> startsAndEnds) {
		List<CodeBlock> blocksInLine = new ArrayList<>();
		int elementBalance = 0;
		int oldSplitPosition = 0;
		int blockComplexity = 0;
		for (int startOrEndElement : startsAndEnds.keySet()) {
			if (startsAndEnds.get(startOrEndElement) == "Start") {
				elementBalance++;
				blockComplexity++;
			}
			if (startsAndEnds.get(startOrEndElement) == "End") {
				elementBalance--;
			}
			if (elementBalance == 0) {
				int splitPosition = startOrEndElement + ("#END" + blocktype).length();
				List<CodeLine> inlineCodeBlock = createInlineCodeBlock( codeLine, oldSplitPosition, splitPosition);
				inlineCodeBlock = cleanUpInlineCodeBlock(inlineCodeBlock, blocktype);
				CodeBlock codeBlock = new CodeBlock();
				codeBlock.setBlock(inlineCodeBlock);
				codeBlock.setLenght(inlineCodeBlock.size());
				codeBlock.setComplexity(blockComplexity);
				blockComplexity = 0;
				blocksInLine.add( codeBlock);
				oldSplitPosition = splitPosition;
			}
		}
		return blocksInLine;
	}

	private List<CodeLine> createInlineCodeBlock(CodeLine codeLine, int oldSplitPosition, int splitPosition) {
		String splitLine = codeLine.getText();
		CodeLine newLine = new CodeLine(codeLine.getLineNumber(), splitLine.substring(oldSplitPosition, splitPosition));
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

	private List<CodeLine> cleanUpInlineCodeBlock(List<CodeLine> codeBlock, String blocktype) {
		String firstLine = codeBlock.get(0).getText();
		int firstLineNumber = codeBlock.get(0).getLineNumber();
		int positionBeforeFirstElement = firstLine.indexOf("#" + blocktype);
		String trimmedFirstLine = firstLine.substring(positionBeforeFirstElement);
		codeBlock.set(0, new CodeLine(firstLineNumber, trimmedFirstLine));

		int lastIndex = codeBlock.size() - 1;
		String lastLine = codeBlock.get(lastIndex).getText();
		int lastLineNumber = codeBlock.get(lastIndex).getLineNumber();
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
