package renderEngine;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture
{
    private String filepath;
    private int texID;
    private int width, height;

    public Texture()
    {

    }

    public void init(String filepath)
    {
        this.filepath = filepath;

        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        // When shrinking an image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);

//        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(filepath,w,h,c, 0);

        if (image != null) {
            width = w.get(0);
            height = h.get(0);
            if (c.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w.get(0), h.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (c.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(0), h.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                assert false : "Error: (Texture) Unknown number of channels '" + c.get(0) + "'";
            }
        } else {
            assert false : "Error: (Texture) Could not load image '" + filepath + "'";
        }


        stbi_image_free(image);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth(){ return width; }
    public int getHeight(){return height; }

    public int getTexId()
    {
        return texID;
    }
}
