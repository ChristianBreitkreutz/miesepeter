package com.epages.sonar.miesepeter.parser;

import java.util.List;

public class CodeBlock {
	private List<CodeLine> block;
	private int lenght;
	private int blockComplexity;
	
	public int getLenght() {
		return lenght;
	}
	public void setLenght(int blockLenght) {
		this.lenght = blockLenght;
	}
	public int getComplexity() {
		return blockComplexity;
	}
	public void setComplexity(int blockComplexity) {
		this.blockComplexity = blockComplexity;
	}
	public List<CodeLine> getBlock() {
		return block;
	}
	public void setBlock(List<CodeLine> block) {
		this.block = block;
	}
}
