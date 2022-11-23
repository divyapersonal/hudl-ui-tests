package com.hudl.test.ui.model;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class BasePage {
	private WebDriver webDriver;

	private static final int DEFAULT_WAIT_TIME_SECS = 15;

	protected BasePage(WebDriver webDriver) {
		this.webDriver = webDriver;
		waitForPageLoad();
	}

	protected WebDriver getWebDriver() {
		return webDriver;
	}

	protected void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	/**
	 * Checks that the page has loaded successfully by checking for the presence of
	 * key elements.
	 * 
	 * @param wait
	 */
	public abstract boolean isPageLoaded(boolean wait);

	public String getUrl() {
		waitForPageLoad();
		return webDriver.getCurrentUrl();
	}

	public void refreshPage() {
		webDriver.navigate().refresh();
	}

	public void waitForPageLoad() {
		webDriver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
	}

	protected WebElement waitForElement(WebElement elementToWaitFor) {
		return waitForElement(elementToWaitFor, null);
	}

	protected WebElement waitForElement(WebElement elementToWaitFor, Integer waitTimeInSeconds) {
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = DEFAULT_WAIT_TIME_SECS;
		}

		// If a DOM operation happening on the page is temporarily causing the element
		// to be inaccessible, it can cause stale element reference exception. So it has
		// to be ignored in such cases.
		FluentWait<WebDriver> wait = new FluentWait<>(webDriver).withTimeout(Duration.ofSeconds(waitTimeInSeconds))
				.pollingEvery(Duration.ofMillis(200))
				.ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
		return wait.until(ExpectedConditions.visibilityOf(elementToWaitFor));
	}

	protected Boolean waitForTextInElement(WebElement elementToWaitFor, Integer waitTimeInSeconds, String text) {
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = DEFAULT_WAIT_TIME_SECS;
		}
		FluentWait<WebDriver> wait = new FluentWait<>(webDriver).withTimeout(Duration.ofSeconds(waitTimeInSeconds))
				.pollingEvery(Duration.ofMillis(200)).ignoring(StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.textToBePresentInElement(elementToWaitFor, text));
	}

	protected List<WebElement> waitForElements(List<WebElement> elementsToWaitFor) {
		return waitForElements(elementsToWaitFor, null);
	}

	protected List<WebElement> waitForElements(List<WebElement> elementsToWaitFor, Integer waitTimeInSeconds) {
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = DEFAULT_WAIT_TIME_SECS;
		}

		FluentWait<WebDriver> wait = new FluentWait<>(webDriver).withTimeout(Duration.ofSeconds(waitTimeInSeconds))
				.pollingEvery(Duration.ofMillis(200)).ignoring(StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.visibilityOfAllElements(elementsToWaitFor));
	}

	protected List<WebElement> waitForPresenceOfElements(By elementsToWaitFor) {
		return waitForPresenceOfElements(elementsToWaitFor, null);
	}

	protected List<WebElement> waitForPresenceOfElements(By elementsToWaitFor, Integer waitTimeInSeconds) {
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = DEFAULT_WAIT_TIME_SECS;
		}

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(waitTimeInSeconds));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementsToWaitFor));
	}

	protected List<WebElement> waitForElements(By elementLocator) {
		return waitForElements(elementLocator, null);
	}

	protected List<WebElement> waitForElements(By elementLocator, Integer waitTimeInSeconds) {
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = DEFAULT_WAIT_TIME_SECS;
		}

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(waitTimeInSeconds));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementLocator));
	}

	protected WebElement waitForElement(By elementLocator) {
		return waitForElement(elementLocator, null);
	}

	protected WebElement waitForElement(By elementLocator, Integer waitTimeInSeconds) {
		if (waitTimeInSeconds == null) {
			waitTimeInSeconds = DEFAULT_WAIT_TIME_SECS;
		}

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(waitTimeInSeconds));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
	}

	protected boolean waitForElementNotPresent(By elementLocator) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(DEFAULT_WAIT_TIME_SECS));
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
	}

	protected WebElement waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(DEFAULT_WAIT_TIME_SECS));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	protected boolean waitForElementNotPresent(WebElement element) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(DEFAULT_WAIT_TIME_SECS));
		return wait.until(ExpectedConditions.invisibilityOfAllElements(Collections.singletonList(element)));
	}

	protected void waitForURLToChange(String URLKeyword, Integer waitTimeInSeconds) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(waitTimeInSeconds));
		ExpectedCondition<Boolean> urlIsCorrect = arg0 -> webDriver.getCurrentUrl().contains(URLKeyword);
		wait.until(urlIsCorrect);
	}

	protected boolean isElementPresent(WebElement element) {
		if (element.isDisplayed())
			return true;
		else
			return false;
	}

	public boolean isLinkPresentAndClickable(String linkName) {
		scrollElementIntoView(webDriver.findElement(By.partialLinkText(linkName)));
		WebElement linkElement = waitForElement(By.partialLinkText(linkName),20);
		return linkElement.isDisplayed() && linkElement.isEnabled();
	}
	
	public boolean isImageLinkPresentAndClickable(String imageLinkName) {
		scrollElementIntoView(webDriver.findElement(By.xpath("//span[contains(text(),'" + imageLinkName + "')]/..")));
		WebElement linkElement = waitForElement(By.xpath("//span[contains(text(),'" + imageLinkName + "')]/.."));
		return linkElement.isDisplayed() && linkElement.isEnabled();
	}
	
	public boolean isButtonPresentAndDisabled(String label) {
		if(isButtonPresent(label)&&!(isButtonEnabled(label)))
			return true;
		else return false;
	}

	protected WebElement getButton(String label) {
		scrollElementIntoView(webDriver.findElement(By.xpath("//button[contains(.,'" + label + "')]")));
		return waitForElement((By.xpath("//button[contains(.,'" + label + "')]")));
	}

	protected WebElement getLabelOption(String label) throws InterruptedException {
		scrollElementIntoView(webDriver.findElement(By.xpath("//label[contains(.,'" + label + "')]")));
		return waitForElement(By.xpath("//label[contains(.,'" + label + "')]"));
	}

	protected WebElement getLink(String label) {
		if(label.contains("Continue verification")||label.contains("Withdraw funds")) {
			scrollElementIntoView(webDriver.findElement(By.partialLinkText(label)));
			Actions actions = new Actions(webDriver);
			actions.moveToElement(webDriver.findElement(By.partialLinkText(label)));
			actions.sendKeys(Keys.PAGE_UP);
			actions.build().perform();
		}
		scrollElementIntoView(waitForElementToBeClickable(webDriver.findElement(By.partialLinkText(label))));
		return waitForElement(By.partialLinkText(label));
	}
	
	protected WebElement getLink(String label, int number) {
		List<WebElement> links = webDriver.findElements(By.partialLinkText(label));
		scrollElementIntoView(waitForElement(links.get(number-1)));
		return waitForElement(links.get(number-1));
	}

	public boolean isButtonPresent(String label) {
		WebElement buttonElement = getButton(label);
		return buttonElement.isDisplayed();
	}

	public boolean isButtonEnabled(String label) {
		WebElement buttonElement = getButton(label);
		return buttonElement.isDisplayed() && buttonElement.isEnabled();
	}

	public void clickElementByText(String label) throws InterruptedException {
		scrollElementIntoView(webDriver.findElement(By.xpath("//span[contains(text(),'" + label + "')]")));
		WebElement element = waitForElement(By.xpath("//span[contains(text(),'" + label + "')]"));
		element.click();
	}
	
	public void enterTextInTextbox(String textToType, String textboxLabel, int number) {
		
		if(textboxLabel.contains("Contact")||textboxLabel.contains("phone")) {
			List<WebElement> textboxes = webDriver.findElements(By.xpath("//label[contains(text(),'" + textboxLabel + "')]/following-sibling::div/input"));
			scrollElementIntoView(textboxes.get(number-1));
			WebElement element = waitForElement(textboxes.get(number-1));
			element.sendKeys(textToType);
		}
		else if(textboxLabel.contains("Date")) {
			List<WebElement> textboxes = webDriver.findElements(By.xpath("//label[contains(text(),'" + textboxLabel + "')]/following-sibling::span/span[1]/input"));
			scrollElementIntoView(textboxes.get(number-1));
			WebElement element = waitForElement(textboxes.get(number-1));
			element.sendKeys(textToType);
			}
				
		else {
		List<WebElement> textboxes = webDriver.findElements(By.xpath("//label[contains(text(),'" + textboxLabel + "')]/following-sibling::input"));
		scrollElementIntoView(textboxes.get(number-1));
		WebElement element = waitForElement(textboxes.get(number-1));
		element.sendKeys(textToType);
		}
		
	}

	public boolean findElementByText(String label) throws InterruptedException {
		return findElementByText(label, false);
	}

	public boolean findElementByText(String label, boolean checkClickable) throws InterruptedException {
		if (checkClickable == false)
			try {
				scrollElementIntoView(waitForElement(By.xpath("//span[contains(text(),'" + label + "')]")));
				return true;
			} catch (Exception e) {
				return false;
			}
		else
			try {
				scrollElementIntoView(waitForElement(By.xpath("//span[contains(text(),'" + label + "')]")));
				return getWebDriver().findElement(By.xpath("//span[contains(text(),'" + label + "')]")).isEnabled();

			} catch (Exception e) {
				return false;
			}
	}

	public boolean isFieldPresent(String fieldName) throws InterruptedException {
		WebElement element = waitForElement(By.xpath("//input[@name='" + fieldName + "']"));
		return element.isDisplayed() && element.isEnabled();
	}

	public void clickButton(String label) throws InterruptedException {
		
			WebElement buttonElement = getButton(label);
			if (isButtonPresent(label)) {
				buttonElement.click();
			}
		
	}

	public boolean isLabelOptionPresent(String label) throws InterruptedException {
		WebElement labelElement = getLabelOption(label);
		return labelElement.isDisplayed();
	}

	public boolean isLabelOptionEnabled(String label) throws InterruptedException {
		WebElement labelOptionElement = getLabelOption(label);
		return labelOptionElement.isDisplayed() && labelOptionElement.isEnabled();
	}

	public void clickLabelOption(String label) throws InterruptedException {
		WebElement labelOptionElement = getLabelOption(label);
		if (isLabelOptionPresent(label)) {
			labelOptionElement.click();
		}
	}

	public void clickLink(String label) throws InterruptedException {
		
		WebElement linkElement = getLink(label);
		if (isLinkPresentAndClickable(label)) {
			linkElement.click();
		} 
	}
	
	public void clickLink(String label, int number) throws InterruptedException {
		WebElement linkElement = getLink(label,number);
		if (isLinkPresentAndClickable(label)) {
			linkElement.click();
		}
	}

	public WebElement getModal() {
		return webDriver.switchTo().activeElement();
	}

	public void getDefaultWindow() {
		webDriver.switchTo().defaultContent();
	}

	protected void scrollElementIntoView(WebElement element) {
		((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	protected void scrollElementIntoViewAndClick(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", element);
	}

	protected void scrollElementIntoViewInModal(WebElement element) {
		((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].scrollTop = arguments[1];", element, 150);
	}

	protected void scrollElementIntoViewToTypeText(WebElement element, String textToType) {
		String js = "arguments[0].setAttribute('title','" + textToType + ".00" + "')";
		((JavascriptExecutor) getWebDriver()).executeScript(js, element);
	}
	
	protected void setAttributeValue(String attributeName, String attributeValue, String id) {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("document.getElementById('"+id+"').setAttribute('"+attributeName+"', '"+attributeValue+"')");
	}
	
	protected void changeDisplayStyleFromHidden(String id) throws InterruptedException {
		((JavascriptExecutor) getWebDriver())
				.executeScript("document.getElementById('" + id + "').style.display='block';");
		Thread.sleep(2000);
	}
	
	protected void scrollElement(WebElement element) {
		// Scroll by clicking on the list and sending a couple of page downs
		Actions actions = new Actions(webDriver);
		actions.moveToElement(element);
		actions.click();
		// actions.sendKeys(Keys.PAGE_DOWN);
		// actions.sendKeys(Keys.PAGE_DOWN);
		actions.build().perform();
	}
	
	protected void scrollPageUp(WebElement element) {
		// Scroll by clicking on the list and sending a couple of page downs
		Actions actions = new Actions(webDriver);
		actions.moveToElement(element);
		actions.sendKeys(Keys.PAGE_UP);
		//actions.sendKeys(Keys.PAGE_UP);
		actions.build().perform();
	}

	public void saveScreenshot(String fileName) {
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(fileName));
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	public WebElement getDropdownWithSpan(String dropdownLabel) throws InterruptedException {
		Thread.sleep(2000);
		scrollElementIntoView(webDriver
				.findElement(By.xpath("//label[contains(.,'" + dropdownLabel + "')]/following-sibling::span")));
		return waitForElement(By.xpath("//label[contains(.,'" + dropdownLabel + "')]/following-sibling::span"));
	}
	
	public WebElement getDropdownWithSpan(String dropdownLabel, int number) throws InterruptedException {
		Thread.sleep(2000);
		List<WebElement> dropdowns = webDriver
				.findElements(By.xpath("//label[contains(.,'" + dropdownLabel + "')]/following-sibling::span"));
		scrollElementIntoView(dropdowns.get(number-1));
		return waitForElement(dropdowns.get(number-1));
	}

	public WebElement getDropdownWithDiv(String dropdownLabel) throws InterruptedException {
		Thread.sleep(2000);
		scrollElementIntoView(webDriver
				.findElement(By.xpath("//label[contains(.,'" + dropdownLabel + "')]/following-sibling::div[1]")));
		return waitForElement(By.xpath("//label[contains(.,'" + dropdownLabel + "')]/following-sibling::div[1]"));
	}
	
	public void clickDropdown(String label, int number) throws InterruptedException {
		getDropdownWithSpan(label, number).click();		
	}

	public void typeInTextBox(String text) {
		Actions actions = new Actions(webDriver);
		actions.sendKeys(text);
		actions.sendKeys(Keys.ENTER);
	}

	public void typeInDropdown(String text) throws InterruptedException {
		if (text.contains("Select bank"))
			Thread.sleep(2000);
		WebElement dropdown = webDriver.findElement(By.cssSelector("ul[aria-hidden=false]"));
		List<WebElement> dropdownList = waitForElements(dropdown.findElements(By.tagName("li")));
		for (WebElement li : dropdownList) {
			waitForElement(li);
			if (li.getText().contains(text)) {
				li.click();
				break;
			}
		}
	}

	public boolean isDropdownPresent(String dropdownLabel) throws InterruptedException {
		if (webDriver.getCurrentUrl().contains("Register")) {
			WebElement element = getDropdownWithDiv(dropdownLabel);
			return element.isDisplayed() && element.isEnabled();
		} else {
			WebElement element = getDropdownWithSpan(dropdownLabel);
			return element.isDisplayed() && element.isEnabled();
		}

	}
	
	public void clickCheckbox(String label, int number) throws InterruptedException {
		Thread.sleep(2000);
		List<WebElement> checkboxes = webDriver
				.findElements(By.xpath("//span[contains(.,'" + label + "')]/preceding-sibling::span"));
		scrollElementIntoView(checkboxes.get(number-1));
		waitForElement(checkboxes.get(number-1)).click();	
	}
	
	public void typeInAutoFillDropdown(String autoFillBoxLabel, int number, String textToType) throws InterruptedException {
		WebElement page = webDriver.findElement(By.className("id-check"));
		List<WebElement> autoFillTextBoxes= page.findElements(By.xpath("//input[contains(@id,'addressSearchid-')]"));
		scrollElementIntoView(autoFillTextBoxes.get(number-1));
		autoFillTextBoxes.get(number-1).click();
		autoFillTextBoxes.get(number-1).sendKeys(textToType);
		Thread.sleep(1500);
		autoFillTextBoxes.get(number-1).sendKeys(Keys.DOWN);
		autoFillTextBoxes.get(number-1).sendKeys(Keys.DOWN);
		autoFillTextBoxes.get(number-1).sendKeys(Keys.ENTER);
		autoFillTextBoxes.get(number-1).sendKeys(Keys.ENTER);
		
	}
	
	public void checkPageUrl(String expectedUrl) throws InterruptedException {

		waitForPageLoad();
		Thread.sleep(2000);
		assertTrue(getUrl().toLowerCase().contains(expectedUrl.toLowerCase()));
	}

}
