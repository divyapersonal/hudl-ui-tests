package com.hudl.test.ui.core.driver;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * This class exists to destroy the web driver instance after the context has stopped i.e. we're all done
 * testing.
 */
public class ApplicationClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private WebDriver webDriver;

    @Override
    public void onApplicationEvent(ContextClosedEvent applicationEvent) {
        // Comment this line out if you want the browser to stay open after tests run for debugging purposes
        webDriver.quit();
    }
}
