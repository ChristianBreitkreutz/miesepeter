package miesepeter;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;



public class miese_sensor implements Sensor {

	private Settings settings;
	private FileSystem fs;

	public miese_sensor(Settings settings, FileSystem fs) {
		this.settings = settings;
		this.fs = fs;
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	@Override
	public void analyse(Project module, SensorContext context) {
		for (InputFile inputFile : fs.inputFiles(fs.predicates().all())) {
			context.saveMeasure(inputFile, new Measure<String>(miese_metric.MESSAGE, "some message"));
			context.saveMeasure(inputFile, new Measure<String>(miese_metric.RANDOM, Math.random()));
			context.saveMeasure(inputFile, new Measure<String>(miese_metric.TechDEPT, Math.random()));
		    }
			
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
