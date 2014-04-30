package com.ibm.biginsights.g2t.keyboard.impl;

import java.util.List;

import com.ibm.biginsights.g2t.keyboard.KeyPressed;
import com.ibm.biginsights.g2t.keyboard.KeyReleased;
import com.ibm.biginsights.g2t.keyboard.KeyboardRecorder;
import com.ibm.biginsights.g2t.visualization.Event;

public class KeyboardRecorderImpl implements KeyboardRecorder
{
    private List<Event> eventSequence;
    private long startTime;

    public KeyboardRecorderImpl(List<Event> eventSequence, long startTime)
    {
        this.eventSequence = eventSequence;
        this.startTime = startTime;
    }

    public void keyPressed(int keyCode, long when)
    {
        Event event = new KeyPressed(keyCode, when - startTime);
        eventSequence.add(new KeyPressed(keyCode, when - startTime));
        System.out.println(event);
    }

    public void keyReleased(int keyCode, long when)
    {
        Event event = new KeyReleased(keyCode, when - startTime);
        eventSequence.add(new KeyReleased(keyCode, when - startTime));
        System.out.println(event);
    }

    public void keyTyped(int keyCode, long when)
    {
        // Do nothing for now
    }

    public List<Event> getEventSequence()
    {
        return eventSequence;
    }

}
