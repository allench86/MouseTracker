package com.ibm.biginsights.g2t.mouse.record.impl;

import static org.fest.assertions.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ibm.biginsights.g2t.mouse.Click;
import com.ibm.biginsights.g2t.mouse.DoubleClick;
import com.ibm.biginsights.g2t.mouse.DragDrop;
import com.ibm.biginsights.g2t.mouse.MouseButton;
import com.ibm.biginsights.g2t.mouse.MouseEvent;
import com.ibm.biginsights.g2t.visualization.Event;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MouseRecorderImpl.class })
public class MouseRecorderImplTest
{
    private final static int LEFT_BUTTON = 1;
    private final static int RIGHT_BUTTON = 2;

    private final static int CLICK = 1;
    private final static int DOUBLE_CLICKS = 2;
    private final static int CLICK_MANY_TIMES = 5;

    private final static Point ANY_POINT = new Point(7, 15);

    private final static long START_TIME = 1000;
    private final static long ANY_TIME = 1715;
    private final static long OFFSET = ANY_TIME - START_TIME;

    private final static Point FROM_POINT = new Point(7, 15);
    private final static Point TO_POINT = new Point(4, 19);
    private final static Point PASSED_POINT = new Point(10, 21);

    private MouseRecorderImpl mouseRecorder;
    private List<Event> eventSequence;

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        eventSequence = Collections.synchronizedList(new ArrayList<Event>());
        mouseRecorder = new MouseRecorderImpl(eventSequence, START_TIME);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void when_left_button_double_clicked_it_should_recored_the_event()
    {
        mouseRecorder.mouseClicked(LEFT_BUTTON, DOUBLE_CLICKS, ANY_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).containsOnly(
                new DoubleClick(MouseButton.LEFT_BUTTON, ANY_POINT, OFFSET));
    }

    @Test
    public void when_right_button_double_clicked_it_should_recored_the_event()
    {
        mouseRecorder.mouseClicked(RIGHT_BUTTON, DOUBLE_CLICKS, ANY_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).containsOnly(
                new DoubleClick(MouseButton.RIGHT_BUTTON, ANY_POINT, OFFSET));
    }

    @Test
    public void when_left_button_clicked_it_should_recored_the_event()
    {
        mouseRecorder.mouseClicked(LEFT_BUTTON, CLICK, ANY_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).containsOnly(
                new Click(MouseButton.LEFT_BUTTON, ANY_POINT, OFFSET));
    }

    @Test
    public void when_right_button_clicked_it_should_recored_the_event()
    {
        mouseRecorder.mouseClicked(RIGHT_BUTTON, CLICK, ANY_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).containsOnly(
                new Click(MouseButton.RIGHT_BUTTON, ANY_POINT, OFFSET));
    }

    @Test
    public void when_left_button_clicked_many_times_it_should_recored_all_the_events()
    {
        ArrayList<MouseEvent> expectedEvents = new ArrayList<MouseEvent>();
        for (int i = 0; i < CLICK_MANY_TIMES; i++)
        {
            expectedEvents.add(new Click(MouseButton.LEFT_BUTTON, ANY_POINT, OFFSET));
        }

        mouseRecorder.mouseClicked(LEFT_BUTTON, CLICK_MANY_TIMES, ANY_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).isEqualTo(expectedEvents);
    }

    @Test
    public void when_right_button_clicked_many_times_it_should_recored_all_the_events()
    {
        ArrayList<MouseEvent> expectedEvents = new ArrayList<MouseEvent>();
        for (int i = 0; i < CLICK_MANY_TIMES; i++)
        {
            expectedEvents.add(new Click(MouseButton.RIGHT_BUTTON, ANY_POINT, OFFSET));
        }

        mouseRecorder.mouseClicked(RIGHT_BUTTON, CLICK_MANY_TIMES, ANY_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).isEqualTo(expectedEvents);
    }

    @Test
    public void when_left_button_drag_from_a_point_to_another_it_should_record_the_drag_and_drop_event()
    {
        mouseRecorder.mouseDragged(FROM_POINT, ANY_TIME);
        mouseRecorder.mouseDragged(TO_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).containsOnly(
                new DragDrop(MouseButton.LEFT_BUTTON, FROM_POINT, TO_POINT, OFFSET));
    }

    @Test
    public void when_left_button_drag_passed_a_point_it_should_record_the_initiate_point_and_final_piont_not_the_passed_point()
    {
        mouseRecorder.mouseDragged(FROM_POINT, ANY_TIME);
        mouseRecorder.mouseDragged(PASSED_POINT, ANY_TIME);
        mouseRecorder.mouseDragged(TO_POINT, ANY_TIME);

        assertThat(mouseRecorder.getEventSequence()).containsOnly(
                new DragDrop(MouseButton.LEFT_BUTTON, FROM_POINT, TO_POINT, OFFSET));
    }

    @Test
    public void when_button_release_it_should_call_draggingFinished() throws Exception
    {
        MouseRecorderImpl mockMouseRecorder = PowerMockito.spy(new MouseRecorderImpl(eventSequence, START_TIME));
        mockMouseRecorder.mouseReleased(LEFT_BUTTON, TO_POINT, ANY_TIME);

        verifyPrivate(mockMouseRecorder).invoke("draggingFinished");
    }
}
