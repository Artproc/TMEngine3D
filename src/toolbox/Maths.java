package toolbox;

import entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths
{
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale)
    {

        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(translation)
                .rotateX((float) Math.toRadians(rx))
                .rotateY((float) Math.toRadians(ry))
                .rotateZ((float) Math.toRadians(rz))
                .scale(scale);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotateX((float) Math.toRadians(camera.getPitch()));
        viewMatrix.rotateY((float) Math.toRadians(camera.getYaw()));
        Vector3f negativePosition = new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
        viewMatrix.translate(negativePosition);
        return viewMatrix;
    }

}
