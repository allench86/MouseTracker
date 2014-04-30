package com.ibm.biginsights.g2t.mouse;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;
import static org.skyscreamer.jsonassert.JSONAssert.*;

import java.awt.Point;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ClickTest
{
    private final static String CLICK_CLASS_NAME = Click.class.getName();

    private final static String LEFT_BUTTON_STRING = "LEFT_BUTTON";
    private final static Point ANY_POINT = new Point(7, 15);
    private final static String ANY_POINT_STRING = "{ \"x\": 7, \"y\": 15}";
    private final static long ANY_TIME = 1715;

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
    public void test_toJSONObject_method() throws JSONException, IOException
    {
        JSONObject expectedJSON = new JSONObject();
        expectedJSON.put(ACTION, CLICK_CLASS_NAME);
        expectedJSON.put(BUTTON, LEFT_BUTTON_STRING);
        expectedJSON.put(POINT, new JSONObject(ANY_POINT_STRING));
        expectedJSON.put(OFFSET, ANY_TIME);

        Click click = new Click(MouseButton.LEFT_BUTTON, ANY_POINT, ANY_TIME);
        assertEquals(expectedJSON, click.toJsonObject(), false);
    }

}
