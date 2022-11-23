package com.hudl.test.ui.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class DashboardPage extends BasePage {

	@FindBy(partialLinkText = "Home")
	private WebElement homeLink;
	
	@FindBy(partialLinkText = "Explore")
	private WebElement exploreLink;

	public DashboardPage(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public boolean isPageLoaded(boolean wait) {
		if (wait) {
			waitForPageLoad();
			waitForElement(homeLink, 30);
			waitForElement(exploreLink, 30);
		}

		return true;
	}

}
