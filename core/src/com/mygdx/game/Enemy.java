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

    static int SHIP_SIZE=16;
    private int hp;
    public Enemy(Texture texture,float speed,int difficulty) {
        super(texture,speed);
        this.hp = difficulty;
        /*

        TODO: add difficulty scaling using difficulty arguement!

        */
        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth()/2f);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body = this.create(fdef, BodyDef.BodyType.DynamicBody);

        this.body.setUserData(CollisionListener.enemy_id);
    }

    //Enemy Creation
    public static void place_enemy(Player player,float maxDist,int difficulty) { //spawns an enemy between d and 2d from player
        float speed = 0.5f*difficulty;
        Texture ship = new Texture(""+difficulty+".png");
        new Enemy(ship,speed,difficulty);
    }   //might not be necessary
        //this method bleeds over in map

    //Move
    public void move_circle(Player player) { //enemy flies in direction of player, until it reaches a certain distance from player, then it will circle
        //TODO: enemies should have a variable called distFromPlayer, which is the distance away from the player they like to stay at
        //TODO: Possibly make enemy rotation not instantenous
        //get angle ship needs to travel in
        float targetAngle = MathUtils.atan2(player.body.getPosition().y-this.body.getPosition().y,player.body.getPosition().x-this.body.getPosition().x);

        //Update enemy
        this.rotate(targetAngle,0.055f); //enemy tries to face player
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
