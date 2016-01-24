package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;

public class ParseResult {
	private ArrayList<IssueLine> lonelySet;
	private ArrayList<IssueLine> genericTle;
	private ArrayList<IssueLine> loopIssues;
	private ArrayList<IssueLine> javascript;
	

	public ArrayList<IssueLine> getLonelySet() {
		return lonelySet;
	}

	public void setLonelySet(ArrayList<IssueLine> lonelySet) {
		this.lonelySet = lonelySet;
	}

	public ArrayList<IssueLine> getGenericTle() {
		return genericTle;
	}

	public void setGenericTle(ArrayList<IssueLine> genericTle) {
		this.genericTle = genericTle;
	}

	public ArrayList<IssueLine> getLoopIssue() {
		return loopIssues;
	}

	public void setLoopIssues(ArrayList<IssueLine> loopIssue) {
		this.loopIssues = loopIssue;
	}

	public ArrayList<IssueLine> getJavascript() {
		return javascript;
	}

	public void setJavascript(ArrayList<IssueLine> javascript) {
		this.javascript = javascript;
	}
	
}
