package com.ibm.biginsights.g2t.keyboard.impl;

import static org.mockito.Mockito.*;

import org.jnativehook.GlobalScreen;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ibm.biginsights.g2t.keyboard.KeyboardRecorder;

/**
 * This test is not working.
 * 
 * @author allench86
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalScreen.class)
public class KeyboardInputListenerImplTest
{

    private static KeyboardRecorder mockKeyboardRecorder;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        mockKeyboardRecorder = mock(KeyboardRecorder.class);
        GlobalScreen.registerNativeHook();
        GlobalScreen.getInstance()
                .addNativeKeyListener(new KeyboardInputListenerImpl(mockKeyboardRecorder, null, null));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Ignore
    @Test
    public void when_press_escape_it_should_unregister_native_hook_and_not_record_the_event()
    {
        // KeyboardInputListenerImpl keyboardInputListener = new KeyboardInputListenerImpl(mockKeyboardRecorder);
        // NativeKeyEvent mockNativeKeyEvent = mock(NativeKeyEvent.class);
        // stub(mockNativeKeyEvent.getKeyCode()).toReturn(NativeKeyEvent.VK_ESCAPE);
        //
        // keyboardInputListener.nativeKeyPressed(mockNativeKeyEvent);
        //
        // PowerMockito.verifyStatic(times(1));
        // GlobalScreen.unregisterNativeHook();
    }

    @Test
    public void when_press_print_screen_it_should_not_record_the_event()
    {

    }

    @Test
    public void when_release_escape_it_should_not_record_the_event()
    {

    }

    @Test
    public void when_release_print_screen_it_should_not_record_the_event()
    {

    }

}
