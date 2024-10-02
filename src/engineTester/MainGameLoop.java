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
                 0.5f, 0.5f, 0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        RawModel model = loader.loadToVao(vertices, indices);


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
