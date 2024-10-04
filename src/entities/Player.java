package entities;

import engineTester.MainGameLoop;
import input.KeyInput;
import models.TexturedModel;
import org.joml.Vector3f;
import terrains.Terrain;

import java.awt.event.KeyEvent;

public class Player extends Entity
{
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
    {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain terrain)
    {
        checkInputs();
        increaseRotation(0, currentTurnSpeed * MainGameLoop.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * MainGameLoop.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
        increasePosition(dx,0,dz);
        upwardsSpeed += GRAVITY * MainGameLoop.getFrameTimeSeconds();
        increasePosition(0, upwardsSpeed * MainGameLoop.getFrameTimeSeconds(),0);
        float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
        if(getPosition().y < terrainHeight)
        {
            upwardsSpeed = 0;
            getPosition().y = terrainHeight;
            isInAir = false;
        }
    }

    private void jump()
    {
        if(!isInAir) {
            upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }

    }

    private void checkInputs()
    {
        if(KeyInput.GetKey('W')){
            currentSpeed = RUN_SPEED;
        }else if(KeyInput.GetKey('S')){
            currentSpeed = -RUN_SPEED;
        }else{
            currentSpeed = 0;
        }

        if(KeyInput.GetKey('D')){
            currentTurnSpeed = -TURN_SPEED;
        }else if(KeyInput.GetKey('A')){
            currentTurnSpeed = TURN_SPEED;
        }else{
            currentTurnSpeed = 0;
        }

        if(KeyInput.GetKeyDown(KeyEvent.VK_SPACE)){
            jump();
        }
    }
}
