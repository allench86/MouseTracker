package com.ibm.biginsights.g2t.mouse;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.awt.Point;
import java.io.IOException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class DragDrop extends MouseEvent
{
    private Point fromPoint;
    private Point toPoint;

    public DragDrop(MouseButton button, Point fromPoint, Point toPoint, long offset)
    {
        this.button = button;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.offset = offset;
    }

    public Point getFromPoint()
    {
        return fromPoint;
    }

    public Point getToPoint()
    {
        return toPoint;
    }

    public void setFromPoint(Point fromPoint)
    {
        this.fromPoint = fromPoint;
    }

    public void setToPoint(Point point)
    {
        this.toPoint = point;
    }

    @Override
    public JSONObject toJsonObject() throws IOException, JSONException
    {
        JSONObject json = new JSONObject();
        json.put(ACTION, DragDrop.class.getName());
        json.put(BUTTON, button.toString());
        json.put(FROM_POINT, new JSONObject(String.format(POINT_FORMAT_STRING, fromPoint.x, fromPoint.y)));
        json.put(TO_POINT, new JSONObject(String.format(POINT_FORMAT_STRING, toPoint.x, toPoint.y)));
        json.put(OFFSET, Long.valueOf(offset));

        return json;
    }

    @Override
    public String toString()
    {
        String result = "{\"" + ACTION + "\":\"" + this.getClass().getName() + "\"," +
                "\"" + BUTTON + "\":\"" + button.toString() + "\"," +
                "\"" + FROM_POINT + "\":" + String.format(POINT_FORMAT_STRING, fromPoint.x, fromPoint.y) + "," +
                "\"" + TO_POINT + "\":" + String.format(POINT_FORMAT_STRING, toPoint.x, toPoint.y) + "," +
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
        if (!(obj instanceof DragDrop))
        {
            return false;
        }

        DragDrop rhs = (DragDrop) obj;

        return new EqualsBuilder().append(button, rhs.button)
                .append(fromPoint, rhs.fromPoint)
                .append(toPoint, rhs.toPoint)
                .append(offset, rhs.offset)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(button)
                .append(fromPoint)
                .append(toPoint)
                .append(offset)
                .toHashCode();
    }

}
