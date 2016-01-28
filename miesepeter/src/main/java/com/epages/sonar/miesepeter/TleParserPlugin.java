package com.epages.sonar.miesepeter;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.SonarPlugin;

public class TleParserPlugin extends SonarPlugin{

	@Override
	public List<Object> getExtensions() {
		List<Object> extensions = new ArrayList<Object>();
		extensions.add(Properties.definitions());
		extensions.add(ParsingLanguage.class);
		extensions.add(TleMetrics.class);
		extensions.add(GlobalMeasure.class);

		extensions.add(Rules.class);
		extensions.add(MiesesProfile.class);
		extensions.add(TleIssues.class);
		return extensions;
	}

}
