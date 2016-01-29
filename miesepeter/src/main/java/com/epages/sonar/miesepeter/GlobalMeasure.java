package com.epages.sonar.miesepeter;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

public class GlobalMeasure implements Sensor {

	private final FileSystem fs;

	public GlobalMeasure(FileSystem fs) {
		this.fs = fs;
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	@Override
	public void analyse(Project module, SensorContext context) {
		for (InputFile inputFile : fs.inputFiles(fs.predicates().all())) {
			context.saveMeasure(inputFile, new Measure<String>(TleMetrics.MESSAGE, "file message" + Math.random()));
			context.saveMeasure(inputFile, new Measure<String>(TleMetrics.RANDOM, Math.random()));
			context.saveMeasure(inputFile, new Measure<String>(TleMetrics.TechDEPT, Math.random()));
		}
		context.saveMeasure(new Measure<String>(TleMetrics.Global_MESSAGE, "project message" + Math.random()));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
