package com.ibm.biginsights.g2t.visualization.exception;

public class G2TVisualizationTestException extends Exception
{

    private static final long serialVersionUID = 4486983640778351704L;

    public G2TVisualizationTestException(String message)
    {
        super(message);
    }

    public G2TVisualizationTestException(String string, Exception e)
    {
        super(string, e);
    }

}
