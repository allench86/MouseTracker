package com.ibm.biginsights.g2t.record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.ibm.biginsights.g2t.keyboard.KeyboardRecorder;
import com.ibm.biginsights.g2t.keyboard.impl.KeyboardInputListenerImpl;
import com.ibm.biginsights.g2t.keyboard.impl.KeyboardRecorderImpl;
import com.ibm.biginsights.g2t.mouse.record.MouseRecorder;
import com.ibm.biginsights.g2t.mouse.record.impl.MouseInputListenerImpl;
import com.ibm.biginsights.g2t.mouse.record.impl.MouseRecorderImpl;
import com.ibm.biginsights.g2t.screen.ScreenshotEventRecorder;
import com.ibm.biginsights.g2t.screen.ScreenshotTaker;
import com.ibm.biginsights.g2t.visualization.Event;

public class ActionRecorder implements Runnable
{
    private static final long startTime = System.currentTimeMillis();
    private final List<Event> eventSequence;
    private final ScreenshotEventRecorder screenshotEventRecorder;
    private final KeyboardRecorder keyboardRecorder;
    private final KeyboardInputListenerImpl keyboardInputListener;
    private final MouseRecorder mouseRecorder;
    private final MouseInputListenerImpl mouseInputListener;
    private ScreenshotTaker screenshotTaker;

    public ActionRecorder()
    {
        this(null);
    }

    public ActionRecorder(ScreenshotTaker screenshotTaker)
    {
        setScreenshotTaker(screenshotTaker);
        eventSequence = Collections.synchronizedList(new ArrayList<Event>());
        screenshotEventRecorder = new ScreenshotEventRecorder(eventSequence, startTime);
        keyboardRecorder = new KeyboardRecorderImpl(eventSequence, startTime);
        keyboardInputListener = new KeyboardInputListenerImpl(keyboardRecorder, screenshotEventRecorder,
                getScreenshotTaker());
        mouseRecorder = new MouseRecorderImpl(eventSequence, startTime);
        mouseInputListener = new MouseInputListenerImpl(mouseRecorder);
    }

    public void run()
    {
        try
        {
            startRecording();
        } catch (NativeHookException e)
        {
            throw new RuntimeException(e);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getEventSequence()
    {
        return eventSequence;
    }

    public ScreenshotTaker getScreenshotTaker()
    {
        return screenshotTaker;
    }

    public void setScreenshotTaker(ScreenshotTaker screenshotTaker)
    {
        this.screenshotTaker = screenshotTaker;
    }

    private void startRecording() throws NativeHookException, InterruptedException
    {
        GlobalScreen.registerNativeHook();

        GlobalScreen.getInstance().addNativeKeyListener(keyboardInputListener);
        GlobalScreen.getInstance().addNativeMouseListener(mouseInputListener);
        GlobalScreen.getInstance().addNativeMouseMotionListener(mouseInputListener);

        while (GlobalScreen.isNativeHookRegistered())
        {
            Thread.sleep(1000);
        }
    }

}
