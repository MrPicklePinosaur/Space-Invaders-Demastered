package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


public class Renderer {
    OrthographicCamera cam;
    float WINDOW_SIZE_X;
    float WINDOW_SIZE_Y;
    float CAM_SIZE_X;
    float CAM_SIZE_Y;
    float RESOLUTION;
    Sprite sprite,background;
    int SHIP_SIZE = 64;
    int MAP_SIZE = 40000;
    EasyEnemy e;

    float speed = 10;

    public Renderer(SpriteBatch batch){
        cam = new OrthographicCamera(400,400);
        WINDOW_SIZE_X = Gdx.graphics.getWidth();
        WINDOW_SIZE_Y = Gdx.graphics.getHeight();
        CAM_SIZE_X = cam.viewportWidth;
        CAM_SIZE_Y = cam.viewportHeight;
        RESOLUTION = (WINDOW_SIZE_Y/WINDOW_SIZE_X);
        //System.out.println(CAM_SIZE_X+" "+CAM_SIZE_Y);

        sprite = new Sprite(new Texture("ship-blue.png"));
        background = new Sprite(new Texture("librotation/space.jpg"));
        sprite.setSize(SHIP_SIZE,SHIP_SIZE*(sprite.getHeight()/sprite.getWidth())); //ALSO GET SHIP RESOLUTION IN CASE THE SPRITE IS NOT A SQUARE
        background.setSize(MAP_SIZE,MAP_SIZE*RESOLUTION); //fix height with resolution

        e = new EasyEnemy(new Texture("ship-blue.png"));

        sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f); //allows sprite to rotate around center
        sprite.setPosition(CAM_SIZE_X/2f-sprite.getWidth()/2f,CAM_SIZE_Y/2f-sprite.getHeight()/2f); //set sprite as starting in center of screen
        sprite.setRotation(-90);
        background.setPosition(0,0);

        cam.position.set(CAM_SIZE_X/2f-sprite.getWidth()/2f,CAM_SIZE_Y/2f-sprite.getHeight()/2f,0); //offset camera to center of screen
        cam.zoom = 3f;
        cam.update();
    }
    public void draw(SpriteBatch batch){
        handleInput();
        //System.out.println(sprite.getX()+" "+sprite.getY());
        e.move(sprite.getX(),sprite.getY());
        cam.update(); //update camera
        batch.setProjectionMatrix(cam.combined);
        background.draw(batch);
        sprite.draw(batch);
        e.draw(batch);
        //rest of draw will come here later, with more objs being added
    }
    public void handleInput() {
        //Find out how far mouse is from center of screen
        float shiftX = Gdx.input.getX()-WINDOW_SIZE_X/2f;
        float shiftY = WINDOW_SIZE_Y/2f-Gdx.input.getY();
        float angle = MathUtils.atan2(shiftY,shiftX); //calculate degree of mouse relative to center of screen

        //MOVE PLAYER
        //move player depending on direction of mouse
        float deltaX = speed*MathUtils.cos(MathUtils.degreesToRadians*(sprite.getRotation()+90));
        float deltaY = speed*MathUtils.sin(MathUtils.degreesToRadians*(sprite.getRotation()+90));

        sprite.translate(deltaX, deltaY); //move sprite

        //ROTATE SHIP
        //THIS BLOCK OF CODE IS FROM https://gamedev.stackexchange.com/questions/108795/libgdx-rotatetoaction-does-not-directly-rotate-between-179-and-179
        float mouseAngle = angle*MathUtils.radiansToDegrees-90;
        float shipAngle = (sprite.getRotation()+360)%360;
        if (shipAngle - mouseAngle > 180) mouseAngle += 360;
        if (mouseAngle - shipAngle > 180) shipAngle += 360;
        sprite.setRotation((shipAngle+(mouseAngle-shipAngle)*0.07f)%360);


        //Acceleration/deceleration
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speed += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speed -= 0.1;
        }
        speed = MathUtils.clamp(speed,6,20);

        //MOVE CAMERA
        //Camera shifts in direction of mouse and along with player
        cam.translate(deltaX+shiftX/128f,deltaY+shiftY/128f);
        //cam.translate(deltaX,deltaY);

        //ZOOM
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) { //zoom in
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) { //zoom out
            cam.zoom += 0.02;
        }


        //Make sure camera cant zoom out too much and cant go off map
        cam.zoom = MathUtils.clamp(cam.zoom, 1, 5); //the camera can only zoom up to half of the whole map

        //float effectiveViewportWidth = CAM_SIZE_X * cam.zoom;
        //float effectiveViewportHeight = CAM_SIZE_Y * cam.zoom;

        //clamp camera position to be a set distance away from player
        float spriteCX = sprite.getX()+sprite.getWidth()/2f;
        float spriteCY = sprite.getY()+sprite.getHeight()/2f;
        float maxCamDist = 60; //max distance camera is allowed from player

        cam.position.x = MathUtils.clamp(cam.position.x,spriteCX-maxCamDist,spriteCX+maxCamDist);
        cam.position.y = MathUtils.clamp(cam.position.y,spriteCY-maxCamDist,spriteCY+maxCamDist);

    }

}
