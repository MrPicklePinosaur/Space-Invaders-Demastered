package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import java.lang.Math.*;

public class EasyEnemy extends Enemy{
    //private int x;
    //private int y;
    //private int hp;
    //private float theta;
    private int r=2;//,speed=3;
    //private b2RevoluteJointDef b2DJ;
    //private Sprite sprite;
    static int SHIP_SIZE = 64;
    private RevoluteJoint j;
    private RevoluteJointDef jDef;
    public EasyEnemy(Texture texture,Player player){
        super(texture);
        //x=0;y=0;hp=3;
        //this.sprite = new Sprite(new Texture("ship-blue.png"));
        //Create body for enemy - same code as in player class - reason not in entity bc not sure if all entities will have circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius((this.sprite.getWidth()/2f)/Global.PPM); //The fixture for the player is a circle with radius spriteWidth/2
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body =  this.create(fdef);
        this.body.getPosition().x = player.body.getPosition().x-100;
        this.body.getPosition().y = player.body.getPosition().y-100;

        //this.sprite.setSize(SHIP_SIZE,SHIP_SIZE*(this.sprite.getHeight()/this.sprite.getWidth())); //ALSO GET SHIP RESOLUTION IN CASE THE SPRITE IS NOT A SQUARE
        this.sprite.setSize(this.sprite.getWidth()/Global.PPM,this.sprite.getHeight()/Global.PPM);
        this.sprite.setOrigin(this.sprite.getWidth()/2f,this.sprite.getHeight()/2f); //allows sprite to rotate around center

        /*
        Resources:
        https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/physics/box2d/joints/RevoluteJoint.html
        https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/physics/box2d/joints/RevoluteJointDef.html
        http://www.iforce2d.net/b2dtut/joints-revolute
        */

        this.jDef = new RevoluteJointDef(); //need this to define what joint is actually between
        jDef.bodyA = player.body;
        jDef.bodyB = this.body;
        Global.world.createJoint(jDef);
        this.jDef.initialize(this.body,player.body,player.body.getWorldCenter()); //defining what joint is actually between

        this.jDef.motorSpeed = (float)Math.toRadians(3);//-Math.PI*3;   //how fast motor goes -- is in radians / second, for some reason
        this.jDef.collideConnected = true;   //we want box2d to have the two bodies collide normally if they do so, instead of ignoring them in a joint (as is default)

        this.j = new RevoluteJoint(Global.world,0000);

        this.sprite.setPosition(400f/2f-this.sprite.getWidth()/2f,400f/2f-this.sprite.getHeight()/2f); //set sprite as starting in center of screen
        this.sprite.setRotation(-90);
    }
    public void move(Body p){//float pX, float pY){
        //System.out.println(true);
        //double dist = Math.sqrt(Math.pow(pX-this.body.getPosition().x,2)+Math.pow(pY-this.body.getPosition().y,2));
        double dist = Math.sqrt(Math.pow(p.getWorldCenter().x-this.body.getWorldCenter().x,2)+Math.pow(p.getWorldCenter().y-this.body.getWorldCenter().y,2));
        if(dist>(150/Global.PPM)){
            //First stage: Running directly at the player
            //System.out.println(Math.sqrt(Math.pow(pX-this.getX(),2)+Math.pow(pY-this.getY(),2)));
            //System.out.println("Enemy position: ("+this.body.getPosition().x+", "+this.body.getPosition().y+"); Player position: ("+pX+", "+pY+")");
            this.rotation = (float) Math.atan2(p.getPosition().y-this.body.getPosition().y,p.getPosition().x-this.body.getPosition().x);
            //this.body.getPosition().x+=this.r*Math.cos(this.rotation);
            //this.body.getPosition().y+=this.r*Math.sin(this.rotation);
            this.body.setTransform(this.body.getPosition().x+(float)(this.r*Math.cos(this.rotation)/Global.PPM),this.body.getPosition().y+(float)(this.r*Math.sin(this.rotation)/Global.PPM),rotation);

        }else{
            System.out.println(dist);
            //if(!this.j.isMotorEnabled()){
                //this.j.enableMotor(true);
            //}
            //Second stage: When close enough, revolve around player in circle

        }
        this.update();
    }
    public void kill(){
        //add dispose methods for joints, bodies here
        //look at:
        //http://www.iforce2d.net/b2dtut/joints-overview

    }
    /*public void draw(SpriteBatch batch){
        this.sprite.draw(batch);
    }*/

}
