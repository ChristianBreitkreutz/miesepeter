package com.epages.sonar.miesepeter.parser;

import java.util.ArrayList;

public class ParseResult {
	private ArrayList<TleLine> lonelySet;
	private ArrayList<TleLine> genericTle;

	public ArrayList<TleLine> getLonelySet() {
		return lonelySet;
	}

	public void setLonelySet(ArrayList<TleLine> lonelySet) {
		this.lonelySet = lonelySet;
	}

	public ArrayList<TleLine> getGenericTle() {
		return genericTle;
	}

	public void setGenericTle(ArrayList<TleLine> genericTle) {
		this.genericTle = genericTle;
	}
	
}
