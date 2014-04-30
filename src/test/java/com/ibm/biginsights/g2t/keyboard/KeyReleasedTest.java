package com.ibm.biginsights.g2t.keyboard;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;
import static org.skyscreamer.jsonassert.JSONAssert.*;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class KeyReleasedTest
{

    private final static String KEY_RELEASED_CLASS_NAME = KeyReleased.class.getName();
    private final static int ANY_KEY = 15;
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
        expectedJSON.put(ACTION, KEY_RELEASED_CLASS_NAME);
        expectedJSON.put(KEY_CODE, ANY_KEY);
        expectedJSON.put(OFFSET, ANY_TIME);

        KeyPressed pressed = new KeyPressed(ANY_KEY, ANY_TIME);
        assertEquals(expectedJSON, pressed.toJsonObject(), false);
    }

}
