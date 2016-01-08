package miesepeter;

import java.io.File;
import java.io.IOException;

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




public class miese_issues implements Sensor{

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
			System.out.println("inputfiles"+inputFile.path());
			File file = inputFile.file();
			
			this.analyseFile(file,perspectives);
			LineIterator it = null;
			try {
				it = FileUtils.lineIterator(file, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("its broken");
				e.printStackTrace();
			}
			try {
			    while (it.hasNext()) {
			    String line = it.nextLine();
			    System.out.println(line);
			    // do something with line
			    }
			} finally {
			    it.close();
			}
		      Issuable issuable = perspectives.as(Issuable.class, inputFile);
		      issuable.addIssue(issuable.newIssueBuilder()
		        .ruleKey(RuleKey.of("wurstname", "Wurstrule"))
		        .message("Message explaining why this issue has been raised.")
		        .line(1)
		        .build());
		      issuable.addIssue(issuable.newIssueBuilder()
		    		  .ruleKey(RuleKey.of("wurstname", "minorrule"))
		    		  .message("FuckingHell")
		    		  .line(3)
		    		  .build());
		    }
	}

	private void analyseFile(File file, ResourcePerspectives perspectives2) {
		// TODO Auto-generated method stub
		
	}
	

}
