/*
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class MediumEnemy extends Enemy{
    //private int x;
    //private int y;
    //private int hp;
    //private float theta;
    //public static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
    //public static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    //parameter.size = 12;
    //BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
    private int r=3,speed=3;
    //private b2RevoluteJointDef b2DJ;
    //private Sprite sprite;
    static int SHIP_SIZE = 64;
    private RevoluteJoint j;
    private RevoluteJointDef jDef;
    public MediumEnemy(Texture texture, Player player){
        super(texture);
        //x=0;y=0;hp=3;
        this.sprite = new Sprite(new Texture("ship-blue.png"));
        this.sprite.setSize(SHIP_SIZE,SHIP_SIZE*(this.sprite.getHeight()/this.sprite.getWidth())); //ALSO GET SHIP RESOLUTION IN CASE THE SPRITE IS NOT A SQUARE
        this.sprite.setOrigin(this.sprite.getWidth()/2f,this.sprite.getHeight()/2f); //allows sprite to rotate around center


        Resources:
        https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/physics/box2d/joints/RevoluteJoint.html
        https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/physics/box2d/joints/RevoluteJointDef.html
        http://www.iforce2d.net/b2dtut/joints-revolute


        this.jDef = new RevoluteJointDef(); //need this to define what joint is actually between

        this.jDef.initialize(this.body,player.body,player.body.getWorldCenter()); //defining what joint is actually between

        this.jDef.motorSpeed = (float)Math.toRadians(3);//-Math.PI*3;   //how fast motor goes -- is in radians / second, for some reason
        this.jDef.collideConnected = true;   //we want box2d to have the two bodies collide normally if they do so, instead of ignoring them in a joint (as is default)

        this.j = new RevoluteJoint(Global.world,0000);

        this.sprite.setPosition(400f/2f-this.sprite.getWidth()/2f,400f/2f-this.sprite.getHeight()/2f); //set sprite as starting in center of screen
        this.sprite.setRotation(-90);
    }
    public void move(float pX, float pY){
        //System.out.println(true);
        if(Math.sqrt(Math.pow(pX-this.getX(),2)+Math.pow(pY-this.getY(),2))>(this.sprite.getY()+this.sprite.getWidth())){
            //First stage: Running directly at the player
            //System.out.println(true);
            this.rotation = (float) Math.atan2(pX-this.body.getPosition().x,pY-this.body.getPosition().x);
            this.body.getPosition().x+=this.r*Math.cos(this.rotation);
            this.body.getPosition().y+=this.r*Math.sin(this.rotation);

        }else{
            System.out.println('collision');
            //Second stage: Explode

        }
    }
    public void kill(){
        //add dispose methods for joints, bodies here
        //look at:
        //http://www.iforce2d.net/b2dtut/joints-overview
        //generator.dispose(); // don't forget to dispose to avoid memory leaks!
    }
    public void draw(SpriteBatch batch){
        this.sprite.draw(batch);
    }

}
*/
