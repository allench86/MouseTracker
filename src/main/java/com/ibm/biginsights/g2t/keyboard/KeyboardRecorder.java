package com.ibm.biginsights.g2t.keyboard;

public interface KeyboardRecorder
{
    public void keyPressed(int keyCode, long when);

    public void keyReleased(int keyCode, long when);

    public void keyTyped(int keyCode, long when);

}
