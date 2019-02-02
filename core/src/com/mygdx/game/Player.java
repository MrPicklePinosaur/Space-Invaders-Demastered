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

    public Player(Texture texture) {
        super(texture);

        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius((this.sprite.getWidth()/2f)/Global.PPM); //The fixture for the player is a circle with radius spriteWidth/2
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body =  this.create(fdef);
    }

    public void handleInput() {
        //Accelerate / deccelerate
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            //Accelerate
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            //Deccelerate
        }

        /*
        //Rotate ship using mouse
        //Find out how far mouse is from center of screen
        float shiftX = Gdx.input.getX()-Global.SCREEN_WIDTH/2f;
        float shiftY = Global.SCREEN_HEIGHT/2f-Gdx.input.getY();
        float angle = MathUtils.atan2(shiftY,shiftX); //calculate degree of mouse relative to center of screen
        //THIS BLOCK OF CODE IS FROM https://gamedev.stackexchange.com/questions/108795/libgdx-rotatetoaction-does-not-directly-rotate-between-179-and-179
        float mouseAngle = angle*MathUtils.radiansToDegrees-90;
        float shipAngle = (body.getRotation()+360)%360;
        if (shipAngle - mouseAngle > 180) mouseAngle += 360;
        if (mouseAngle - shipAngle > 180) shipAngle += 360;
        sprite.setRotation((shipAngle+(mouseAngle-shipAngle)*0.07f)%360);
        */

        this.update(); //sync
    }

    @Override
    public void destroy() {
        //game over or respawn
    }

    //Getters
    public float getHp() { return this.hp; }

    //Setters
}