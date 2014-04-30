package com.ibm.biginsights.g2t.screen;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.ibm.biginsights.g2t.visualization.exception.ScreenResolutionParseErrorException;

public class ScreenResolutionTest
{

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void test_parse_method()
    {
        String configString = "1920*1080";
        try
        {
            ScreenResolution.parse(configString);
        } catch (ScreenResolutionParseErrorException e)
        {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}
