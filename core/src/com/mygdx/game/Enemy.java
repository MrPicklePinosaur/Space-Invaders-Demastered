package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Enemy extends Entity {

    protected int r=3,maxSpeed=3,minSpeed=1;
    public Enemy(Texture texture) {
        super(texture);
        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius((this.sprite.getWidth()/2f)/Global.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body =  this.create(fdef);
        System.out.println(this.posX);
        this.sprite = new Sprite(new Texture("ship-blue.png"));
        this.sprite.setSize(64,64*(this.sprite.getHeight()/this.sprite.getWidth())); //ALSO GET SHIP RESOLUTION IN CASE THE SPRITE IS NOT A SQUARE


        this.sprite.setOrigin(this.sprite.getWidth()/2f,this.sprite.getHeight()/2f); //allows sprite to rotate around center
        this.sprite.setPosition(400f/2f-this.sprite.getWidth()/2f,400f/2f-this.sprite.getHeight()/2f); //set sprite as starting in center of screen
        this.sprite.setRotation(-90);

    }

    @Override
    public void update() {
        //AI stuff goes here
    }

    @Override
    public void destroy() {
        //Remove enemy from list or whatever here
    }
}
