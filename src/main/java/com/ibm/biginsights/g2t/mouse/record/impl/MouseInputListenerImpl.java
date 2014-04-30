package com.ibm.biginsights.g2t.mouse.record.impl;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.ibm.biginsights.g2t.mouse.record.MouseRecorder;

public class MouseInputListenerImpl implements NativeMouseInputListener
{
    private MouseRecorder mouseRecorder;

    public MouseInputListenerImpl(MouseRecorder mouseRecorder)
    {
        this.mouseRecorder = mouseRecorder;
    }

    public void nativeMouseClicked(NativeMouseEvent e)
    {
        mouseRecorder.mouseClicked(e.getButton(), e.getClickCount(), e.getPoint(), e.getWhen());
    }

    public void nativeMousePressed(NativeMouseEvent e)
    {
        mouseRecorder.mousePressed(e.getButton(), e.getPoint(), e.getWhen());
    }

    public void nativeMouseReleased(NativeMouseEvent e)
    {
        mouseRecorder.mouseReleased(e.getButton(), e.getPoint(), e.getWhen());
    }

    public void nativeMouseDragged(NativeMouseEvent e)
    {
        mouseRecorder.mouseDragged(e.getPoint(), e.getWhen());
    }

    public void nativeMouseMoved(NativeMouseEvent e)
    {
        mouseRecorder.mouseMoved(e.getPoint(), e.getWhen());
    }

}
