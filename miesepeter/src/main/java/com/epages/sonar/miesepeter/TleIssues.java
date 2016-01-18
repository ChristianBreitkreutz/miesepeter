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

		for (IssueLine result : genericResults) {
			switch (result.type) {
			case "LOCAL":
				triggerIssue(issuable, "local", result.lineNumber, "don't use tle local");
				break;

			case "SET":
				triggerIssue(issuable, "local", result.lineNumber, "don't use tle set (and local)");
				break;

			default:
				triggerIssue(issuable, "general", result.lineNumber, "default tle logic");
				break;
			}
		}

		// lonelySet
		ArrayList<IssueLine> lonelySetResults = parseResult.getLonelySet();
		for (IssueLine result : lonelySetResults) {
			triggerIssue(issuable, "lonelySet", result.lineNumber, "magic #SET without a 'LOCAL");
		}

		// measures
		context.saveMeasure(inputFile, new Measure<String>( CoreMetrics.COMPLEXITY, (double) genericResults.size()));
		context.saveMeasure(inputFile, new Measure<String>( CoreMetrics.NCLOC, (double) inputFile.lines()));
				
	}

	private void triggerIssue ( Issuable issuable, String ruleName, int lineNumber, String message ){
		issuable.addIssue(issuable.newIssueBuilder().ruleKey(RuleKey.of("EpagesTemplateLanguage", ruleName))//
				.message(message).line(lineNumber).build());
		
	}
}
