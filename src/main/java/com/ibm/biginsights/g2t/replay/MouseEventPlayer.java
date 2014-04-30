package com.ibm.biginsights.g2t.replay;

import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;

import com.ibm.biginsights.g2t.mouse.Click;
import com.ibm.biginsights.g2t.mouse.DoubleClick;
import com.ibm.biginsights.g2t.mouse.DragDrop;
import com.ibm.biginsights.g2t.visualization.Event;
import com.ibm.biginsights.g2t.visualization.exception.UnknownEventException;

public class MouseEventPlayer
{
    private final Mouse mouse;

    public MouseEventPlayer()
    {
        this.mouse = new DesktopMouse();
    }

    public void playEvent(Event event, long preOffset) throws InterruptedException, UnknownEventException
    {
        if (event instanceof Click)
        {
            DesktopScreen firstScreen = new DesktopScreen(0);
            ScreenLocation point = new DefaultScreenLocation(firstScreen, ((Click) event).getPoint().x,
                    ((Click) event).getPoint().y);

            Thread.sleep(((Click) event).getOffset() - preOffset);
            System.out.println(event.toString());
            mouse.click(point);
        }
        else if (event instanceof DoubleClick)
        {
            DesktopScreen firstScreen = new DesktopScreen(0);
            ScreenLocation point = new DefaultScreenLocation(firstScreen, ((DoubleClick) event).getPoint().x,
                    ((DoubleClick) event).getPoint().y);

            Thread.sleep(((DoubleClick) event).getOffset() - preOffset);
            System.out.println(event.toString());
            mouse.doubleClick(point);
        }
        else if (event instanceof DragDrop)
        {
            DesktopScreen firstScreen = new DesktopScreen(0);
            ScreenLocation fromPoint = new DefaultScreenLocation(firstScreen, ((DragDrop) event).getFromPoint().x,
                    ((DragDrop) event).getFromPoint().y);
            ScreenLocation toPoint = new DefaultScreenLocation(firstScreen, ((DragDrop) event).getToPoint().x,
                    ((DragDrop) event).getToPoint().y);
            Thread.sleep(((DragDrop) event).getOffset() - preOffset);
            System.out.println(event.toString());
            mouse.click(fromPoint);
            mouse.drag(fromPoint);
            mouse.drag(fromPoint);
            mouse.drop(toPoint);
        }
        else
        {
            throw new UnknownEventException("Event " + event.toString() + "is not a mouse evnet.");
        }
    }
}
