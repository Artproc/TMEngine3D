package entities;

import input.KeyInput;
import org.joml.Vector3f;

import java.awt.event.KeyEvent;

public class Camera
{
    private Vector3f position = new Vector3f();
    private float pitch;
    private float yaw;
    private float roll;

    public Camera()
    {
    }

    public void move()
    {

        if(KeyInput.GetKey('W'))
        {
            position.z -= 0.002f;
            if(position.z <= 0.025f)
                position.z = (float) Math.max(position.z, 0.025);
        }
        if(KeyInput.GetKey('S'))
        {
            position.z += 0.002f;

        }
        if(KeyInput.GetKey('D'))
        {
            position.x += 0.002f;
        }
        if(KeyInput.GetKey('A'))
        {
            position.x -= 0.002f;
        }
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
