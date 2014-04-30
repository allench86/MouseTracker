package com.ibm.biginsights.g2t.replay;

import static org.fest.assertions.Assertions.*;

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
import com.ibm.biginsights.g2t.visualization.exception.UnknownEventException;

public class EventSequenceReaderTest
{
    private static final String testFilePath = "writeTest.json";
    private static List<Event> exceptedEventSequence;

    private final static Point ANY_POINT = new Point(7, 15);

    private final static long START_TIME = 1000;
    private final static long ANY_TIME = 1715;
    private final static long OFFSET = ANY_TIME - START_TIME;

    private final static Point FROM_POINT = new Point(7, 15);
    private final static Point TO_POINT = new Point(4, 19);

    private final static int ANY_KEY = 15;

    private final static Click click = new Click(MouseButton.LEFT_BUTTON, ANY_POINT, OFFSET);
    private final static DoubleClick doubleClick = new DoubleClick(MouseButton.LEFT_BUTTON, ANY_POINT, OFFSET);
    private final static DragDrop dragDrop = new DragDrop(MouseButton.LEFT_BUTTON, FROM_POINT, TO_POINT, OFFSET);

    private final static KeyPressed keyPressed = new KeyPressed(ANY_KEY, OFFSET);
    private final static KeyReleased keyReleased = new KeyReleased(ANY_KEY, OFFSET);

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {

    }

    @Before
    public void setUp() throws Exception
    {
        exceptedEventSequence = Collections.synchronizedList(new ArrayList<Event>());

        exceptedEventSequence.add(click);
        exceptedEventSequence.add(doubleClick);
        exceptedEventSequence.add(dragDrop);
        exceptedEventSequence.add(keyPressed);
        exceptedEventSequence.add(keyReleased);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void test() throws IOException, JSONException, UnknownEventException
    {
        List<Event> eventSequence = EventSequenceReader.readEventSequenceFromFile(testFilePath);

        // assertThat(eventSequence).containsSequence(click, doubleClick, dragDrop, keyPressed, keyReleased);
        assertThat(eventSequence).containsExactly(click, doubleClick, dragDrop, keyPressed, keyReleased);
    }

}
