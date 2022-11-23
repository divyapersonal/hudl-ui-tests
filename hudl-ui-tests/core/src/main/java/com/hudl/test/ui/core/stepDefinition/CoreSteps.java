package com.hudl.test.ui.core.stepDefinition;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hudl.test.ui.model.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CoreSteps {

	@Autowired
	private WebDriver webDriver;

	@Autowired
	private ConfigurationOptions configOptions;

	private LoginPage loginPage;

	@When("^I refresh the page$")
	public void i_refresh_the_page() {
		webDriver.navigate().refresh();
	}

	@When("^I go back a page$")
	public void i_go_back_a_page() throws Throwable {
		webDriver.navigate().back();
	}

	@Given("^I am on hudl \"([^\"]*)\" page$")
	public void i_m_on_hudl_page(String requestedPage) {
		if (requestedPage.contains("Login"))
			webDriver.manage().deleteAllCookies();
		String path = null;
		if (requestedPage.contains("Home")) {
			path = "";
			webDriver.manage().deleteAllCookies();
		}
		
		webDriver.get(configOptions.getLoginUrl() + path);
		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

	}

	@Given("^I am logged in to Hudl$")
	public void i_m_logged_in_to_hudl() throws InterruptedException {
		loginPage = PageFactory.initElements(webDriver, LoginPage.class);
		loginPage.enterEmail(configOptions.getUserName());
		loginPage.enterPassword(configOptions.getPassword());
		loginPage.clickButton("Log in");
		webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}


	@When("^I accept the alert$")
	public void i_accept_the_alert() {
		if (isAlertPresent()) {
			webDriver.switchTo().alert().accept();
			webDriver.switchTo().defaultContent();
		}
	}

	public void setConfigOptions(ConfigurationOptions configOptions) {
		this.configOptions = configOptions;
	}

	public ConfigurationOptions getConfigOptions() {
		return configOptions;
	}

	public boolean isAlertPresent() {
		try {
			webDriver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
}
