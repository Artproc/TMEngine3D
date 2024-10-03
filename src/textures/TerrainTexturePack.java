package textures;

public class TerrainTexturePack
{
    private TerrainTexture backgroundTexture;
    private TerrainTexture rTexture;
    private TerrainTexture gTexture;
    private TerrainTexture bTexture;

    public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture)
    {
        this.gTexture = gTexture;
        this.backgroundTexture = backgroundTexture;
        this.bTexture = bTexture;
        this.rTexture = rTexture;
    }

    public TerrainTexture getBackgroundTexture()
    {
        return backgroundTexture;
    }

    public TerrainTexture getbTexture()
    {
        return bTexture;
    }

    public TerrainTexture getgTexture()
    {
        return gTexture;
    }

    public TerrainTexture getrTexture()
    {
        return rTexture;
    }
}
