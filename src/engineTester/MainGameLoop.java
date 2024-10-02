package engineTester;

import input.KeyInput;
import renderEngine.Application;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

import static org.lwjgl.glfw.GLFW.*;

public class MainGameLoop
{


    public static void main(String[] args)
    {
        long glfwWindow = Application.Init();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadToVao(vertices);


        while (!glfwWindowShouldClose(glfwWindow)) {
            KeyInput.Reset();
            glfwPollEvents();

            renderer.prepare();

            renderer.render(model);

            glfwSwapBuffers(glfwWindow);
        }
//        loader.cleanUp();
        glfwTerminate();
    }
}
