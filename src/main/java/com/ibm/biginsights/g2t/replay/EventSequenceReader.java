package com.ibm.biginsights.g2t.replay;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.biginsights.g2t.keyboard.KeyPressed;
import com.ibm.biginsights.g2t.keyboard.KeyReleased;
import com.ibm.biginsights.g2t.mouse.Click;
import com.ibm.biginsights.g2t.mouse.DoubleClick;
import com.ibm.biginsights.g2t.mouse.DragDrop;
import com.ibm.biginsights.g2t.mouse.MouseButton;
import com.ibm.biginsights.g2t.screen.ScreenshotEvent;
import com.ibm.biginsights.g2t.visualization.Event;
import com.ibm.biginsights.g2t.visualization.exception.UnknownEventException;

public class EventSequenceReader
{
    public static List<Event> readEventSequenceFromFile(final String filePath) throws IOException, JSONException,
            UnknownEventException
    {
        List<Event> eventSequence = null;
        JSONObject json = getJsonObjectFromFile(filePath);

        if (json.has(ACTIONS))
        {
            JSONArray events = json.getJSONArray(ACTIONS);
            eventSequence = createEventSequenceFromJSON(events);
        }

        return eventSequence;
    }

    private static List<Event> createEventSequenceFromJSON(JSONArray json) throws JSONException, UnknownEventException
    {
        List<Event> eventSequence = Collections.synchronizedList(new ArrayList<Event>());
        for (int i = 0; i < json.length(); i++)
        {
            JSONObject eventJson = json.getJSONObject(i);
            String action = (String) eventJson.get(ACTION);
            if (Click.class.getName().equals(action))
            {
                String button = (String) eventJson.get(BUTTON);
                Point point = new Point(
                        (Integer) ((JSONObject) eventJson.get(POINT)).get("x"),
                        (Integer) ((JSONObject) eventJson.get(POINT)).get("y"));
                long offset = Long.valueOf((String) eventJson.get(OFFSET));
                eventSequence.add(new Click(MouseButton.valueOf(button), point, offset));
            }
            else if (DoubleClick.class.getName().equals(action))
            {
                String button = (String) eventJson.get(BUTTON);
                Point point = new Point(
                        (Integer) ((JSONObject) eventJson.get(POINT)).get("x"),
                        (Integer) ((JSONObject) eventJson.get(POINT)).get("y"));
                long offset = Long.valueOf((String) eventJson.get(OFFSET));
                eventSequence.add(new DoubleClick(MouseButton.valueOf(button), point, offset));
            }
            else if (DragDrop.class.getName().equals(action))
            {
                String button = (String) eventJson.get(BUTTON);
                Point fromPoint = new Point(
                        (Integer) ((JSONObject) eventJson.get(FROM_POINT)).get("x"),
                        (Integer) ((JSONObject) eventJson.get(FROM_POINT)).get("y"));
                Point toPoint = new Point(
                        (Integer) ((JSONObject) eventJson.get(TO_POINT)).get("x"),
                        (Integer) ((JSONObject) eventJson.get(TO_POINT)).get("y"));
                long offset = Long.valueOf((String) eventJson.get(OFFSET));
                eventSequence.add(new DragDrop(MouseButton.valueOf(button), fromPoint, toPoint, offset));
            }
            else if (KeyPressed.class.getName().equals(action))
            {
                int keyCode = Integer.valueOf((String) eventJson.get(KEY_CODE));
                long offset = Long.valueOf((String) eventJson.get(OFFSET));
                eventSequence.add(new KeyPressed(keyCode, offset));
            }
            else if (KeyReleased.class.getName().equals(action))
            {
                int keyCode = Integer.valueOf((String) eventJson.get(KEY_CODE));
                long offset = Long.valueOf((String) eventJson.get(OFFSET));
                eventSequence.add(new KeyReleased(keyCode, offset));
            }
            else if (ScreenshotEvent.class.getName().equals(action))
            {
                int index = Integer.valueOf((String) eventJson.get(INDEX));
                long offset = Long.valueOf((String) eventJson.get(OFFSET));
                eventSequence.add(new ScreenshotEvent(index, offset));
            }
            else
            {
                throw new UnknownEventException("Unknown event: " + action + " from file.");
            }
        }
        return eventSequence;
    }

    private static JSONObject getJsonObjectFromFile(String filePath) throws IOException, JSONException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        StringBuilder content = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            content.append(line);
        }
        reader.close();

        return (content.length() == 0)
                ? new JSONObject() : new JSONObject(content.toString());
    }
}
