package com.epages.sonar.miesepeter;

import java.io.File;
import java.util.ArrayList;

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
import com.epages.sonar.miesepeter.parser.issues.LoopIssues;

public class TleIssues implements Sensor {

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
		Parser TleParser = new Parser();
		ParseResult parseResult = TleParser.parseFile(file);
		ArrayList<IssueLine> genericResults = parseResult.getGenericTle();
		int complexity = 0;

		for (IssueLine result : genericResults) {
			switch (result.type) {
			case "#LOCAL":
				complexity += 2;
				triggerIssue(issuable, "local", result.lineNumber, "don't use tle local");
				break;

			case "#SET":
				complexity += 2;
				triggerIssue(issuable, "local", result.lineNumber, "don't use tle set (and local)");
				break;

			default:
				complexity += 1;
				triggerIssue(issuable, "general", result.lineNumber, "default tle logic");
				break;
			}
		}

		// lonelySet
		ArrayList<IssueLine> lonelySetResults = parseResult.getLonelySet();
		for (IssueLine result : lonelySetResults) {
			complexity += 2;
			triggerIssue(issuable, "lonelySet", result.lineNumber, "magic #SET without a 'LOCAL");
		}

		// loopIssues
		ArrayList<IssueLine> loopIssues = parseResult.getLoopIssue();
		for (IssueLine issueLine : loopIssues) {
			switch (issueLine.type) {
			case "NESTED_LOOP":
				complexity += 3;
				triggerIssue(issuable, "nestedLoop", issueLine.lineNumber, "nested loops, increase complexity");
				break;
			case "VARIABLE_IN_LOOP":
				complexity += 3;
				triggerIssue(issuable, "loopWithSet", issueLine.lineNumber, "#LOOP with #SET is programming. This don't belong in TLE");
				break;

			default:
				System.out.println("ohje");
				break;
			}
		}
		// javascript Issues
		ArrayList<IssueLine> javascriptIssues = parseResult.getJavascript();
		for (IssueLine javascriptIssue : javascriptIssues) {
			complexity += 1;
			triggerIssue(issuable, "javascriptInTemplate", javascriptIssue.lineNumber, "javascript in template");
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
