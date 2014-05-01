package com.ibm.biginsights.g2t.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverFactory
{
    public static WebDriver getSeleniumWebDriver(Browsers browser)
    {
        switch (browser) {
          case FIREFOX:
            return new FirefoxDriver();
          case IE:
            return new InternetExplorerDriver();
          default:
            return new FirefoxDriver();
        }
    }
}
