package com.ibm.biginsights.g2t.mouse;

import com.ibm.biginsights.g2t.visualization.Event;

public abstract class MouseEvent extends Event
{
    protected MouseButton button;
    protected long offset;

    public MouseButton getButton()
    {
        return button;
    }

    public long getOffset()
    {
        return offset;
    }

    public void setButton(MouseButton button)
    {
        this.button = button;
    }

    public void setOffset(long offset)
    {
        this.offset = offset;
    }
}
