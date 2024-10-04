package guis;

import org.joml.Vector2f;

public class GuiTexture
{
    private int texture;
    private Vector2f position;
    private Vector2f scale;

    public GuiTexture(int texture, Vector2f position, Vector2f scale )
    {
        this.position = position;
        this.scale = scale;
        this.texture = texture;
    }

    public Vector2f getPosition()
    {
        return position;
    }

    public Vector2f getScale()
    {
        return scale;
    }

    public int getTexture()
    {
        return texture;
    }
}
