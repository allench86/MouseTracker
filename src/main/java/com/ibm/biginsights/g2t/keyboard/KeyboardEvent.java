package com.ibm.biginsights.g2t.keyboard;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.biginsights.g2t.visualization.Event;

public abstract class KeyboardEvent extends Event
{
    protected int keyCode;
    protected long offset;

    public KeyboardEvent(int keyCode, long offset)
    {
        this.keyCode = keyCode;
        this.offset = offset;
    }

    public void setKeyCode(int keyCode)
    {
        this.keyCode = keyCode;
    }

    public void setOffset(long offset)
    {
        this.offset = offset;
    }

    public int getKeyCode()
    {
        return keyCode;
    }

    public long getOffset()
    {
        return offset;
    }

    @Override
    public JSONObject toJsonObject() throws IOException, JSONException
    {
        JSONObject json = new JSONObject();
        json.put(ACTION, this.getClass().getName());
        json.put(KEY_CODE, keyCode);
        json.put(OFFSET, Long.valueOf(offset));

        return json;
    }

    @Override
    public String toString()
    {
        String result = "{\"" + ACTION + "\":\"" + this.getClass().getName() + "\"," +
                "\"" + KEY_CODE + "\":\"" + keyCode + "\"," +
                "\"" + OFFSET + "\":\"" + offset + "\"}";
        return result;
    }
}
