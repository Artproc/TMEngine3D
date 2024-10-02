package renderEngine;

import input.KeyInput;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Application
{
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int FPS_CAP = 60;
    private static final String TITLE = "Our First Display";
    private static long window;

    public static long Init()
    {
        //If glfw cannot be initialized
        if(!glfwInit())
        {
            //Print an error to the console and close the application
            System.err.println("GLFW Failed to Initialize");
            System.exit(1);
        }

        //Create the window, show it and make the context current
        window = glfwCreateWindow(WIDTH,HEIGHT, TITLE, 0, 0);
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);

        glfwSetKeyCallback(window, new KeyInput());

        //Create capabilities and set the background color
        GL.createCapabilities();
        glClearColor(0, 0, 0, 1);

        //Return the window
        return window;
    }

    public static long getWindow(){return window;}
    public static float getWidth(){return WIDTH;}
    public static float getHeight(){return HEIGHT;}
}
