import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardListenerExample implements NativeKeyListener
{
    public void nativeKeyPressed(NativeKeyEvent e)
    {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        System.out.println("Key char Pressed: " + e.getKeyChar());
        System.out.println("Key code Pressed: " + e.getKeyCode());
        System.out.println("Raw code Pressed: " + e.getRawCode());
        System.out.println("Modifier text Pressed: " + NativeKeyEvent.getModifiersText(e.getRawCode()));
        System.out.println("ParamString Pressed: " + e.paramString());
        System.out.println("----------------------");

        if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE)
        {
            GlobalScreen.unregisterNativeHook();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e)
    {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        System.out.println("Key char Released: " + e.getKeyChar());
        System.out.println("Key code Released: " + e.getKeyCode());
        System.out.println("Raw code Released: " + e.getRawCode());
        System.out.println("Modifier text Released: " + NativeKeyEvent.getModifiersText(e.getRawCode()));
        System.out.println("ParamString Released: " + e.paramString());
        System.out.println("----------------------");

    }

    public void nativeKeyTyped(NativeKeyEvent e)
    {
        // System.out.println("Raw Code Typed: " + e.getRawCode());
        // System.out.println("Key code Typed: " + e.getKeyCode());
        // System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
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

        // Construct the example object and initialze native hook.
        GlobalScreen.getInstance().addNativeKeyListener(new KeyboardListenerExample());
    }
}
