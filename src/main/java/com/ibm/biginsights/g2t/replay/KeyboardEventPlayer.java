package com.ibm.biginsights.g2t.replay;

import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.desktop.DesktopKeyboard;

import com.ibm.biginsights.g2t.keyboard.KeyPressed;
import com.ibm.biginsights.g2t.keyboard.KeyReleased;
import com.ibm.biginsights.g2t.visualization.Event;
import com.ibm.biginsights.g2t.visualization.exception.UnknownEventException;

public class KeyboardEventPlayer
{
    private final Keyboard keyboard;

    public KeyboardEventPlayer()
    {
        this.keyboard = new DesktopKeyboard();
    }

    public void playEvent(Event event, long preOffset) throws InterruptedException, UnknownEventException
    {
        if (event instanceof KeyPressed)
        {
            System.out.println(event.toString());
            Thread.sleep(((KeyPressed) event).getOffset() - preOffset);
            keyboard.keyDown(((KeyPressed) event).getKeyCode());
        }
        else if (event instanceof KeyReleased)
        {
            System.out.println(event.toString());
            Thread.sleep(((KeyReleased) event).getOffset() - preOffset);
            keyboard.keyUp(((KeyReleased) event).getKeyCode());
        }
        else
        {
            throw new UnknownEventException("Event " + event.toString() + "is not a keyborad evnet.");
        }
    }
}
