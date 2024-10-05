package renderEngine;

import de.matthiasmann.twl.utils.PNGDecoder;
import models.RawModel;
import org.lwjgl.BufferUtils;
import textures.TextureData;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
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

    public RawModel loadToVao(float[] positions,float[] textureCoords, float[] normals, int[] indices)
    {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbind();
        return new RawModel(vaoID, indices.length);
    }

    public RawModel loadToVAO(float[] positions, int dimensions)
    {
        int vaoID = createVAO();
        storeDataInAttributeList(0, dimensions, positions);
        unbind();
        return new RawModel(vaoID,positions.length/dimensions);
    }

    public int loadTexture(String fileName)
    {

        Texture texture = new Texture();
        texture.init("res/models/" + fileName + ".png");
        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS,-0.4f );
        textures.add(texture.getTexId());
        return texture.getTexId();
    }

    public int loadCubeMap(String[] textureFiles)
    {
        int texID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texID);

        for(int i = 0; i < textureFiles.length; i++)
        {
            TextureData data = decodeTextureFile("res/models/" + textureFiles[i] + ".png");
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, data.getWidth(), data.getHeight(),0, GL_RGBA, GL_UNSIGNED_BYTE, data.getBuffer());
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
        textures.add(texID);
        return texID;
    }

    private TextureData decodeTextureFile(String fileName) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try {
            FileInputStream in = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ", didn't work");
            System.exit(-1);
        }
        return new TextureData(buffer, width, height);
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
