//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 ______     __   __     ______   __     ______   __  __
/\  ___\   /\ "-.\ \   /\__  _\ /\ \   /\__  _\ /\ \_\ \
\ \  __\   \ \ \-.  \  \/_/\ \/ \ \ \  \/_/\ \/ \ \____ \
 \ \_____\  \ \_\\"\_\    \ \_\  \ \_\    \ \_\  \/\_____\
  \/_____/   \/_/ \/_/     \/_/   \/_/     \/_/   \/_____/
 */
//The superclass of player, enemy and objects (like projectiles!)
//Helps sync the entity's fixture with the sprite
//     'An entity is anything that has position and texture'

package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Entity {
    //NOTE: I dont think you need to have protected variables in an abstract class, look into further later
    protected Sprite sprite;
    protected Body body;
    protected float speed;

    public Entity(Texture texture,float speed) {
        this.sprite = new Sprite(texture);
        this.speed = speed;
        this.sprite.setSize(this.sprite.getWidth()/Global.PPM,this.sprite.getHeight()/Global.PPM);
        this.sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f); //allows sprite to rotate around center
    }

    public Body create(FixtureDef fdef, BodyDef.BodyType bodyType) { ///takes in a fixture definition and creates a body
        //Create the body
        BodyDef bdef = new BodyDef();
        bdef.type = bodyType;
        Body new_body = Global.world.createBody(bdef);
        new_body.createFixture(fdef); //DONT FORGET TO DISPOSE OF fdef

        return new_body;
    }

    public void update() { //Sync sprite with body (sprite follows body)
        //Sync position
        Vector2 bodyPos = this.body.getPosition(); //get body position
        //this.body.getWorldCenter()
        this.sprite.setPosition(bodyPos.x-this.sprite.getWidth()/2f,bodyPos.y-this.sprite.getHeight()/2f);

        //Sync rotation
        sprite.setRotation(body.getAngle()*MathUtils.radiansToDegrees-90);
    }

    public void rotate(float targetAngle,float rotateSpeed) { //non-instateous rotation of body
        //THIS BLOCK OF CODE IS FROM https://gamedev.stackexchange.com/questions/108795/libgdx-rotatetoaction-does-not-directly-rotate-between-179-and-179
        float endAngle = targetAngle;
        float startAngle = (this.body.getAngle()+MathUtils.PI2)%MathUtils.PI2;
        if (startAngle - endAngle > MathUtils.PI) endAngle += MathUtils.PI2;
        if (endAngle - startAngle > MathUtils.PI) startAngle += MathUtils.PI2;
        float rotate = (startAngle+(endAngle-startAngle)*rotateSpeed)%MathUtils.PI2; //the amount the ship rotates
        this.body.setTransform(this.body.getPosition().x,this.body.getPosition().y,rotate); //rotate player body without touching position
    }

    //Getters
    public float getX() { return this.body.getPosition().x; }
    public float getY() { return this.body.getPosition().y; }
    public float getRotation() { return this.body.getAngle(); } //make decision on rotation being in radians or degrees
    public float getSpeed() { return this.speed; }
    public Body getBody() { return this.body; }

    //Setters
    //NOTE: DECIDE WETHER OR NOT THE ARGUMENTS SHOULD BE IN PIXELS OR METERS
    //SHOULD BE CALLED ONLY ONCE
    public void init(float posX, float posY, float angle) { //used to place an entity in a specific orientation in the world
        this.body.setTransform(posX,posY,angle);
    }

}
