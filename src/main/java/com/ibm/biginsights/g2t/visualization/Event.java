package com.ibm.biginsights.g2t.visualization;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Event
{
    public abstract JSONObject toJsonObject() throws IOException, JSONException;
}
