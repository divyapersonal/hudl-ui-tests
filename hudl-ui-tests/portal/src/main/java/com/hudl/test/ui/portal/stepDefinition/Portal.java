package com.hudl.test.ui.portal.stepDefinition;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.hudl.test.ui.core.stepDefinition.CoreSteps;
import com.hudl.test.ui.model.DashboardPage;
import com.hudl.test.ui.model.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration("/portal-context.xml")
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
public class Portal implements InitializingBean {

	@Autowired
	private WebDriver webDriver;

	@Autowired
	private CoreSteps coreSteps;

	private DashboardPage dashboardPage;

	private LoginPage loginPage;

	@Override
	public void afterPropertiesSet() throws Exception {
		dashboardPage = PageFactory.initElements(webDriver, DashboardPage.class);
		loginPage = PageFactory.initElements(webDriver, LoginPage.class);
	}
	
	@When("^I type \"([^\"]*)\" in the \"([^\"]*)\" field$")
	public void i_type_something_in_the_something_field_in_login_page(String text, String fieldName) throws Throwable {
		if (fieldName.contains("Email")) {
			loginPage.enterEmail(text);
		} else if (fieldName.contains("Password")) {
			if(text.contains("******"))
				loginPage.enterPassword(coreSteps.getConfigOptions().getPassword());
			else
			    loginPage.enterPassword(text);
		}
	}
	
	@When("^I click the logout link$")
	public void i_click_the_logout_link() throws InterruptedException {
		loginPage.clickLogout();
	}
	
	@Then("I expect to be logged out successfully")
	public void i_expect_to_be_logged_out() {
		loginPage.verifyLogout();
	}
	
	@Then("I expect to be navigated to the hudl login page")
	public void i_expect_to_be_navigated_to_the_login_page() {
		loginPage.isPageLoaded(true);
	}
	
	@Then("I expect Remember me checkbox to be present and clickable")
	public void i_expect_remember_me_checkbox_to_be_present_and_clickable() {
	    loginPage.checkRememberMeCheckbox();
	}

	@Then("^I expect \"([^\"]*)\" button to be present and clickable$")
	public void i_expect_button_to_be_present_and_clickable(String button) throws Throwable {
		assertTrue(loginPage.isButtonPresent(button));
		assertTrue(loginPage.isButtonEnabled(button));
	}

	@Then("^I expect \"([^\"]*)\" link to be present and clickable$")
	public void i_expect_link_to_be_present_and_clickable(String link) throws Throwable {
		assertTrue(loginPage.isLinkPresentAndClickable(link));
	}

	@When("^I click the \"([^\"]*)\" button$")
	public void i_click_the_button(String buttonLabel) throws InterruptedException {
		loginPage.clickButton(buttonLabel);
	}

	@When("^I click the \"([^\"]*)\" link$")
	public void i_click_the_link(String linkText) throws InterruptedException {
		loginPage.clickLink(linkText);
	}
	
	@Then("^I expect \"([^\"]*)\" field to be present and clickable$")
	public void i_expect_field_to_be_present_and_clickable(String fieldName) throws Throwable {
		loginPage.isFieldPresent(fieldName);
	}

	@Then("^I expect the url to contain \"([^\"]*)\"$")
	public void i_expect_the_url_to_contain_something(String expectedUrl) throws Throwable {
		loginPage.checkPageUrl(expectedUrl);
	}

	
	@Then("^I expect a login error with the message \"([^\"]*)\"$")
	public void i_expect_login_error_message(String errormessage)
			throws Throwable {
		loginPage.checkValidationErrors(errormessage);
	}

	@Then("^I expect to be navigated to the dashboard page$")
	public void i_expect_to_be_navigated_to_the_dashboard_page() throws Throwable {
		dashboardPage.isPageLoaded(true);
	}

	@Then("^I expect \"([^\"]*)\" dropdown to be present and clickable$")
	public void i_expect_dropdown_to_be_present_and_clickable(String dropdownLabel) throws Throwable {
		assertTrue(loginPage.isDropdownPresent(dropdownLabel));
	}


}
