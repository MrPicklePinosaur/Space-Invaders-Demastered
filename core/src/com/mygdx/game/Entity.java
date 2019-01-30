package com.mygdx.game;
//The superclass of player, enemy and objects
//Helps sync the entity's fixture with the sprite

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Entity {
    protected Sprite sprite;

    //Positional Variables
    protected float posX;
    protected float posY;
    protected float rotation;

    public Entity() {
        this.sprite = sprite;
        this.posX = 0;
        this.posY = 0;
        this.rotation = 0;
    }
    public Entity(Sprite sprite, float posX, float posY, float rotation) {
        this.sprite = sprite;
        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;
    }

    public void create() {
    }

    public void update() {
    }

    public void destroy() {
    }

    public float getX() { return this.posX; }
    public float getY() { return this.posY; }
    public float getRotation() { return this.rotation; } //make decision on rotation being in radians or degrees

}
