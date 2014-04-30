package com.ibm.biginsights.g2t.mouse;

public enum MouseButton
{
    LEFT_BUTTON(1), RIGHT_BUTTON(2);
    private int value;

    private MouseButton(int value)
    {
        this.value = value;
    }

    public static MouseButton fromInteger(int x)
    {
        switch (x)
        {
        case 1:
            return LEFT_BUTTON;
        case 2:
            return RIGHT_BUTTON;
        }
        return null;
    }

}
