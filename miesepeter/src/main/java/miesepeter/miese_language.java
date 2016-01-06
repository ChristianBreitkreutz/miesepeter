package miesepeter;

import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.config.Settings;


public class miese_language extends AbstractLanguage{

	public static final String NAME = "epages template language engine(TLE)";
	public static final String KEY = "tle";
	
	private Settings settings;
	public miese_language(Settings settings) {
		super(KEY, NAME);
	    this.settings = settings;
	}

	@Override
	public String[] getFileSuffixes() {
		String[] suffixes = {"html"};
		return suffixes;
	}

}
