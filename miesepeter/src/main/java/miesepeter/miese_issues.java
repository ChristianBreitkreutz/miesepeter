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
		System.out.println("analyse issues");
		for (InputFile inputFile : fs.inputFiles(fs.predicates().all())) {
			System.out.println("inputfiles" + inputFile.path());
			this.analyseFile(inputFile, perspectives);
		}
	}

	private void analyseFile(InputFile inputFile, ResourcePerspectives perspectives2) {
		File file = inputFile.file();
		// TODO Auto-generated method stub
		Issuable issuable = perspectives.as(Issuable.class, inputFile);
		miese_tleparser mTleP = new miese_tleparser();
		ArrayList<Integer> myList = mTleP.parseFile(file);

		for (Integer lineNumber : myList) {
			issuable.addIssue(issuable.newIssueBuilder().ruleKey(RuleKey.of("tleLogic", "tlelogic"))
					.message("found TLE #IF").line(lineNumber).build());
		}
	}

}
