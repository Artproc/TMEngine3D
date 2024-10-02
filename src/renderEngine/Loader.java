package renderEngine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
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

    public RawModel loadToVao(float[] position)
    {
        int vaoID = createVAO();
        storeDataInAttributeList(0, 3, position);
        unbind();
        return new RawModel(vaoID, position.length / 3);
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
        vaos.clear();
        vbos.clear();
    }
}
