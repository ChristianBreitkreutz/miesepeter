package com.epages.sonar.miesepeter;

import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.config.Settings;


public class ParsingLanguage extends AbstractLanguage{

	private static final String NAME = "epages template language engine (TLE)";
	private static final String KEY = "tle";
	
	public ParsingLanguage(Settings settings) {
		super(KEY, NAME);
	}

	@Override
	public String[] getFileSuffixes() {
		String[] suffixes = {"html"};
		return suffixes;
	}

}
