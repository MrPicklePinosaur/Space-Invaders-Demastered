package com.mygdx.game;
//The superclass of player, enemy and objects (like projectiles!)
//Helps sync the entity's fixture with the sprite

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

//     'An entity is anything that has position and texture'
//      All entities are DYNAMIC BODIES
public abstract class Entity {
    //NOTE: I dont think you need to have protected variables in an abstract class, look into further later
    protected Sprite sprite;
    protected Body body;
    protected float rotation;

    public Entity(Texture texture) { //NOTE: superclass never uses world
        this.sprite = new Sprite(texture);
    }

    public Body create(FixtureDef fdef) { ///takes in a fixture definition and creates a body
        //Create the body
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(this.getX()/Global.PPM,this.getY()/Global.PPM);
        Body new_body = Global.world.createBody(bdef);
        new_body.createFixture(fdef); //DONT FORGET TO DISPOSE OF fdef

        return new_body;
    }
    public void update() { //Sync sprite with body (sprite follows body)
        //Sync position
        Vector2 bodyPos = this.body.getPosition(); //get body position
        this.sprite.setPosition(bodyPos.x*Global.PPM,bodyPos.y*Global.PPM);

        //Sync rotation

    }

    public abstract void destroy();

    //Getters
    public float getX() { return this.sprite.getX(); }
    public float getY() { return this.sprite.getY(); }
    public float getRotation() { return this.sprite.getRotation(); } //make decision on rotation being in radians or degrees

    //Setters
    public void init(float posX, float posY, float rotation) { //used to place an entity in a specific orientation in the world
        this.sprite.setX(posX);
        this.sprite.setY(posY);
        this.sprite.setRotation(rotation);
    }

}
