package com.hudl.test.ui.model;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	@FindBy(xpath = "//label[contains(@data-qa-id,'remember-me-checkbox-label')]")
	private WebElement rememberMeCheckBox;
	
	@FindBy(xpath = "//div[contains(@class,'hui-globaluseritem__display-name')]")
	private WebElement displayNameCollapser;

	@FindBy(partialLinkText = "Sign up")
	private WebElement signUpLink;
	
	@FindBy(partialLinkText = "Log Out")
	private WebElement logoutLink;
	
	@FindBy(partialLinkText = "Log in")
	private WebElement loginLink;

	@FindBy(id = "email")
	private WebElement emailInput;

	@FindBy(id = "password")
	private WebElement passwordInput;

	@FindBy(xpath = "//p[contains(@data-qa-id,'error-display')]")
	private WebElement loginErrorMessage;

	@FindBy(xpath = "//span[@for='Email']")
	private WebElement emailErrorMessage;

	@FindBy(xpath = "//span[@for='Password']")
	private WebElement passwordErrorMessage;

	public LoginPage(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public boolean isPageLoaded(boolean wait) {
		if (wait) {
			waitForElement(signUpLink);
		} else {
			return signUpLink.isDisplayed();
		}

		return true;
	}

	public void enterPassword(String password) {
		waitForElement(passwordInput);
		passwordInput.sendKeys(password);
	}

	public void enterEmail(String email) {
		waitForElement(emailInput);
		emailInput.sendKeys(email);
	}
	
	public void checkValidationErrors(String errormessage) {
		waitForElement(loginErrorMessage);
		assertTrue(loginErrorMessage.getText().contains(errormessage));
	}

	public void checkRememberMeCheckbox() {
		waitForElement(rememberMeCheckBox);
		assertTrue(rememberMeCheckBox.isDisplayed() && rememberMeCheckBox.isEnabled());
	}

	public void clickLogout() {
		displayNameCollapser.click();
		Actions actions = new Actions(getWebDriver());
        actions.moveToElement(displayNameCollapser);
        actions.build().perform();
        actions.moveToElement(logoutLink);
        actions.click();
        actions.build().perform();
	}

	public void verifyLogout() {
		waitForPageLoad();
		waitForElement(loginLink);
		assertTrue(loginLink.isDisplayed()&&loginLink.isEnabled());
	}
}
