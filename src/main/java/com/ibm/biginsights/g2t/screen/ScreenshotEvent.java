package com.ibm.biginsights.g2t.screen;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.biginsights.g2t.visualization.Event;

public class ScreenshotEvent extends Event
{
    private int index;
    private long offset;

    public ScreenshotEvent(int index, long offset)
    {
        this.index = index;
        this.offset = offset;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public long getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    @Override
    public JSONObject toJsonObject() throws IOException, JSONException
    {
        JSONObject json = new JSONObject();
        json.put(ACTION, this.getClass().getName());
        json.put(INDEX, index);
        json.put(OFFSET, Long.valueOf(offset));

        return json;
    }

    @Override
    public String toString()
    {
        String result = "{\"" + ACTION + "\":\"" + this.getClass().getName() + "\"," +
                "\"" + INDEX + "\":\"" + index + "\"," +
                "\"" + OFFSET + "\":\"" + offset + "\"}";
        return result;
    }

}
