package com.ibm.biginsights.g2t.replay;

import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;

import com.ibm.biginsights.g2t.mouse.Click;
import com.ibm.biginsights.g2t.mouse.DoubleClick;
import com.ibm.biginsights.g2t.mouse.DragDrop;
import com.ibm.biginsights.g2t.mouse.MouseButton;
import com.ibm.biginsights.g2t.visualization.Event;
import com.ibm.biginsights.g2t.visualization.exception.UnknownEventException;
import com.ibm.biginsights.g2t.visualization.exception.UnknownMouseButtonException;

public class MouseEventPlayer
{
    private final Mouse mouse;

    public MouseEventPlayer()
    {
        this.mouse = new DesktopMouse();
    }

    public void playEvent(Event event, long preOffset) throws InterruptedException, UnknownEventException,
            UnknownMouseButtonException
    {
        if (event instanceof Click)
        {
            Click clickEvent = (Click) event;
            DesktopScreen firstScreen = new DesktopScreen(1);
            ScreenLocation point = new DefaultScreenLocation(firstScreen, clickEvent.getPoint().x,
                    clickEvent.getPoint().y);

            Thread.sleep(clickEvent.getOffset() - preOffset);
            System.out.println(clickEvent.toString());
            if (clickEvent.getButton() == MouseButton.LEFT_BUTTON)
            {
                mouse.click(point);
            }
            else if (clickEvent.getButton() == MouseButton.RIGHT_BUTTON)
            {
                mouse.rightClick(point);
            }
            else
            {
                throw new UnknownMouseButtonException(clickEvent, clickEvent.getButton().name());
            }
        }
        else if (event instanceof DoubleClick)
        {
            DoubleClick doubleClickEvent = (DoubleClick) event;
            DesktopScreen firstScreen = new DesktopScreen(1);
            ScreenLocation point = new DefaultScreenLocation(firstScreen, doubleClickEvent.getPoint().x,
                    doubleClickEvent.getPoint().y);

            Thread.sleep(doubleClickEvent.getOffset() - preOffset);
            System.out.println(doubleClickEvent.toString());
            if (doubleClickEvent.getButton() == MouseButton.LEFT_BUTTON)
            {
                mouse.doubleClick(point);
            }
            else
            {
                throw new UnknownMouseButtonException(doubleClickEvent, doubleClickEvent.getButton().name());
            }
        }
        else if (event instanceof DragDrop)
        {
            DragDrop dragDropEvent = (DragDrop) event;
            DesktopScreen firstScreen = new DesktopScreen(1);
            ScreenLocation fromPoint = new DefaultScreenLocation(firstScreen, dragDropEvent.getFromPoint().x,
                    dragDropEvent.getFromPoint().y);
            ScreenLocation toPoint = new DefaultScreenLocation(firstScreen, dragDropEvent.getToPoint().x,
                    dragDropEvent.getToPoint().y);
            Thread.sleep(dragDropEvent.getOffset() - preOffset);
            System.out.println(dragDropEvent.toString());
            if (dragDropEvent.getButton() == MouseButton.LEFT_BUTTON)
            {
                mouse.click(fromPoint);
                mouse.drag(fromPoint);
                mouse.drag(fromPoint);
                mouse.drop(toPoint);
            }
            else
            {
                throw new UnknownMouseButtonException(dragDropEvent, dragDropEvent.getButton().name());
            }
        }
        else
        {
            throw new UnknownEventException("Event " + event.toString() + "is not a mouse evnet.");
        }
    }
}
