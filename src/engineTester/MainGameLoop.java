package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import input.KeyInput;
import models.TexturedModel;
import org.joml.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

import static org.lwjgl.glfw.GLFW.*;

public class MainGameLoop
{


    public static void main(String[] args)
    {
        long glfwWindow = Application.Init();

        Loader loader = new Loader();


        RawModel model = OBJLoader.loadObjModel("dragon", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(staticModel,new Vector3f(0, -5, -25), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(3000,2000, 3000), new Vector3f(1,1,1));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        while (!glfwWindowShouldClose(glfwWindow)) {
            KeyInput.Reset();
            glfwPollEvents();
            entity.increaseRotation(0,0.03f,0);
            camera.move();

            renderer.processEntity(entity);
            renderer.render(light, camera);

            glfwSwapBuffers(glfwWindow);
        }
        renderer.cleanUp();
        loader.cleanUp();
        glfwTerminate();
    }
}
