package com.ibm.biginsights.g2t.visualization.exception;

import com.ibm.biginsights.g2t.visualization.Event;

public class UnknownMouseButtonException extends G2TVisualizationTestException
{

    private static final long serialVersionUID = -2107599359839300511L;

    public UnknownMouseButtonException(String message)
    {
        super(message);
    }

    public UnknownMouseButtonException(Event event, String mouseButton)
    {
        this("Can't replay mouse event [" + event.toString()
                + "] due to unknown mouse button: " + mouseButton);
    }
}
