package com.ibm.biginsights.g2t.screen;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotTaker
{
    private int screenshotIndex;

    private final WebDriver webDriver;
    private final String screenshotFoler;
    private boolean isPlaybackMode;

    public ScreenshotTaker(WebDriver webDriver, String screenshotFoler, boolean isPlaybackMode)
    {
        this.screenshotIndex = 0;
        this.webDriver = webDriver;
        this.screenshotFoler = screenshotFoler;
        this.isPlaybackMode = isPlaybackMode;
    }

    public String takeScreenshot() throws IOException
    {
        try
        {
            System.out.println("Taking screenshot...");
            File screenFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            String screenshotFilename = getScreenshotFilename();
            FileUtils.copyFile(screenFile, new File(screenshotFoler + screenshotFilename));
            System.out.println("Took screenshot: " + screenshotFilename);
            screenshotIndex++;
            return screenshotFilename;
        } catch (IOException e)
        {
            throw e;
        }
    }

    private String getScreenshotFilename()
    {
        if (isPlaybackMode)
        {
            return SCREENSHOT_ + getScreenshotIndex() + _PLAYBACK + PNG;
        }
        else
        {
            return SCREENSHOT_ + getScreenshotIndex() + PNG;
        }
    }

    public int getScreenshotIndex()
    {
        return screenshotIndex;
    }

    public void setScreenshotIndex(int screenshotIndex)
    {
        this.screenshotIndex = screenshotIndex;
    }

    public boolean isPlaybackMode()
    {
        return isPlaybackMode;
    }

    public void setPlaybackMode(boolean isPlaybackMode)
    {
        if (isPlaybackMode)
        {
            // When start playback mode, reset the screenshot
            // index to 0.
            setScreenshotIndex(0);
        }
        this.isPlaybackMode = isPlaybackMode;
    }

    public WebDriver getWebDriver()
    {
        return webDriver;
    }

    public String getScreenshotFoler()
    {
        return screenshotFoler;
    }
}
