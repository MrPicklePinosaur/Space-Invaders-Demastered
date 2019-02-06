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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import java.util.ArrayList;

public class Enemy extends Entity {

    private ArrayList<Enemy> active_enemy = new ArrayList<Enemy>();

    public Enemy(Texture texture,float max_speed) {
        super(texture,float max_speed);
        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius((this.sprite.getWidth()/2f)/Global.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body = this.create(fdef, BodyDef.BodyType.DynamicBody);

        this.sprite.setSize(this.sprite.getWidth()/Global.PPM,this.sprite.getHeight()/Global.PPM);
        this.sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f); //allows sprite to rotate around center
    }

    //Enemy Creation
    public void place_enemy(Player player,float distFromPlayer) { //spawns an enemy between d and 2d from player

    }

    //Move
    public void move_circle(Player player,float distFromPlayer) { //enemy flies in direction of player, until it reaches a certain distance from player, then it will circle
        float px = player.body.getPosition().x;
        float py = player.body.getPosition().y;
        float targetAngle =

    }
    public void move_drift() { //used for asteroids

    }

    public void move() {

        this.update();
    }

    @Override
    public void destroy() {
        //Remove enemy from list or whatever here
    }
}
