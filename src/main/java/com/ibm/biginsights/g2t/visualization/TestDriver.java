package com.ibm.biginsights.g2t.visualization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.json.JSONException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.ibm.biginsights.g2t.record.ActionRecorder;
import com.ibm.biginsights.g2t.record.EventSequenceWriter;
import com.ibm.biginsights.g2t.replay.ActionReplayer;
import com.ibm.biginsights.g2t.replay.EventSequenceReader;
import com.ibm.biginsights.g2t.screen.ScreenResolution;
import com.ibm.biginsights.g2t.screen.ScreenshotTaker;
import com.ibm.biginsights.g2t.visualization.exception.G2TVisualizationTestException;
import com.ibm.biginsights.g2t.visualization.exception.ScreenResolutionParseErrorException;
import com.ibm.biginsights.g2t.web.Browsers;
import com.ibm.biginsights.g2t.web.WebDriverFactory;

public class TestDriver
{

    private static final String NAME = "name";
    private static final String MODE = "mode";
    private static final String ACTION_RECORDER = "ActionRecorder";
    private static final int DEFAULT_H = 768;
    private static final int DEFAULT_W = 1024;
    private static final String SCREENSIZE = "screensize";
    private static final String URL = "url";
    private static final String SCREENSHOTS_FOLDER_NAME = "screenshots";
    private static final String TEST_CONFIG_FILE_NAME = "test.config";
    private static final String ACTION_FILE_NAME = "actions.json";
    private static final String TEST_FOLDER = "test_folder";
    private static final String BROWSER_TYPE = "browser";
    private static final Browsers DEFAULT_BROWSER = Browsers.FIREFOX;

    public static void main(String[] args)
    {
        int testResult = 0;
        WebDriver webDriver = null;
        Properties testConfig = null;
        try
        {
            System.out.println("Starting test, please wait.");

            Options options = createCommandLineOptions();

            CommandLine line = new GnuParser().parse(options, args);

            String modeString = line.getOptionValue(MODE);

            Modes mode = Modes.valueOf(modeString.toUpperCase());

            String testFolder = line.getOptionValue(TEST_FOLDER);

            String testConfigFilePath = testFolder + "/" + TEST_CONFIG_FILE_NAME;

            testConfig = getTestConfig(testConfigFilePath);

            String actionFilePath = testFolder + "/" + ACTION_FILE_NAME;
            String screenshotFolder = testFolder + "/" + SCREENSHOTS_FOLDER_NAME + "/";

            String url = testConfig.getProperty(URL);
            ScreenResolution screenResolution = getScreenResolutionFromConfig(testConfig.getProperty(SCREENSIZE));

            Browsers browser = DEFAULT_BROWSER;
            String browserTypeString = line.getOptionValue(BROWSER_TYPE);
            if (browserTypeString != null && !browserTypeString.isEmpty())
            {
                browser = Browsers.valueOf(browserTypeString.toUpperCase());
            }

            webDriver = WebDriverFactory.getSeleniumWebDriver(browser);

            switch (mode)
            {
            case RECORD:
                doRecord(webDriver, actionFilePath, screenshotFolder, url, screenResolution);
                doReplay(webDriver, actionFilePath, screenshotFolder, url, screenResolution);
                break;
            case RECORD_ONLY:
                doRecord(webDriver, actionFilePath, screenshotFolder, url, screenResolution);
                break;
            case PLAYBACK_ONLY:
                doReplay(webDriver, actionFilePath, screenshotFolder, url, screenResolution);
                break;
            }

            System.out.println("Finished test [" + testConfig.getProperty(NAME) + "] in mode [" + mode + "]");
        } catch (Exception e)
        {
            if (testConfig != null && testConfig.getProperty(NAME) != null)
            {
                G2TVisualizationTestException g2tVisualizationTestException = new G2TVisualizationTestException(
                        "Can't finish test [" + testConfig.getProperty(NAME) + "]", e);
                g2tVisualizationTestException.printStackTrace();
            }
            else
            {
                e.printStackTrace();
            }
            testResult = -1;
        } finally
        {
            if (webDriver != null)
            {
                webDriver.quit();
            }
        }

        System.exit(testResult);
    }

