package com.epages.sonar.miesepeter;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;


public class MiesesProfile extends ProfileDefinition {

	@Override
	public RulesProfile createProfile(ValidationMessages validation) {
		return RulesProfile.create("rules for tle scan", "tle");
	}
}
