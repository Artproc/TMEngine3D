package engineTester;

import input.KeyInput;
import models.TexturedModel;
import renderEngine.Application;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import static org.lwjgl.glfw.GLFW.*;

public class MainGameLoop
{


    public static void main(String[] args)
    {
        long glfwWindow = Application.Init();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

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

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel model = loader.loadToVao(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("alien"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (!glfwWindowShouldClose(glfwWindow)) {
            KeyInput.Reset();
            glfwPollEvents();

            renderer.prepare();
            shader.start();

            renderer.render(texturedModel);

            shader.stop();

            glfwSwapBuffers(glfwWindow);
        }
        shader.cleanUp();
        loader.cleanUp();
        glfwTerminate();
    }
}
