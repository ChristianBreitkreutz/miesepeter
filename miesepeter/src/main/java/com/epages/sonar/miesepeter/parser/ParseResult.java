package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;

public class ParseResult {
	private ArrayList<LineIssue> lonelySet;
	private ArrayList<LineIssue> genericTle;

	public ArrayList<LineIssue> getLonelySet() {
		return lonelySet;
	}

	public void setLonelySet(ArrayList<LineIssue> lonelySet) {
		this.lonelySet = lonelySet;
	}

	public ArrayList<LineIssue> getGenericTle() {
		return genericTle;
	}

	public void setGenericTle(ArrayList<LineIssue> genericTle) {
		this.genericTle = genericTle;
	}
	
}
