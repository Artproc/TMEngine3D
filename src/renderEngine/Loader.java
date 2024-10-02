package renderEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glGetActiveAttrib;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Loader
{
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVao(float[] position,float[] textureCoords, int[] indices)
    {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, position);
        storeDataInAttributeList(1, 2, textureCoords);
        unbind();
        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(String fileName)
    {
        Texture texture = new Texture();
        texture.init("res/models/" + fileName + ".png");
        textures.add(texture.getTexId());
        return texture.getTexId();
    }

    private int createVAO()
    {
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber,int size, float[] data)
    {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0,0 );
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void unbind()
    {
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices)
    {
        int iboID = glGenBuffers();
        vbos.add(iboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

    }

    private IntBuffer storeDataInIntBuffer(int[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void cleanUp()
    {
        for(int vao : vaos) {
            glDeleteVertexArrays(vao);
        }
        for(int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        for(int tex : textures)
            glDeleteTextures(tex);
        vaos.clear();
        vbos.clear();
        textures.clear();
    }
}
