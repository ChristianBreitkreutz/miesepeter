package miesepeter;

import org.sonar.api.rule.Severity;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.server.rule.RulesDefinition;


public class miese_rule implements RulesDefinition{

	@Override
	public void define(Context context) {
		 NewRepository repository = context.createRepository("wurstname", "tle").setName("mywurst");

		    NewRule x1Rule = repository.createRule("Wurstrule")
		      .setName("wurst Stupid rule")
		      .setHtmlDescription("wurt Generate an issue on every line 1")
		      .setTags("style", "stupid")
		      .setStatus(RuleStatus.READY)
		      .setSeverity(Severity.BLOCKER);

		    x1Rule
		      .setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		      .setDebtRemediationFunction(x1Rule.debtRemediationFunctions().linearWithOffset("1h","1min"));
		    
		    NewRule x2Rule = repository.createRule("minorrule")
		    		.setName("my minor rule")
		    		.setHtmlDescription("minor description for test")
		    		.setSeverity(Severity.MAJOR);
		    
		    x2Rule
		    .setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		    .setDebtRemediationFunction(x2Rule.debtRemediationFunctions().linearWithOffset("1h","1min"));

		    // don't forget to call done() to finalize the definition
		    repository.done();
		    NewRepository TleRepo = context.createRepository("tleLogic", "tle").setName("TLE logig");
		    NewRule TLElogic = TleRepo.createRule("tlelogic")
		    		.setName("TLE logic blocks")
		    		.setHtmlDescription("collect all TLE logic blocks from templates")
		    		.setSeverity(Severity.MINOR);
		    TLElogic.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		            .setDebtRemediationFunction(TLElogic.debtRemediationFunctions().linearWithOffset("1h", "1min"));
		    TleRepo.done();
		
	}

}
