package shaders;

import entities.Camera;
import entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import toolbox.Maths;

import java.util.List;

public class TerrainShader extends ShaderProgram
{
    private static final int MAX_LIGHT = 4;

    private static final String VERTEX_FILE = "res/shaders/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "res/shaders/terrainFragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;



    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations()
    {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
        location_backgroundTexture = super.getUniformLocation("backgroundTexture");
        location_rTexture = super.getUniformLocation("rTexture");
        location_gTexture = super.getUniformLocation("gTexture");
        location_bTexture = super.getUniformLocation("bTexture");
        location_blendMap = super.getUniformLocation("blendMap");

        location_lightPosition = new int[MAX_LIGHT];
        location_lightColour = new int[MAX_LIGHT];
        location_attenuation = new int[MAX_LIGHT];
        for(int i = 0; i < MAX_LIGHT; i++)
        {
            location_lightPosition[i] = getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = getUniformLocation("attenuation[" + i + "]");
        }
    }

    public void connectTextureUnits()
    {
        super.loadInt(location_backgroundTexture, 0);
        super.loadInt(location_rTexture, 1);
        super.loadInt(location_gTexture, 2);
        super.loadInt(location_bTexture, 3);
        super.loadInt(location_blendMap, 4);
    }

    public void loadSkyColour(float r, float g, float b)
    {
        super.loadVector3f(location_skyColour, new Vector3f(r, g, b));
    }

    public void loadShineVariables(float damper, float reflectivity)
    {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadLights(List<Light> lights)
    {
        for(int i = 0; i < MAX_LIGHT; i++)
        {
            if(i < lights.size())
            {
                super.loadVector3f(location_lightPosition[i], lights.get(i).getPosition());
                super.loadVector3f(location_lightColour[i], lights.get(i).getColour());
                super.loadVector3f(location_attenuation[i], lights.get(i).getAttenuation());
            }
            else {
                super.loadVector3f(location_lightPosition[i], new Vector3f(0));
                super.loadVector3f(location_lightColour[i], new Vector3f(0));
                super.loadVector3f(location_attenuation[i], new Vector3f(1,0,0));
            }
        }
    }

    public void loadViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_projectionMatrix, matrix);
    }
}
