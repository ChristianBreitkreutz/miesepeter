package com.epages.sonar.miesepeter;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;

import com.epages.sonar.miesepeter.parser.IssueLine;
import com.epages.sonar.miesepeter.parser.ParseResult;
import com.epages.sonar.miesepeter.parser.Parser;

public class TleIssues implements Sensor {

	private static final Logger log = LoggerFactory.getLogger(TleIssues.class);

	private FileSystem fs;
	private ResourcePerspectives perspectives;

	public TleIssues(FileSystem fs, ResourcePerspectives perspectives) {
		this.fs = fs;
		this.perspectives = perspectives;
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	@Override
	public void analyse(Project module, SensorContext context) {
		for (InputFile inputFile : fs.inputFiles(fs.predicates().all())) {
			this.analyseFile( inputFile, context );
		}
	}

	private void analyseFile(InputFile inputFile, SensorContext context) {
		File file = inputFile.file();
		Issuable issuable = perspectives.as(Issuable.class, inputFile);
		Parser tleParser = new Parser();
		ParseResult parseResult = tleParser.parseFile(file);
		List<IssueLine> genericResults = parseResult.getGenericTle();
		int complexity = 0;

		for (IssueLine result : genericResults) {
			switch (result.getType()) {
			case "#LOCAL":
				complexity += 2;
				triggerIssue(issuable, "local", result.getLineNumber(), "try not to use a variable in the template (solve this in UI controller)");
				break;

			case "#SET":
				complexity += 2;
				triggerIssue(issuable, "local", result.getLineNumber(), "try not to use a variable in the template (solve this in UI controller)");
				break;

			case "#IF":
				complexity += 1;
				triggerIssue(issuable, "IF", result.getLineNumber(), "TLE #IF");
				break;
				
			case "#ELSE":
				complexity += 1;
				triggerIssue(issuable, "ELSE", result.getLineNumber(), "TLE #ELSE");
				break;
				
			case "#ELSIF":
				complexity += 1;
				triggerIssue(issuable, "ELSIF", result.getLineNumber(), "TLE #ELSIF");
				break;
			case "#REM":
				complexity += 1;
				triggerIssue(issuable, "REM", result.getLineNumber(), "Comment in TLE");
				break;
			case "#BLOCK":
				complexity += 1;
				triggerIssue(issuable, "BLOCK", result.getLineNumber(), "TLE Block");
				break;
			case "#WITH":
				complexity += 1;
				triggerIssue(issuable, "WITH", result.getLineNumber(), "TLE #With scope change");
				break;
			case "#WITH_ERROR":
				complexity += 1;
				triggerIssue(issuable, "WITH_ERROR", result.getLineNumber(), "TLE error context");
				break;
			case "#FUNCTION":
				complexity += 1;
				triggerIssue(issuable, "FUNCTION", result.getLineNumber(), "TLE Function call (try to solve this in UI controller)");
				break;
			case "#MENU":
				complexity += 1;
				triggerIssue(issuable, "MENU", result.getLineNumber(), "TLE Menu");
				break;
			case "#CALCULATE":
				complexity += 1;
				triggerIssue(issuable, "CALCULATE", result.getLineNumber(), "Calculation in TLE (try to solve this in UI controller)");
				break;
			case "#PROGRESS":
				complexity += 1;
				triggerIssue(issuable, "PROGRESS", result.getLineNumber(), "TLE #PROGRESS");
				break;
			case "OR":
				complexity += 1;
				triggerIssue(issuable, "LogicElement", result.getLineNumber(), "(OR) TLE logic increase complexity");
				break;
			case "AND":
				complexity += 1;
				triggerIssue(issuable, "LogicElement", result.getLineNumber(), "(AND) TLE logic increase complexity");
				break;

			default:
				complexity += 1;
				triggerIssue(issuable, "general", result.getLineNumber(), "default tle logic");
				break;
			}
		}

		// lonelySet
		List<IssueLine> lonelySetResults = parseResult.getLonelySet();
		for (IssueLine result : lonelySetResults) {
			complexity += 2;
			triggerIssue(issuable, "lonelySet", result.getLineNumber(), "magic #SET without a 'LOCAL");
		}

		// loopIssues
		List<IssueLine> loopIssues = parseResult.getLoopIssue();
		for (IssueLine issueLine : loopIssues) {
			switch (issueLine.getType()) {
			case "NESTED_LOOP":
				complexity += 3;
				triggerIssue(issuable, "nestedLoop", issueLine.getLineNumber(), "nested loops, increase complexity");
				break;
			case "VARIABLE_IN_LOOP":
				complexity += 3;
				triggerIssue(issuable, "loopWithSet", issueLine.getLineNumber(), "#LOOP with #SET is programming. This don't belong in TLE");
				break;

			default:
				log.warn("Unknown issue type {} in line {}", issueLine.getType(), issueLine.getLineNumber());
				break;
			}
		}
		// javascript Issues
		List<IssueLine> javascriptIssues = parseResult.getJavascript();
		for (IssueLine javascriptIssue : javascriptIssues) {
			complexity += 1;
			triggerIssue(issuable, "javascriptInTemplate", javascriptIssue.getLineNumber(), "javascript in template");
		}
		// measures
		double linesOfCode = (double) (genericResults.size() + loopIssues.size() + javascriptIssues.size());
		context.saveMeasure(inputFile, new Measure<String>( CoreMetrics.COMPLEXITY, (double) complexity));
		context.saveMeasure(inputFile, new Measure<String>( CoreMetrics.NCLOC, (double) linesOfCode));
				
	}

	private void triggerIssue ( Issuable issuable, String ruleName, int lineNumber, String message ){
		issuable.addIssue(issuable.newIssueBuilder().ruleKey(RuleKey.of("EpagesTemplateLanguage", ruleName))//
				.message(message).line(lineNumber).build());
		
	}
}
