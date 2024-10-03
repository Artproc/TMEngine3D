package entities;

import input.KeyInput;
import org.joml.Vector3f;

import java.awt.event.KeyEvent;

public class Camera
{
    private Vector3f position = new Vector3f(0,1,0);
    private float pitch = 0;
    private float yaw = 0;
    private float roll = 0;

    public Camera()
    {
    }

    public void move()
    {

        if(KeyInput.GetKey('W'))
        {
            position.z -= 0.05f;

        }
        if(KeyInput.GetKey('S'))
        {
            position.z += 0.05f;

        }
        if(KeyInput.GetKey('D'))
        {
            position.x += 0.05f;
        }
        if(KeyInput.GetKey('A'))
        {
            position.x -= 0.05f;
        }
        if(KeyInput.GetKey(KeyEvent.VK_SPACE))
        {
            position.y += 0.05f;
        }
        if(KeyInput.GetKey(KeyEvent.VK_BACK_SLASH))
        {
            position.y -= 0.05f;
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
