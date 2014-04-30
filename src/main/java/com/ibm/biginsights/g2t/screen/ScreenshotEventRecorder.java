package com.ibm.biginsights.g2t.screen;

import java.util.List;

import com.ibm.biginsights.g2t.visualization.Event;

public class ScreenshotEventRecorder
{
    private List<Event> eventSequence;
    private long startTime;

    public ScreenshotEventRecorder(List<Event> eventSequence, long startTime)
    {
        this.eventSequence = eventSequence;
        this.startTime = startTime;
    }

    public void addScreenshotEvent(int screenshotIndex, long when)
    {
        eventSequence.add(new ScreenshotEvent(screenshotIndex, when - startTime));
    }
}
