package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.Regex;
import com.epages.sonar.miesepeter.parser.TleParser;

public class LonelySet implements TleParser {
	@Override
	public ArrayList<IssueLine> parse (List<String> linedFile){
		int lineNumber = 0;
		ArrayList<IssueLine> lineIssues = new ArrayList<>();
				Pattern patternSET = Pattern.compile(
						".*#SET"
						+ Regex.OPENPARENTHESIS
						+ Regex.UNLIMITEDWHITESPACE
						+ Regex.DOUBLEQUOTE
						+ "(" // open group parenthesis
							+ "[^" + Regex.DOUBLEQUOTE +"|.]*"// [^"|.]* all symbols except a double quote
						+ ")" // end group parenthesis
						+ Regex.DOUBLEQUOTE
						+ ".*"
				);
				for (String line : linedFile) {
				Matcher matcher = patternSET.matcher(line);
				if (matcher.find()) {
					if (!hasDefinitionInSameFile(lineNumber,matcher.group(1),linedFile)) {
						lineIssues.add(new IssueLine(lineNumber,"LonelySet"));
					}
				}
			lineNumber++;
		}
		return lineIssues;
	}
	private boolean hasDefinitionInSameFile (int startingLineNumber, String varName, List<String> linedFile){
		boolean hasDefinition = false;
		Pattern patternLOCALE = Pattern.compile(
				".*#LOCAL"
				+ Regex.OPENPARENTHESIS
				+ Regex.UNLIMITEDWHITESPACE
				+ Regex.DOUBLEQUOTE
				+ varName
				+ Regex.DOUBLEQUOTE
				+ ".*"
		);
		Matcher matcher;
		int firstLine = 1;// line 0 contains the filename
		for (int i = startingLineNumber; i>=firstLine; i--) {
			matcher = patternLOCALE.matcher(linedFile.get(i));
			if (matcher.matches()) {
				hasDefinition = true;
				break;
			}
		}
		return hasDefinition;
	}
}