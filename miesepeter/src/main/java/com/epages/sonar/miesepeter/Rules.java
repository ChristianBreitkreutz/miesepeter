package com.epages.sonar.miesepeter;

import static org.sonar.api.server.rule.RulesDefinition.SubCharacteristics.INTEGRATION_TESTABILITY;

import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RulesDefinition;


public class Rules implements RulesDefinition{

	@Override
	public void define(Context context) {
		    NewRepository TleRepo = context.createRepository("EpagesTemplateLanguage", "tle").setName("TLE logic");
		    setRule(TleRepo, "IF", "10");
		    setRule(TleRepo, "ELSIF", "10");
		    setRule(TleRepo, "ELSE", "10");
		    setRule(TleRepo, "REM", "10");
		    setRule(TleRepo, "BLOCK", "10");
		    setRule(TleRepo, "WITH", "10");
		    setRule(TleRepo, "WITH_ERROR", "10");
		    setRule(TleRepo, "FUNCTION", "10");
		    setRule(TleRepo, "MENU", "10");
		    setRule(TleRepo, "CALCULATE", "10");
		    setRule(TleRepo, "PROGRESS", "10");
		    setRule(TleRepo, "LogicElement", "10");
		    setRule(TleRepo, "general", "10");
		    NewRule tLElogic_LOCAL = TleRepo.createRule("local")
		    		.setName("TLE locale and set")
		    		.setHtmlDescription("use of #LOCAL and #SET points to programming logic in template, belongs in controller")
		    		.setSeverity(Severity.MAJOR);
		    tLElogic_LOCAL.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    		.setDebtRemediationFunction(tLElogic_LOCAL.debtRemediationFunctions().constantPerIssue("10min"));
		    NewRule tlelogic_lonelySet = TleRepo.createRule("lonelySet")
		    		.setName("set without local (loose of context)")
		    		.setHtmlDescription("#SET without #LOCAL (high risk of site effects)")
		    		.setSeverity(Severity.CRITICAL);
		    tlelogic_lonelySet.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    		.setDebtRemediationFunction(tlelogic_lonelySet.debtRemediationFunctions().constantPerIssue("10min"));
		    // loop issues
		    NewRule tlelogicNestedLoop = TleRepo.createRule("nestedLoop")
		    		.setName("nested loop")
		    		.setHtmlDescription("bad complexity")
		    		.setSeverity(Severity.MAJOR);
		    tlelogicNestedLoop.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    .setDebtRemediationFunction(tlelogicNestedLoop.debtRemediationFunctions().constantPerIssue("10min"));
		    
		    NewRule tlelogicLoopWithSet = TleRepo.createRule("loopWithSet")
		    		.setName("loop with set")
		    		.setHtmlDescription("loop with set points to progamming with TLE")
		    		.setSeverity(Severity.CRITICAL);
		    tlelogicLoopWithSet.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    .setDebtRemediationFunction(tlelogicLoopWithSet.debtRemediationFunctions().constantPerIssue("10min"));
		    // javascriptIssue Issue
		    NewRule javascriptInTemplate = TleRepo.createRule("javascriptInTemplate")
		    		.setName("javascript in template")
		    		.setHtmlDescription("javascript")
		    		.setSeverity(Severity.MAJOR);
		    javascriptInTemplate.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    .setDebtRemediationFunction(javascriptInTemplate.debtRemediationFunctions().constantPerIssue("59min"));
		    TleRepo.done();
		
	}

	private void setRule(NewRepository TleRepo, String name, String timeInMinutes) {
		NewRule TLElogic = TleRepo.createRule(name)
				.setName("TLE: "+name)
				.setHtmlDescription("TLE: "+name)
				.setSeverity(Severity.INFO);
		TLElogic.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		        .setDebtRemediationFunction(TLElogic.debtRemediationFunctions().constantPerIssue(timeInMinutes+"min"));
	}

}
