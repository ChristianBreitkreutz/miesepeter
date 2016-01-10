package miesepeter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.internal.apachecommons.io.FileUtils;
import org.sonar.api.internal.apachecommons.io.LineIterator;
import org.sonar.api.issue.Issuable;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;

public class miese_issues implements Sensor {

	private FileSystem fs;
	private ResourcePerspectives perspectives;

	public miese_issues(FileSystem fs, ResourcePerspectives perspectives) {
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
			this.analyseFile( inputFile );
		}
	}

	private void analyseFile(InputFile inputFile) {
		File file = inputFile.file();
		Issuable issuable = perspectives.as(Issuable.class, inputFile);
		miese_tleparser mTleP = new miese_tleparser();
		ArrayList<miese_tleDTO> myList = mTleP.parseFile(file);

		for (miese_tleDTO dto : myList) {
			switch (dto.getTleType()) {
			case "LOCAL":
				triggerRule(issuable, "local", dto.getLineNumber(), "don't use tle local");
				break;

			case "SET":
				triggerRule(issuable, "local", dto.getLineNumber(), "don't use tle set (and local)");
				break;

			default:
				triggerRule(issuable, "tlelogic", dto.getLineNumber(), "default tle logic");
				break;
			}
		}
	}

	private void triggerRule ( Issuable issuable, String ruleName, int lineNumber, String message ){
		issuable.addIssue(issuable.newIssueBuilder().ruleKey(RuleKey.of("tleLogic", ruleName))//
				.message(message).line(lineNumber).build());
		
	}
}
