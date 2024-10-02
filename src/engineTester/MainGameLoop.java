package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import input.KeyInput;
import models.TexturedModel;
import org.joml.Vector3f;
import renderEngine.Application;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
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
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("white"));
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel,new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0,0, -20), new Vector3f(1,1,1));

        Camera camera = new Camera();

        while (!glfwWindowShouldClose(glfwWindow)) {
            KeyInput.Reset();
            glfwPollEvents();
            entity.increaseRotation(0,0.03f,0);
            camera.move();

            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);

            renderer.render(entity, shader);

            shader.stop();

            glfwSwapBuffers(glfwWindow);
        }
        shader.cleanUp();
        loader.cleanUp();
        glfwTerminate();
    }
}
