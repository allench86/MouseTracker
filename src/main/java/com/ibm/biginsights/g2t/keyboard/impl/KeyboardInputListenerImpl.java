package com.ibm.biginsights.g2t.keyboard.impl;

import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.ibm.biginsights.g2t.keyboard.KeyboardRecorder;
import com.ibm.biginsights.g2t.screen.ScreenshotEventRecorder;
import com.ibm.biginsights.g2t.screen.ScreenshotTaker;

public class KeyboardInputListenerImpl implements NativeKeyListener
{
    private KeyboardRecorder keyboardRecorder;
    private ScreenshotTaker screenshotTaker;
    private ScreenshotEventRecorder screenshotEventRecorder;

    public KeyboardInputListenerImpl(KeyboardRecorder keyboardRecorder,
            ScreenshotEventRecorder screenshotEventRecorder, ScreenshotTaker screenshotTaker)
    {
        this.keyboardRecorder = keyboardRecorder;
        this.screenshotTaker = screenshotTaker;
        this.screenshotEventRecorder = screenshotEventRecorder;
    }

    public void nativeKeyPressed(NativeKeyEvent e)
    {
        if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE)
        {
            System.out.println("Stop recording...");
            if (GlobalScreen.isNativeHookRegistered())
            {
                GlobalScreen.unregisterNativeHook();
            }
        }
        else if (e.getKeyCode() == NativeKeyEvent.VK_PRINTSCREEN || e.getKeyCode() == 61440 || e.getKeyCode() == 16)
        {
            System.out.println("Take screenshot...");
            screenshotEventRecorder.addScreenshotEvent(screenshotTaker.getScreenshotIndex(), e.getWhen());
            try
            {
                screenshotTaker.takeScreenshot();
            } catch (IOException e1)
            {
                throw new RuntimeException(e1);
            }
        }
        else
        {
            keyboardRecorder.keyPressed(e.getKeyCode(), e.getWhen());
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e)
    {
        if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE)
        {
            // do nothing
        }
        else if (e.getKeyCode() == NativeKeyEvent.VK_PRINTSCREEN || e.getKeyCode() == 61440)
        {
            // do nothing
        }
        else
        {
            keyboardRecorder.keyReleased(e.getKeyCode(), e.getWhen());
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e)
    {
        keyboardRecorder.keyTyped(e.getKeyCode(), e.getWhen());
    }

}
