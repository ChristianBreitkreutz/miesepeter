package com.epages.sonar.miesepeter;

import static org.sonar.api.server.rule.RulesDefinition.SubCharacteristics.INTEGRATION_TESTABILITY;

import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RulesDefinition;


public class Rules implements RulesDefinition{

	@Override
	public void define(Context context) {
		    NewRepository tleRepo = context.createRepository("EpagesTemplateLanguage", "tle").setName("TLE logic");
		    setRule(tleRepo, "IF", "10");
		    setRule(tleRepo, "ELSIF", "10");
		    setRule(tleRepo, "ELSE", "10");
		    setRule(tleRepo, "REM", "10");
		    setRule(tleRepo, "BLOCK", "10");
		    setRule(tleRepo, "WITH", "10");
		    setRule(tleRepo, "WITH_ERROR", "10");
		    setRule(tleRepo, "FUNCTION", "10");
		    setRule(tleRepo, "MENU", "10");
		    setRule(tleRepo, "CALCULATE", "10");
		    setRule(tleRepo, "PROGRESS", "10");
		    setRule(tleRepo, "LogicElement", "10");
		    setRule(tleRepo, "general", "10");
		    NewRule tleLogicLocal = tleRepo.createRule("local")
		    		.setName("TLE locale and set")
		    		.setHtmlDescription("use of #LOCAL and #SET points to programming logic in template, belongs in controller")
		    		.setSeverity(Severity.MAJOR);
		    tleLogicLocal.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    		.setDebtRemediationFunction(tleLogicLocal.debtRemediationFunctions().constantPerIssue("10min"));
		    NewRule tleLogicLonelySet = tleRepo.createRule("lonelySet")
		    		.setName("set without local (loose of context)")
		    		.setHtmlDescription("#SET without #LOCAL (high risk of site effects)")
		    		.setSeverity(Severity.CRITICAL);
		    tleLogicLonelySet.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    		.setDebtRemediationFunction(tleLogicLonelySet.debtRemediationFunctions().constantPerIssue("10min"));
		    // loop issues
		    NewRule tlelogicNestedLoop = tleRepo.createRule("nestedLoop")
		    		.setName("nested loop")
		    		.setHtmlDescription("bad complexity")
		    		.setSeverity(Severity.MAJOR);
		    tlelogicNestedLoop.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    .setDebtRemediationFunction(tlelogicNestedLoop.debtRemediationFunctions().constantPerIssue("10min"));
		    
		    NewRule tlelogicLoopWithSet = tleRepo.createRule("loopWithSet")
		    		.setName("loop with set")
		    		.setHtmlDescription("loop with set points to progamming with TLE")
		    		.setSeverity(Severity.CRITICAL);
		    tlelogicLoopWithSet.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    .setDebtRemediationFunction(tlelogicLoopWithSet.debtRemediationFunctions().constantPerIssue("10min"));
		    // javascriptIssue Issue
		    NewRule javascriptInTemplate = tleRepo.createRule("javascriptInTemplate")
		    		.setName("javascript in template")
		    		.setHtmlDescription("javascript")
		    		.setSeverity(Severity.MAJOR);
		    javascriptInTemplate.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		    .setDebtRemediationFunction(javascriptInTemplate.debtRemediationFunctions().constantPerIssue("59min"));
		    tleRepo.done();
		
	}

	private void setRule(NewRepository tleRepo, String name, String timeInMinutes) {
		NewRule tleLogic = tleRepo.createRule(name)
				.setName("TLE: "+name)
				.setHtmlDescription("TLE: "+name)
				.setSeverity(Severity.INFO);
		tleLogic.setDebtSubCharacteristic(INTEGRATION_TESTABILITY)
		        .setDebtRemediationFunction(tleLogic.debtRemediationFunctions().constantPerIssue(timeInMinutes+"min"));
	}

}
