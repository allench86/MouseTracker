import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseClass
{
    public static void main(String[] args) throws Exception
    {

        Robot robot = new Robot();
        Thread.sleep(5000);

        // robot.mouseMove(1297, 156);
        // robot.mousePress(InputEvent.BUTTON1_MASK);
        // robot.mouseRelease(InputEvent.BUTTON1_MASK);
        // Thread.sleep(5000);

        // SET THE MOUSE X Y POSITION
        robot.mouseMove(77, 207);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.waitForIdle();
        robot.mouseMove(570, 284);
        robot.delay(10000);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.waitForIdle();

        // RIGHT CLICK
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }
}
