package entities;

import org.joml.Vector3f;

public class Camera
{
    private final Vector3f position = new Vector3f(100, 35, 50);
    private final float pitch = 10;
    private final float yaw = 0;
    private final float roll = 0;

    public Camera()
    {
    }

    public void move()
    {

    }

    public Vector3f getPosition()
    {
        return position;
    }

    public float getPitch()
    {
        return pitch;
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getRoll()
    {
        return roll;
    }
}
