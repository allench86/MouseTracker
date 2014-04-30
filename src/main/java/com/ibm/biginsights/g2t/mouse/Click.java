package com.ibm.biginsights.g2t.mouse;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.awt.Point;
import java.io.IOException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class Click extends MouseEvent
{
    private Point point;

    public Click(MouseButton button, Point point, long offset)
    {
        this.button = button;
        this.point = point;
        this.offset = offset;
    }

    public Point getPoint()
    {
        return point;
    }

    public void setPoint(Point point)
    {
        this.point = point;
    }

    @Override
    public JSONObject toJsonObject() throws IOException, JSONException
    {
        JSONObject json = new JSONObject();
        json.put(ACTION, Click.class.getName());
        json.put(BUTTON, button.toString());
        json.put(POINT, new JSONObject(String.format(POINT_FORMAT_STRING, point.x, point.y)));
        json.put(OFFSET, Long.valueOf(offset));

        return json;
    }

    @Override
    public String toString()
    {
        String result = "{\"" + ACTION + "\":\"" + this.getClass().getName() + "\"," +
                "\"" + BUTTON + "\":\"" + button.toString() + "\"," +
                "\"" + POINT + "\":" + String.format(POINT_FORMAT_STRING, point.x, point.y) + "," +
                "\"" + OFFSET + "\":\"" + offset + "\"}";
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (!(obj instanceof Click))
        {
            return false;
        }

        Click rhs = (Click) obj;

        return new EqualsBuilder().append(button, rhs.button)
                .append(point, rhs.point)
                .append(offset, rhs.offset)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(button)
                .append(point)
                .append(offset)
                .toHashCode();
    }
}
