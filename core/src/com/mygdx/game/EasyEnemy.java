package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EasyEnemy extends Enemy{
    //private int x;
    //private int y;
    //private int hp;
    //private float theta;
    private int r=3,speed=3;
    //private Sprite sprite;
    static int SHIP_SIZE = 64;
    public EasyEnemy(Texture texture){
        super(texture);
        //x=0;y=0;hp=3;
        this.sprite = new Sprite(new Texture("ship-blue.png"));
        this.sprite.setSize(SHIP_SIZE,SHIP_SIZE*(this.sprite.getHeight()/this.sprite.getWidth())); //ALSO GET SHIP RESOLUTION IN CASE THE SPRITE IS NOT A SQUARE
        this.sprite.setOrigin(this.sprite.getWidth()/2f,this.sprite.getHeight()/2f); //allows sprite to rotate around center

        this.sprite.setPosition(400f/2f-this.sprite.getWidth()/2f,400f/2f-this.sprite.getHeight()/2f); //set sprite as starting in center of screen
        this.sprite.setRotation(-90);
    }
    public void move(float pX, float pY){
        //System.out.println(true);
        if(Math.sqrt(Math.pow(pX-this.posX,2)+Math.pow(pY-this.posY,2))>150){
            //First stage: Running directly at the player
            System.out.println(true);
            this.rotation = (float) Math.atan2(pX-this.posX,pY-this.posY);
            this.posX+=this.r*Math.cos(this.rotation);
            this.posY+=this.r*Math.sin(this.rotation);

        }else{
            //Second stage: When close enough, revolve around player in circle
            
        }
    }
    public void draw(SpriteBatch batch){
        this.sprite.draw(batch);
    }

}
