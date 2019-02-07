/*
 ______   ______     ______       __     ______     ______     ______   __     __         ______
/\  == \ /\  == \   /\  __ \     /\ \   /\  ___\   /\  ___\   /\__  _\ /\ \   /\ \       /\  ___\
\ \  _-/ \ \  __<   \ \ \/\ \   _\_\ \  \ \  __\   \ \ \____  \/_/\ \/ \ \ \  \ \ \____  \ \  __\
 \ \_\    \ \_\ \_\  \ \_____\ /\_____\  \ \_____\  \ \_____\    \ \_\  \ \_\  \ \_____\  \ \_____\
  \/_/     \/_/ /_/   \/_____/ \/_____/   \/_____/   \/_____/     \/_/   \/_/   \/_____/   \/_____/
 */
//   ALL projectiles are kinematic bodies, have rectangular fixtures and have constant velocities

package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class Projectile extends Entity{

    private static ArrayList<Projectile> active_projectiles = new ArrayList<Projectile>();
    private static ArrayList<Projectile> dead_projectiles = new ArrayList<Projectile>();

    private float angle;

    public Projectile(Texture texture,float speed,float angle) {
        super(texture,speed);
        this.speed = speed;
        this.angle = angle;

        //Create body for projectile - it is assumed that all projectiles have a rectangular fixtures
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(this.sprite.getWidth()/Global.PPM,this.sprite.getHeight()/Global.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = rect;
        fdef.isSensor = true;
        this.body = this.create(fdef, BodyDef.BodyType.KinematicBody);
        body.setUserData(CollisionListener.player_projectile_id); //TODO: differentiate between player projeciltes and enemy projectiles (to prevent enemy friendly fire)
    }

    public void init(float x, float y) { //spawn the projectile in the world given starting x and y; given in PPM
        this.body.setTransform(x,y,this.angle);
        this.body.setLinearVelocity(this.speed*MathUtils.cos(this.angle),this.speed*MathUtils.sin(this.angle));
        this.update();
        //TODO: add current projectile to active_list
    }
    public void init(Vector2 start_pos) { //overloaded
        this.init(start_pos.x,start_pos.y);
    }

    //There may be no need as physics step may move all the projectiles anyways
    public static void updateAll() { //updates all active projectiles

    }

    public void destroy() {

    }
}
