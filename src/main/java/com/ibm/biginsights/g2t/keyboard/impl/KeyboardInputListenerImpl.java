package com.ibm.biginsights.g2t.keyboard.impl;

import java.io.IOException;
import java.util.HashMap;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeSystem;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.ibm.biginsights.g2t.keyboard.KeyboardRecorder;
import com.ibm.biginsights.g2t.screen.ScreenshotEventRecorder;
import com.ibm.biginsights.g2t.screen.ScreenshotTaker;

public class KeyboardInputListenerImpl implements NativeKeyListener
{
    private static final boolean IS_OSX = NativeSystem.getFamily() == NativeSystem.Family.OSX;
    private static final HashMap<Integer, Integer> unrecognizableOSXKeys = new HashMap<Integer, Integer>();
    private KeyboardRecorder keyboardRecorder;
    private ScreenshotTaker screenshotTaker;
    private ScreenshotEventRecorder screenshotEventRecorder;
    private NativeKeyEvent currentPressedKey;

    public KeyboardInputListenerImpl(KeyboardRecorder keyboardRecorder,
            ScreenshotEventRecorder screenshotEventRecorder, ScreenshotTaker screenshotTaker)
    {
        this.keyboardRecorder = keyboardRecorder;
        this.screenshotTaker = screenshotTaker;
        this.screenshotEventRecorder = screenshotEventRecorder;
        currentPressedKey = null;
        setupUnrecognizableOSXKeys();
    }

    public void nativeKeyPressed(NativeKeyEvent e)
    {
        if (currentPressedKey != null && currentPressedKey.equals(e))
        {
            return;
        }
        else
        {
            currentPressedKey = e;
        }

        if (isEscPressed(e))
        {
            stopRecordingKeyBoardAction();
        }
        else if (isScreenPrintPressed(e))
        {
            takeScreenshot(e);
        }
        else
        {
            if (isUnrecognizableOSXKeysPressedOrReleased(e))
            {
                keyboardRecorder.keyPressed(unrecognizableOSXKeys.get(e.getRawCode()), e.getWhen());
            }
            else
            {
                keyboardRecorder.keyPressed(e.getKeyCode(), e.getWhen());
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e)
    {
        currentPressedKey = null;
        if (isEscPressed(e))
        {
            // do nothing
        }
        else if (isScreenPrintPressed(e))
        {
            // do nothing
        }
        else
        {
            if (isUnrecognizableOSXKeysPressedOrReleased(e))
            {
                keyboardRecorder.keyReleased(unrecognizableOSXKeys.get(e.getRawCode()), e.getWhen());
            }
            else
            {
                keyboardRecorder.keyReleased(e.getKeyCode(), e.getWhen());
            }
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e)
    {
        keyboardRecorder.keyTyped(e.getKeyCode(), e.getWhen());
    }

    private void setupUnrecognizableOSXKeys()
    {
        // A cheat here. When control, shift, alt is pressed,
        // JNativeHook can't recognize the correct key by key code.
        // All of these keys are 16 for their key code.
        // The raw code of left control is 59, and its correct key
        // code should be 17. Cheat here to make sure left control
        // works.
        // -- Kai, 05/06/2014
        unrecognizableOSXKeys.put(55, NativeKeyEvent.VK_CONTROL); // left command
        unrecognizableOSXKeys.put(58, NativeKeyEvent.VK_ALT); // left alt/option
        unrecognizableOSXKeys.put(59, NativeKeyEvent.VK_CONTROL); // left control
        unrecognizableOSXKeys.put(76, NativeKeyEvent.VK_ENTER); // number pad enter
        unrecognizableOSXKeys.put(81, NativeKeyEvent.VK_EQUALS); // number pad "="
    }

    private boolean isUnrecognizableOSXKeysPressedOrReleased(NativeKeyEvent e)
    {
        return IS_OSX && unrecognizableOSXKeys.containsKey(e.getRawCode());
    }

    private boolean isScreenPrintPressed(NativeKeyEvent e)
    {
        return e.getKeyCode() == NativeKeyEvent.VK_PRINTSCREEN
                || isF13OnOSXPressed(e);
    }

    private boolean isF13OnOSXPressed(NativeKeyEvent e)
    {
        return IS_OSX && e.getKeyCode() == NativeKeyEvent.VK_F13;
    }

    private boolean isEscPressed(NativeKeyEvent e)
    {
        return e.getKeyCode() == NativeKeyEvent.VK_ESCAPE;
    }

    private void stopRecordingKeyBoardAction()
    {
        System.out.println("Stop recording...");
        if (GlobalScreen.isNativeHookRegistered())
        {
            GlobalScreen.unregisterNativeHook();
        }
    }

    private void takeScreenshot(NativeKeyEvent e)
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

}
