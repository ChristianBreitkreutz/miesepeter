package miesepeter;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.SonarPlugin;

public class miese_plugin extends SonarPlugin{

	@Override
	public List getExtensions() {
		List extensions = new ArrayList();
		extensions.add(miese_properties.definitions());
		extensions.add(miese_language.class);
		extensions.add(miese_metric.class);
		extensions.add(miese_sensor.class);
		// rule
		extensions.add(miese_rule.class);
		extensions.add(miese_profile.class);
		extensions.add(miese_issues.class);
		return extensions;
	}

}
