package com.ibm.biginsights.g2t.replay;

import java.io.IOException;
import java.util.List;

import com.ibm.biginsights.g2t.keyboard.KeyboardEvent;
import com.ibm.biginsights.g2t.mouse.MouseEvent;
import com.ibm.biginsights.g2t.screen.ScreenshotComparator;
import com.ibm.biginsights.g2t.screen.ScreenshotEvent;
import com.ibm.biginsights.g2t.screen.ScreenshotTaker;
import com.ibm.biginsights.g2t.visualization.Event;
import com.ibm.biginsights.g2t.visualization.exception.G2TVisualizationTestException;
import com.ibm.biginsights.g2t.visualization.exception.ScreenshotDifferentException;
import com.ibm.biginsights.g2t.visualization.exception.UnknownEventException;

public class ActionReplayer
{
    private List<Event> eventSequence;
    private final KeyboardEventPlayer keyboardEventPlayer;
    private final MouseEventPlayer mouseEventPlayer;
    private ScreenshotTaker screenshotTaker;
    private ScreenshotComparator screenshotComparator;

    public ActionReplayer(List<Event> eventSequence)
    {
        this(eventSequence, null);
    }

    public ActionReplayer(List<Event> eventSequence, ScreenshotTaker screenshotTaker)
    {
        this.setEventSequence(eventSequence);
        this.keyboardEventPlayer = new KeyboardEventPlayer();
        this.mouseEventPlayer = new MouseEventPlayer();
        this.screenshotComparator = ScreenshotComparator.getInstance();
        setScreenshotTaker(screenshotTaker);
    }

    public void replay() throws InterruptedException, IOException, G2TVisualizationTestException
    {
        long preOffset = 0;
        if (eventSequence != null)
        {
            for (Event event : eventSequence)
            {
                if (event instanceof MouseEvent)
                {
                    replayMouseEvent(event, preOffset);
                    preOffset = ((MouseEvent) event).getOffset();
                }
                else if (event instanceof KeyboardEvent)
                {
                    replayKeyboardEvent(event, preOffset);
                    preOffset = ((KeyboardEvent) event).getOffset();
                }
                else if (event instanceof ScreenshotEvent)
                {
                    Thread.sleep(((ScreenshotEvent) event).getOffset() - preOffset);
                    screenshotTaker.takeScreenshot();
                    ScreenshotEvent screenshotEvent = (ScreenshotEvent) event;
                    preOffset = screenshotEvent.getOffset();

                    if (!screenshotComparator.areScreenshotsIdentical(screenshotEvent,
                            screenshotTaker.getScreenshotFoler()))
                    {
                        throw new ScreenshotDifferentException("Screenshot_" + screenshotEvent.getIndex()
                                + " is not identical.");
                    }
                }
                else
                {
                    throw new UnknownEventException("Unknown event: " + event.toString());
                }
            }
        }
    }

    public List<Event> getEventSequence()
    {
        return eventSequence;
    }

    public void setEventSequence(List<Event> eventSequence)
    {
        this.eventSequence = eventSequence;
    }

    private void replayKeyboardEvent(Event event, long preOffset) throws InterruptedException,
            G2TVisualizationTestException
    {
        keyboardEventPlayer.playEvent(event, preOffset);
    }

    private void replayMouseEvent(Event event, long preOffset) throws InterruptedException,
            G2TVisualizationTestException
    {
        mouseEventPlayer.playEvent(event, preOffset);
    }

    public ScreenshotTaker getScreenshotTaker()
    {
        return screenshotTaker;
    }

    public void setScreenshotTaker(ScreenshotTaker screenshotTaker)
    {
        this.screenshotTaker = screenshotTaker;
    }
}
