package input;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.awt.event.KeyEvent;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyInput extends GLFWKeyCallback
{

    private static boolean anyKey = false;
    private static boolean anyKeyDown = false;
    private static boolean anyKeyUp = false;
    private static boolean[] keys = new boolean[65536];
    private static boolean[] keysDown = new boolean[65536];
    private static boolean[] keysUp = new boolean[65536];

    private static int i;

    public static void Reset()
    {
        for(i = 0; i < keysDown.length; i++) keysDown[i] = false;
        for(i = 0; i < keysUp.length; i++) keysUp[i] = false;
        anyKey = false;
        anyKeyDown = false;
        anyKeyUp = false;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        //return if lwjgl doesn't recognize the key
        if(key == -1) return;

        anyKey = true;
        if(action == GLFW_PRESS) {keysDown[key] = true; anyKeyDown = true; keys[key] = true;}
        if(action == GLFW_RELEASE) {keysUp[key] = true; anyKeyUp = true; keys[key] = false;}
    }

    public static boolean anyKey() {return anyKey;}
    public static boolean GetKey(int keycode) {return keys[keycode];}
    public static boolean GetKey(char keycode) {return keys[KeyEvent.getExtendedKeyCodeForChar(keycode)] ;}

    public static boolean anyKeyDown() {return anyKeyDown;}
    public static boolean GetKeyDown(int keycode) {return keysDown[keycode];}
    public static boolean GetKeyDown(char keycode) {return keysDown[KeyEvent.getExtendedKeyCodeForChar(keycode)];}

    public static boolean anyKeyUp() {return anyKeyUp;}
    public static boolean GetKeyUp(int keycode) {return keysUp[keycode];}
    public static boolean GetKeyUp(char keycode) {return keysUp[KeyEvent.getExtendedKeyCodeForChar(keycode)];}
}
