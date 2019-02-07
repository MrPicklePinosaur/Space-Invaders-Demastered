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

    public Entity(Texture texture,float max_speed) {
        this.sprite = new Sprite(texture);
        this.speed = speed;
    }

    public Body create(FixtureDef fdef, BodyDef.BodyType bodyType) { ///takes in a fixture definition and creates a body
        //Create the body
        BodyDef bdef = new BodyDef();
        bdef.type = bodyType;
        bdef.position.set(this.getX()/Global.PPM,this.getY()/Global.PPM);
        Body new_body = Global.world.createBody(bdef);
        new_body.createFixture(fdef); //DONT FORGET TO DISPOSE OF fdef

        return new_body;
    }

    public void createJoint() {
    }

    public void update() { //Sync sprite with body (sprite follows body)
        //Sync position
        Vector2 bodyPos = this.body.getPosition(); //get body position
        this.sprite.setPosition(bodyPos.x-this.sprite.getWidth()/2f,bodyPos.y-this.sprite.getHeight()/2f);

        //Sync rotation
        sprite.setRotation(body.getAngle()*MathUtils.radiansToDegrees-90);
    }

    //dx and dy are the distance the object is from the destination
    public void applyForce(float forceDirect) { //moves entity given target destination by applying forces
        this.body.applyForceToCenter(new Vector2(this.speed*MathUtils.cos(forceDirect),this.speed*MathUtils.sin(forceDirect)),true);
    }

    public abstract void destroy();

    //Getters
    public float getX() { return this.sprite.getX(); } //TODO: getters should get body positions
    public float getY() { return this.sprite.getY(); }
    public float getRotation() { return this.body.getAngle(); } //make decision on rotation being in radians or degrees

    //Setters
    //NOTE: DECIDE WETHER OR NOT THE ARGUMENTS SHOULD BE IN PIXELS OR METERS
    public void init(float posX, float posY, float rotation) { //used to place an entity in a specific orientation in the world
        this.body.setTransform(posX,posY,rotation);
    }

}
