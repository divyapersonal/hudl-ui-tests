package com.hudl.test.ui.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.FactoryBean;

public class WebDriverFactory implements FactoryBean<WebDriver> {

	private String browserName;

	private String driverPath;

	@Override
	public WebDriver getObject() throws Exception {
		WebDriver webDriver;
		switch (browserName) {
		case "CHROME":
			webDriver = createChromeDriver();
			break;
		case "SAFARI":
			webDriver = createSafariDriver();
			break;
		case "EDGE":
			webDriver = createEdgeDriver();
			break;
		case "FIREFOX":
		default:
			webDriver = createFirefoxDriver();
			break;
		}

		webDriver.manage().window().maximize();
		return webDriver;
	}

	@Override
	public Class<?> getObjectType() {
		return WebDriver.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private WebDriver createSafariDriver() {
		return new SafariDriver();
	}

	private WebDriver createChromeDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary(driverPath + "\\chromedriver.exe");

		return new ChromeDriver(chromeOptions);
	}

	private WebDriver createEdgeDriver() {
		System.setProperty("webdriver.edge.driver", driverPath + "\\msedgedriver.exe");
		return new EdgeDriver();
	}

	private WebDriver createFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", driverPath + "\\geckodriver.exe");
		return new FirefoxDriver();
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}
}
