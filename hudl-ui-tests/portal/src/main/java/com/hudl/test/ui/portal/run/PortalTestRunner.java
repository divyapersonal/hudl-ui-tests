package com.hudl.test.ui.portal.run;

import io.cucumber.testng.*;


@CucumberOptions(
        features = { "classpath:features/" },
        glue = { "com.hudl.test.ui.core.stepDefinition", "com.hudl.test.ui.portal.stepDefinition" },
        plugin = {
                "html:target/cucumber-reports/portal-test-report.html",
                "json:target/cucumber-json-reports/portal-test-json-report.json"
        },
		tags =  "@login" ,
        monochrome = true)
public class PortalTestRunner extends AbstractTestNGCucumberTests {
    // Empty
}
