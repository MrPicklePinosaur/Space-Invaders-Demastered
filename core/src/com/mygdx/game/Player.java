//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 ______   __         ______     __  __     ______     ______
/\  == \ /\ \       /\  __ \   /\ \_\ \   /\  ___\   /\  == \
\ \  _-/ \ \ \____  \ \  __ \  \ \____ \  \ \  __\   \ \  __<
 \ \_\    \ \_____\  \ \_\ \_\  \/\_____\  \ \_____\  \ \_\ \_\
  \/_/     \/_____/   \/_/\/_/   \/_____/   \/_____/   \/_/ /_/
 */
//Handles player input

package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Input;

public class Player extends Entity {
    private String name;
    private float max_hp;
    private float hp;
    private float ship_speed = 1f; //in m/s
    private float SHIP_SIZE = 64;

    public Player(Texture texture) {
        super(texture);

        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius((this.sprite.getWidth()/2f)/Global.PPM); //The fixture for the player is a circle with radius spriteWidth/2
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body =  this.create(fdef);

        this.sprite.setSize(this.sprite.getWidth()/Global.PPM,this.sprite.getHeight()/Global.PPM);
        //sprite.setSize(SHIP_SIZE,SHIP_SIZE*(sprite.getHeight()/sprite.getWidth()); //ALSO GET SHIP RESOLUTION IN CASE THE SPRITE IS NOT A SQUARE
        this.sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f); //allows sprite to rotate around center
        //this.sprite.setPosition(Global.CAM_SIZE_X/2f-sprite.getWidth()/2f,Global.CAM_SIZE_Y/2f-sprite.getHeight()/2f); //set sprite as starting in center of screen
    }

    public void handleInput() {
        //Accelerate / deccelerate
        /*  NOTE: There may be no need for acceleration / decceleration, instead possibly use impulses and linear damping
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        }
        */

        //Rotate ship using mouse
        //Find out how far mouse is from center of screen
        float mx = Gdx.input.getX()-Global.SCREEN_WIDTH/2f;
        float my = Global.SCREEN_HEIGHT/2f-Gdx.input.getY();
        float angle = MathUtils.atan2(my,mx); //calculate degree of mouse relative to center of screen
        //THIS BLOCK OF CODE IS FROM https://gamedev.stackexchange.com/questions/108795/libgdx-rotatetoaction-does-not-directly-rotate-between-179-and-179
        float mouseAngle = angle;
        float shipAngle = (this.body.getAngle()+MathUtils.PI2)%MathUtils.PI2;
        if (shipAngle - mouseAngle > MathUtils.PI) mouseAngle += MathUtils.PI2;
        if (mouseAngle - shipAngle > MathUtils.PI) shipAngle += MathUtils.PI2;
        float rotate = (shipAngle+(mouseAngle-shipAngle)*0.07f)%MathUtils.PI2; //the amount the ship rotates

        //The amount the ship moves
        float shiftX = this.ship_speed*MathUtils.cos(this.body.getAngle())/Global.PPM;
        float shiftY = this.ship_speed*MathUtils.sin(this.body.getAngle())/Global.PPM;

        //Move and rotate player
        this.body.setTransform(this.body.getPosition().x+shiftX,this.body.getPosition().y+shiftY,rotate); //the 0.07f is the turnSpeed
        //this.body.setTransform(this.ship_speed);

        this.update(); //sync texture with body
    }

    @Override
    public void destroy() {
        //game over or respawn
    }

    //Getters
    public float getHp() { return this.hp; }

    //Setters
}