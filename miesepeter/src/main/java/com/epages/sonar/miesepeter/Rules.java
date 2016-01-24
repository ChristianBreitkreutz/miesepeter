package com.epages.sonar.miesepeter;

import org.sonar.api.rule.Severity;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.server.rule.RulesDefinition;


public class Rules implements RulesDefinition{

	@Override
	public void define(Context context) {
		    NewRepository TleRepo = context.createRepository("EpagesTemplateLanguage", "tle").setName("TLE logic");
		    NewRule TLElogic = TleRepo.createRule("general")
		    		.setName("all kind of TLE Elements")
		    		.setHtmlDescription("collect all kind of TLE elements from templates")
		    		.setSeverity(Severity.MINOR);
		    TLElogic.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		            .setDebtRemediationFunction(TLElogic.debtRemediationFunctions().constantPerIssue("5min"));
		    NewRule tLElogic_LOCAL = TleRepo.createRule("local")
		    		.setName("TLE locale and set")
		    		.setHtmlDescription("use of #LOCAL and #SET points to programming logic in template, belongs in controller")
		    		.setSeverity(Severity.MAJOR);
		    tLElogic_LOCAL.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		    		.setDebtRemediationFunction(TLElogic.debtRemediationFunctions().constantPerIssue("5min"));
		    NewRule tlelogic_lonelySet = TleRepo.createRule("lonelySet")
		    		.setName("set without local (loose of context)")
		    		.setHtmlDescription("#SET without #LOCAL (high risk of site effects)")
		    		.setSeverity(Severity.CRITICAL);
		    tlelogic_lonelySet.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		    		.setDebtRemediationFunction(TLElogic.debtRemediationFunctions().constantPerIssue("10min"));
		    // loop issues
		    NewRule tlelogicNestedLoop = TleRepo.createRule("nestedLoop")
		    		.setName("nested loop")
		    		.setHtmlDescription("bad complexity")
		    		.setSeverity(Severity.MAJOR);
		    tlelogicNestedLoop.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		    .setDebtRemediationFunction(TLElogic.debtRemediationFunctions().constantPerIssue("10min"));
		    
		    NewRule tlelogicLoopWithSet = TleRepo.createRule("loopWithSet")
		    		.setName("loop with set")
		    		.setHtmlDescription("loop with set points to progamming with TLE")
		    		.setSeverity(Severity.CRITICAL);
		    tlelogicLoopWithSet.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		    .setDebtRemediationFunction(TLElogic.debtRemediationFunctions().constantPerIssue("10min"));
		    // javascriptIssue Issue
		    NewRule javascriptInTemplate = TleRepo.createRule("javascriptInTemplate")
		    		.setName("javascript in template")
		    		.setHtmlDescription("javascript")
		    		.setSeverity(Severity.MAJOR);
		    javascriptInTemplate.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
		    .setDebtRemediationFunction(TLElogic.debtRemediationFunctions().constantPerIssue("30min"));
		    TleRepo.done();
		
	}

}
