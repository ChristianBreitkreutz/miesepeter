package miesepeter;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.config.PropertyDefinition;


public class miese_properties {
	public static final String MY_PROPERTY_KEY = "sonar.example.myproperty";
	public static List<PropertyDefinition> definitions() {
	    return Arrays.asList(
	      PropertyDefinition.builder(MY_PROPERTY_KEY)
	        .name("My Property")
	        .description("Description of my property")
	        .defaultValue("Hello World!")
	        .build(),

	      PropertyDefinition.builder("sonar.tle.file.suffixes")
	        .name("File Suffixes")
	        .description("Comma-separated list of suffixes for files to analyze.")
	        .defaultValue("html,tle")
	        .build()
	      );
	  }
}
