//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 ______     __   __     ______     __    __     __  __
/\  ___\   /\ "-.\ \   /\  ___\   /\ "-./  \   /\ \_\ \
\ \  __\   \ \ \-.  \  \ \  __\   \ \ \-./\ \  \ \____ \
 \ \_____\  \ \_\\"\_\  \ \_____\  \ \_\ \ \_\  \/\_____\
  \/_____/   \/_/ \/_/   \/_____/   \/_/  \/_/   \/_____/
 */
//Subclass of Entity
// Handles all enemy shaninigans

package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import java.util.ArrayList;

public class Enemy extends Entity {

    private ArrayList<Enemy> active_enemy = new ArrayList<Enemy>();

    public Enemy(Texture texture,float speed) {
        super(texture,speed);
        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius((this.sprite.getWidth()/2f)/Global.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body = this.create(fdef, BodyDef.BodyType.DynamicBody);

        this.sprite.setSize(this.sprite.getWidth()/Global.PPM,this.sprite.getHeight()/Global.PPM);
        this.sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f); //allows sprite to rotate around center
        //this.body.setTransform(2f,2f,0);
    }

    //Enemy Creation
    public void place_enemy(Player player,float distFromPlayer) { //spawns an enemy between d and 2d from player

    }

    //Move
    public void move_circle(Player player) { //enemy flies in direction of player, until it reaches a certain distance from player, then it will circle
        //TODO: enemies should have a variable called distFromPlayer, which is the distance away from the player they like to stay at
        //get angle ship needs to travel in
        float targetAngle = MathUtils.atan2(player.body.getPosition().y-this.body.getPosition().y,player.body.getPosition().x-this.body.getPosition().x);

        //Update enemy
        //this.body.setTransform(0,0,targetAngle); //enemy also faces player
        this.body.setLinearVelocity(this.speed*MathUtils.cos(targetAngle)/Global.PPM,this.speed*MathUtils.sin(targetAngle)/Global.PPM); //apply force towards that direction

    }
    public void move_drift() { //used for asteroids

    }

    public void move(Player player) {
        this.move_circle(player);
        this.update();
    }

    @Override
    public void destroy() {
        //Remove enemy from list or whatever here
    }
}
