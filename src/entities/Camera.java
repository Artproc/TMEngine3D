package entities;

import input.MouseListener;
import org.joml.Vector3f;

public class Camera
{
    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;


    private final Vector3f position = new Vector3f(100, 35, 50);
    private float pitch = 10;
    private float yaw = 0;
    private final float roll = 0;

    private Player player;

    public Camera(Player player)
    {
        this.player = player;
    }

    public void move()
    {
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        yaw = 180 - (player.getRotY() + angleAroundPlayer);
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

    private void calculateCameraPosition(float hz, float vt)
    {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (hz * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (hz * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + vt;

    }

    private float calculateHorizontalDistance()
    {
        float hd = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
        if(hd < 0)
            hd = 0;
        return hd;
    }
    private float calculateVerticalDistance()
    {
        float vd = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
        if(vd < 0)
            vd = 0;
        return vd;
    }

    private void calculateZoom()
    {
        float zoomLevel = MouseListener.getScrollY() * 2f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch()
    {
        if(MouseListener.mouseButtonDown(1))
        {
            float pitchChange = MouseListener.getDy() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer()
    {
        if(MouseListener.mouseButtonDown(0))
        {
            float angleChange = MouseListener.getDx() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }
}
