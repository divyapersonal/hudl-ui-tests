package com.hudl.test.ui.core.stepDefinition;

import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

	@Autowired
	private WebDriver webDriver;

	@Before
	public void beforeScenario() throws InterruptedException {
		// Empty
	}

	@After
	public void afterScenario(Scenario scenario) throws Exception {
		if (scenario.isFailed()) {
			try {
				
				byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png","Screenshot");
			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				System.err.println(somePlatformsDontSupportScreenshots.getMessage());
			}
		}
	}
}
