package com.ibm.biginsights.g2t.screen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.biginsights.g2t.visualization.exception.ScreenResolutionParseErrorException;

public class ScreenResolution
{
    private final int w;
    private final int h;

    public ScreenResolution(int w, int h)
    {
        this.w = w;
        this.h = h;
    }

    public static ScreenResolution parse(String configString) throws ScreenResolutionParseErrorException
    {
        ScreenResolution screenResolution = null;
        Pattern pattern = Pattern.compile("([\\d]+)\\*([\\d]+)");
        Matcher matcher = pattern.matcher(configString);
        boolean matched = matcher.matches();

        if (matched)
        {
            String w = matcher.group(1);
            String h = matcher.group(2);

            screenResolution = new ScreenResolution(Integer.valueOf(w), Integer.valueOf(h));
        }
        else
        {
            throw new ScreenResolutionParseErrorException("Can't parse resolution setting: " + configString);
        }
        return screenResolution;
    }

    public int getW()
    {
        return w;
    }

    public int getH()
    {
        return h;
    }

}
