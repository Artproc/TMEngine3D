package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import input.KeyInput;
import input.MouseListener;
import models.RawModel;
import models.TexturedModel;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderEngine.Application;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class MainGameLoop
{
        private static long lastFrameTime;
        private static float delta;


    public static void main(String[] args)
    {
        long glfwWindow = Application.Init();

        Loader loader = new Loader();

        //*******************TERRAIN TEXTURE STUFF**************************************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        //******************************************************************************

        RawModel model = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("flower")));

        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
        fernTextureAtlas.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),fernTextureAtlas);

        TexturedModel bobble = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader),
                new ModelTexture(loader.loadTexture("lowPolyTree")));
        TexturedModel box = new TexturedModel(OBJLoader.loadObjModel("box", loader),
                new ModelTexture(loader.loadTexture("box")));

        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 400; i++)
        {
            if(i % 10 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(fern, random.nextInt(4),new Vector3f(x, y,z), 0, random.nextFloat() * 360,
                        0, 0.9f));
            }
            if(i% 5 == 0){
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0,
                        random.nextFloat() * 0.1f + 0.6f));
                x = random.nextFloat() * 800 - 400;
                z = random.nextFloat() * -600;
                y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0,
                        random.nextFloat() * 1 + 4));
            }
        }

        Light light = new Light(new Vector3f(0, 10000, -10000), new Vector3f(1, 1, 1));




        MasterRenderer renderer = new MasterRenderer();

        RawModel bunnyModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel bunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("playerTexture")));

        Entity boxEntity = new Entity(box, new Vector3f(225.5f, 5, -352.6f), 0f, 25f, 0f, 5f);

        Player player = new Player(bunny, new Vector3f(100,5,-150),0,180,0,0.6f);
        Camera camera = new Camera(player);

        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        guis.add(gui);
        GuiTexture gui2 = new GuiTexture(loader.loadTexture("thinmatrix"), new Vector2f(0.3f, 0.74f), new Vector2f(0.4f, 0.4f));
        guis.add(gui2);

        GuiRenderer guiRenderer = new GuiRenderer(loader);

        lastFrameTime = System.currentTimeMillis();
        while (!glfwWindowShouldClose(glfwWindow)) {
            KeyInput.Reset();
            glfwPollEvents();
            camera.move();
            player.move(terrain);

            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.processEntity(boxEntity);
            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            guiRenderer.render(guis);

            glfwSwapBuffers(glfwWindow);
            MouseListener.endFrame();
            long currentFrameTime = System.currentTimeMillis();
            delta = (currentFrameTime - lastFrameTime)/1000f;
            lastFrameTime = currentFrameTime;
        }
        guiRenderer.cleanup();
        renderer.cleanUp();
        loader.cleanUp();
        glfwTerminate();
    }
    public static float getFrameTimeSeconds()
    {
        return delta;
    }
}
