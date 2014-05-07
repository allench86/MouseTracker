package com.ibm.biginsights.g2t.mouse.record.impl;

import java.awt.Point;
import java.util.List;

import com.ibm.biginsights.g2t.mouse.Click;
import com.ibm.biginsights.g2t.mouse.DoubleClick;
import com.ibm.biginsights.g2t.mouse.DragDrop;
import com.ibm.biginsights.g2t.mouse.MouseButton;
import com.ibm.biginsights.g2t.mouse.record.MouseRecorder;
import com.ibm.biginsights.g2t.visualization.Event;

public class MouseRecorderImpl implements MouseRecorder
{
    private List<Event> eventSequence;
    private DragDrop tmpDragDrop;
    private long startTime;

    public MouseRecorderImpl(List<Event> eventSequence, long startTime)
    {
        this.tmpDragDrop = null;
        this.eventSequence = eventSequence;
        this.startTime = startTime;
    }

    public void mouseClicked(int button, int times, Point point, long when)
    {
        if (times == 2)
        {
            Event event = new DoubleClick(MouseButton.fromInteger(button), point, when - startTime);
            eventSequence.add(event);
            System.out.println("MouseEventDoubleClick:" + event);
        }
        else
        {
            while (times > 0)
            {
                Event event = new Click(MouseButton.fromInteger(button), point, when - startTime);
                eventSequence.add(event);
                System.out.println("MouseEventClick:" + event);
                times--;
            }
        }
    }

    public void mouseDragged(Point point, long when)
    {
        if (tmpDragDrop == null)
        {
            tmpDragDrop = new DragDrop(MouseButton.LEFT_BUTTON, point, point, when - startTime);
            eventSequence.add(tmpDragDrop);
        }
        else
        {
            tmpDragDrop.setToPoint(point);
        }
    }

    public void mouseMoved(Point point, long when)
    {
        // Do nothing for now
    }

    public void mousePressed(int button, Point point, long when)
    {
        // Do nothing for now
    }

    public void mouseReleased(int button, Point point, long when)
    {
        draggingFinished();
    }

    public List<Event> getEventSequence()
    {
        return eventSequence;
    }

    private void draggingFinished()
    {
        if (tmpDragDrop != null)
        {
            System.out.println("MouseEventDragDrop:" + tmpDragDrop);
        }
        tmpDragDrop = null;
    }

}
