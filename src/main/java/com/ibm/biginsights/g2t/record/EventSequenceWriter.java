package com.ibm.biginsights.g2t.record;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONException;

import com.ibm.biginsights.g2t.visualization.Event;

public class EventSequenceWriter
{
    public static void writeEventSequenceToFile(final String filePath, final List<Event> eventSequence)
            throws IOException,
            JSONException
    {

        File file = new File(filePath);

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        PrintWriter printWriter = new PrintWriter(fileWriter);

        writeOpenBrace(printWriter);
        writeActions(printWriter);
        writeEvents(printWriter, eventSequence);
        writeCloseBrace(printWriter);

        printWriter.close();

    }

    private static void writeEvents(PrintWriter printWriter, List<Event> eventSequence) throws IOException,
            JSONException
    {
        writeOpenBracket(printWriter);
        for (int i = 0; i < eventSequence.size() - 1; i++)
        {
            printWriter.println(eventSequence.get(i).toString() + ",");
        }
        if (eventSequence.size() > 0)
        {
            printWriter.print(eventSequence.get(eventSequence.size() - 1).toString());
        }

        writeCloseBracket(printWriter);
    }

    private static void writeOpenBracket(PrintWriter printWriter)
    {
        printWriter.write("[");
    }

    private static void writeCloseBracket(PrintWriter printWriter)
    {
        printWriter.write("]");
    }

    private static void writeActions(PrintWriter printWriter)
    {
        printWriter.write("\"" + ACTIONS + "\":");
    }

    private static void writeOpenBrace(PrintWriter printWriter)
    {
        printWriter.write("{");
    }

    private static void writeCloseBrace(PrintWriter printWriter)
    {
        printWriter.write("}");
    }

}
