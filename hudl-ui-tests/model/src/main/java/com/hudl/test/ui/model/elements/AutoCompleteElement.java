package com.hudl.test.ui.model.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.hudl.test.ui.model.BasePage;

public class AutoCompleteElement extends BasePage {

    private WebElement element;

    private By elementSelector;

    //update the below value to the auto complete element selector
    private By inputSelector = By.cssSelector("");


    /**
     * Constructor
     * @param element The parent element e.g. app-centre-auto-complete, app-property-set-auto-complete etc.
     */
    public AutoCompleteElement(WebDriver webDriver, WebElement element) {
        super(webDriver);

        this.element = element;
        this.elementSelector = null;
    }

    public AutoCompleteElement(WebDriver webDriver, By selector) {
        super(webDriver);

        this.element = null;
        this.elementSelector = selector;
    }

    @Override
    public boolean isPageLoaded(boolean wait) {
        if (wait) {
            waitForElement(getElement());
        }

        return getElement().isDisplayed() && getElement().isEnabled();
    }

    public int getItemCount() {
		List<WebElement> itemList = getElement().findElements(By.tagName("li"));
		int count = 0;
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).isDisplayed())
				count++;
		}
		return count;
    }

    public String getSelectedText() {
        WebElement input =  getElement().findElement(inputSelector);
        waitForElement(input);

        return input.getText();
    }

    public int enterText(String text) {
        // Select the first item by default
        return enterTextAndSelectItem(text, 1);
    }

    private int enterTextAndSelectItem(String text, int itemToSelect) {
        int matchCount;

        WebElement inputElement = getElement().findElement(inputSelector);
        waitForElement(inputElement);

        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(inputElement);
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();

		List<WebElement> items = new ArrayList<WebElement>();

        // Wait for drop down if auto-complete and return count of matched items
        try {
			
			items = getElement().findElements(By.tagName("li"));
            matchCount = items.size();

            // Select the specified item
            if (itemToSelect > 0) {
				if (items.get(--itemToSelect).isDisplayed())
				{
					waitForElement(items.get(itemToSelect));
					items.get(itemToSelect).click();
				}

            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            matchCount = 0;
        }

        return matchCount;
    }

    private WebElement getElement() {
        if (element == null) {
            element = waitForElement(elementSelector);
        }

        return element;
    }
}

