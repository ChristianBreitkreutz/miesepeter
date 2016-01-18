package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;

public class ParseResult {
	private ArrayList<IssueLine> lonelySet;
	private ArrayList<IssueLine> genericTle;

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
	
}
