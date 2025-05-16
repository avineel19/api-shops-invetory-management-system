package com.dwp.runner;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")  // Points to src/test/resources/features
@ConfigurationParameter(key = "cucumber.glue", value = "com.dwp.stepdefinitions,com.dwp.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm, pretty, summary, html:target/cucumber-report.html")
public class TestRunner {
}
