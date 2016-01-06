package miesepeter;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;


public class miese_profile extends ProfileDefinition{

	@Override
	public RulesProfile createProfile(ValidationMessages validation) {
		return RulesProfile.create("FooLint Rules", "tle");
	}
}
