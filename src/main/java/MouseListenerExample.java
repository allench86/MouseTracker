import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class MouseListenerExample implements NativeMouseInputListener
{
    public void nativeMouseClicked(NativeMouseEvent e)
    {
        System.out.println(e.getButton() + " Mosue Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e)
    {
        System.out.println(e.getButton() + " Mosue Pressed: " + e.getButton() + " : " + e.getWhen());
    }

    public void nativeMouseReleased(NativeMouseEvent e)
    {
        System.out.println(e.getButton() + " Mosue Release: " + e.getButton() + " : " + e.getWhen());
    }

    public void nativeMouseMoved(NativeMouseEvent e)
    {
        // System.out.println("Mosue Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e)
    {
        // System.out.println(e.getButton() + " Mosue Dragged: " + e.getX() + ", " + e.getY());
    }

    public static void main(String[] args)
    {
        try
        {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex)
        {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        // Construct the example object.
        MouseListenerExample example = new MouseListenerExample();

        // Add the appropriate listeners for the example object.
        GlobalScreen.getInstance().addNativeMouseListener(example);
        GlobalScreen.getInstance().addNativeMouseMotionListener(example);
    }
}
