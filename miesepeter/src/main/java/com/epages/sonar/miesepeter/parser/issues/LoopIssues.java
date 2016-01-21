package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;

import com.epages.sonar.miesepeter.parser.BlockFinder;
import com.epages.sonar.miesepeter.parser.CodeBlock;
import com.epages.sonar.miesepeter.parser.CodeLine;
import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.TleParser;

public class LoopIssues implements TleParser{

	@Override
	public ArrayList<IssueLine> parse(List<CodeLine> completeFile) {
		ArrayList<IssueLine> lineIssues = new ArrayList<>();
		BlockFinder blockfinder = new BlockFinder(completeFile);
		List<CodeBlock> loopBlocks = blockfinder.getCodeBlocks("LOOP");
		for (CodeBlock codeBlock : loopBlocks) {
			List<CodeLine> lines = codeBlock.getBlock();
			for (CodeLine codeLine : lines) {
				if(codeLine.text.matches("#SET")) { //TODO: find also multiple sets
					lineIssues.add(new IssueLine(codeLine.lineNumber, "VARIABLE_IN_LOOP"));
				}
				if(codeLine.text.matches("#LOCAL")) {
					lineIssues.add(new IssueLine(codeLine.lineNumber, "VARIABLE_IN_LOOP"));
				}
				if(codeLine.text.matches("#LOOP")) {
					lineIssues.add(new IssueLine(codeLine.lineNumber, "NESTED_LOOP"));
				}
			}
		}
		return lineIssues;
	}

}
