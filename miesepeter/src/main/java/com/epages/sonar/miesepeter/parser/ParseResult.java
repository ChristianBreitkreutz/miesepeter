package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;
import java.util.List;

public class ParseResult {
	private List<IssueLine> lonelySet = new ArrayList<>();
	private List<IssueLine> genericTle = new ArrayList<>();
	private List<IssueLine> loopIssues = new ArrayList<>();
	private List<IssueLine> javascript = new ArrayList<>();

	public List<IssueLine> getLonelySet() {
		return lonelySet;
	}

	public void setLonelySet(List<IssueLine> lonelySet) {
		this.lonelySet.clear();
		this.lonelySet.addAll(lonelySet);
	}

	public List<IssueLine> getGenericTle() {
		return genericTle;
	}

	public void setGenericTle(List<IssueLine> genericTle) {
		this.genericTle.clear();
		this.genericTle.addAll(genericTle);
	}

	public List<IssueLine> getLoopIssue() {
		return loopIssues;
	}

	public void setLoopIssues(List<IssueLine> loopIssue) {
		this.loopIssues.clear();
		this.loopIssues.addAll(loopIssue);
	}

	public List<IssueLine> getJavascript() {
		return javascript;
	}

	public void setJavascript(List<IssueLine> javascript) {
		this.javascript.clear();
		this.javascript.addAll(javascript);
	}
	
}
