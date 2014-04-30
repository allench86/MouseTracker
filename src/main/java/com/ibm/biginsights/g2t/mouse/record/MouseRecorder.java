package com.ibm.biginsights.g2t.mouse.record;

import java.awt.Point;

public interface MouseRecorder
{
    public void mouseClicked(int button, int times, Point point, long when);

    public void mouseDragged(Point point, long when);

    public void mouseMoved(Point point, long when);

    public void mousePressed(int button, Point point, long when);

    public void mouseReleased(int button, Point point, long when);
}