    private static void doReplay(WebDriver webDriver, String actionFilePath, String screenshotFolder, String url,
            ScreenResolution screenResolution) throws IOException, JSONException, InterruptedException,
            G2TVisualizationTestException
    {
        ScreenshotTaker screenshotTaker;
        boolean isPlaybackMode;
        isPlaybackMode = true;
        screenshotTaker = new ScreenshotTaker(webDriver, screenshotFolder, isPlaybackMode);
        List<Event> eventSequence = EventSequenceReader.readEventSequenceFromFile(actionFilePath);
        ActionReplayer actionReplayer = new ActionReplayer(eventSequence, screenshotTaker);

        setWebDriverScreenResolution(webDriver, screenResolution);
        System.out.println("Selenium webdriver starting...");
        startBrowserAndLoadPage(webDriver, url);
        System.out.println("Selenium webdriver started...");

        System.out.println("Start replaying...");
        actionReplayer.replay();
    }

    private static void doRecord(WebDriver webDriver, String actionFilePath, String screenshotFolder, String url,
            ScreenResolution screenResolution) throws InterruptedException, IOException, JSONException
    {
        ScreenshotTaker screenshotTaker;
        boolean isPlaybackMode;
        isPlaybackMode = false;
        screenshotTaker = new ScreenshotTaker(webDriver, screenshotFolder, isPlaybackMode);
        ActionRecorder actionRecorder = new ActionRecorder(screenshotTaker);

        setWebDriverScreenResolution(webDriver, screenResolution);
        System.out.println("Selenium webdriver starting...");
        startBrowserAndLoadPage(webDriver, url);
        System.out.println("Selenium webdriver started...");

        System.out.println("Start recording...");
        Thread recorderWorker = new Thread(actionRecorder);

        recorderWorker.setName(ACTION_RECORDER);
        recorderWorker.start();

        recorderWorker.join();

        System.out.println("Writing action file...");
        EventSequenceWriter.writeEventSequenceToFile(actionFilePath, actionRecorder.getEventSequence());
        System.out.println("Fnished writing action file...");
    }

    private static void setWebDriverScreenResolution(WebDriver webDriver, ScreenResolution screenResolution)
    {
        webDriver.manage().window().setSize(new Dimension(screenResolution.getW(), screenResolution.getH()));
    }

    private static ScreenResolution getScreenResolutionFromConfig(String configString)
            throws ScreenResolutionParseErrorException
    {
        ScreenResolution screenResolution = null;

        if (configString == null || configString.isEmpty())
        {
            screenResolution = new ScreenResolution(DEFAULT_W, DEFAULT_H);
        }
        else
        {
            screenResolution = ScreenResolution.parse(configString);
        }
        return screenResolution;
    }

    // Format of test config
    // name=test name
    // url=test url
    // screensize=W*H
    private static Properties getTestConfig(String testConfigFilePath) throws IOException
    {
        Properties prop = new Properties();
        InputStream input = null;

        try
        {
            input = new FileInputStream(testConfigFilePath);
            prop.load(input);
        } finally
        {
            if (input != null)
            {
                input.close();
            }
        }
        return prop;
    }

    @SuppressWarnings("static-access")
    private static Options createCommandLineOptions()
    {
        Options options = new Options();
        Option modeOption = OptionBuilder
                .withArgName(MODE)
                .withType(String.class)
                .hasArgs(1)
                .withDescription("Running mode. Valid values are: record, record_only, playback_only.")
                .create(MODE);
        modeOption.setRequired(true);
        options.addOption(modeOption);

        Option testFolderOption = OptionBuilder
                .withArgName(TEST_FOLDER).withType(String.class)
                .hasArgs(1)
                .withDescription("Specify the path of the test")
                .create(TEST_FOLDER);
        testFolderOption.setRequired(true);
        options.addOption(testFolderOption);

        Option browserOption = OptionBuilder
                .withArgName(BROWSER_TYPE).withType(String.class)
                .hasArgs(1)
                .withDescription("Browser to run the test: ie, firefox(default)")
                .create(BROWSER_TYPE);
        options.addOption(browserOption);
        return options;
    }

    private static void startBrowserAndLoadPage(WebDriver driver, String url)
    {
        driver.get(url);
    }

}
