package com.ibm.biginsights.g2t.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory
{
    public static WebDriver getSeleniumWebDriver(Browsers browser)
    {
        WebDriver driver = new FirefoxDriver();
        return driver;
    }
}
