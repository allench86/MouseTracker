import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;

public class SikuliMouseTest
{

    public static void main(String[] args) throws InterruptedException
    {
        Thread.sleep(5000);
        Mouse mouse = new DesktopMouse();
        DesktopScreen firstScreen = new DesktopScreen(0);
        ScreenLocation point1 = new DefaultScreenLocation(firstScreen, 95, 210);
        ScreenLocation point2 = new DefaultScreenLocation(firstScreen, 570, 384);

        // SET THE MOUSE X Y POSITION
        mouse.move(point1);
        mouse.click(point1);
        Thread.sleep(3000);
        mouse.drag(point1);
        mouse.drop(point2);
        Thread.sleep(10000);
    }
}
