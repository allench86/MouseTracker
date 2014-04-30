package com.ibm.biginsights.g2t.record;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.ibm.biginsights.g2t.keyboard.KeyPressed;
import com.ibm.biginsights.g2t.keyboard.KeyReleased;
import com.ibm.biginsights.g2t.mouse.Click;
import com.ibm.biginsights.g2t.mouse.DoubleClick;
import com.ibm.biginsights.g2t.mouse.DragDrop;
import com.ibm.biginsights.g2t.mouse.MouseButton;
import com.ibm.biginsights.g2t.visualization.Event;

public class EventSequenceWriterTest
{
    private static final String testFilePath = "writeTest.json";
    private static List<Event> eventSequence;

    private final static Point ANY_POINT = new Point(7, 15);

    private final static long START_TIME = 1000;
    private final static long ANY_TIME = 1715;
    private final static long OFFSET = ANY_TIME - START_TIME;

    private final static Point FROM_POINT = new Point(7, 15);
    private final static Point TO_POINT = new Point(4, 19);

    private final static int ANY_KEY = 15;

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {

    }

    @Before
    public void setUp() throws Exception
    {
        eventSequence = Collections.synchronizedList(new ArrayList<Event>());

        Click click = new Click(MouseButton.LEFT_BUTTON, ANY_POINT, OFFSET);
        DoubleClick doubleClick = new DoubleClick(MouseButton.LEFT_BUTTON, ANY_POINT, OFFSET);
        DragDrop dragDrop = new DragDrop(MouseButton.LEFT_BUTTON, FROM_POINT, TO_POINT, OFFSET);

        KeyPressed keyPressed = new KeyPressed(ANY_KEY, OFFSET);
        KeyReleased keyReleased = new KeyReleased(ANY_KEY, OFFSET);

        eventSequence.add(click);
        eventSequence.add(doubleClick);
        eventSequence.add(dragDrop);
        eventSequence.add(keyPressed);
        eventSequence.add(keyReleased);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void test()
    {
        try
        {
            EventSequenceWriter.writeEventSequenceToFile(testFilePath, eventSequence);
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
