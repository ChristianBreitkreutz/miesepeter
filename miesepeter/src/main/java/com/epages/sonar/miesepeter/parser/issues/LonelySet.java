package com.epages.sonar.miesepeter.parser.issues;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epages.sonar.miesepeter.parser.CodeLine;
import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.Regex;
import com.epages.sonar.miesepeter.parser.TleParser;

public class LonelySet implements TleParser {
	@Override
	public ArrayList<IssueLine> parse(List<CodeLine> linedFile){
		
		int positionInLoop = 0;
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
				for (CodeLine line : linedFile) {
				Matcher matcher = patternSET.matcher(line.text);
				if (matcher.find()) {
					if (!hasDefinitionInSameFile(positionInLoop,matcher.group(1),linedFile)) {
						lineIssues.add(new IssueLine(line.lineNumber,"LonelySet"));
					}
				}
			positionInLoop++;
		}
		return lineIssues;
	}
	private boolean hasDefinitionInSameFile (int currentPosition, String varName, List<CodeLine> linedFile){
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
		for (int i = currentPosition; i>=0; i--) {
				matcher = patternLOCALE.matcher(linedFile.get(i).text);
				if (matcher.matches()) {
					hasDefinition = true;
					break;
				}
		}
		return hasDefinition;
	}

}