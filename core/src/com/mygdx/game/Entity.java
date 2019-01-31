package com.mygdx.game;
//The superclass of player, enemy and objects (like projectiles!)
//Helps sync the entity's fixture with the sprite

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

//     'An entity is anything that has position and texture'
//      All entities are DYNAMIC BODIES
public abstract class Entity {
    //NOTE: I dont think you need to have protected variables in an abstract class, look into further later
    protected Sprite sprite;
    protected Body body;
    protected float posX;
    protected float posY;
    protected float rotation;

    //NOTE: i dont like having world as an argument
    public Entity(World world, Texture texture) { //NOTE: superclass never uses world
        this.sprite = new Sprite(texture);
        this.posX = 0;
        this.posY = 0;
        this.rotation = 0;
    }
    //NOTE: i dont like having world as an argument
    public Body create(World world, FixtureDef fdef) { ///takes in a fixture definition and creates a body
        //Create the body
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(this.posX/Constants.PPM,this.posY/Constants.PPM);
        Body new_body = world.createBody(bdef);
        new_body.createFixture(fdef);

        return new_body;
    }
    public abstract void update();
    public abstract void destroy();

    //Getters
    public float getX() { return this.posX; }
    public float getY() { return this.posY; }
    public float getRotation() { return this.rotation; } //make decision on rotation being in radians or degrees

    //Setters
    public void init(float posX, float posY, float rotation) { //used to place an entity in a specific orientation in the world
        this.setX(posX);
        this.setY(posY);
        this.setRotation(rotation);
    }
    //These methods may be one time use, so possibly merge with init()
    public void setX(float posX) { this.posX = posX; }
    public void setY(float posY) { this.posY = posY;}
    public void setRotation(float rotation) {this.rotation = rotation; }
}
