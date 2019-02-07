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
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class Projectile extends Entity{

    private static ArrayList<Projectile> active_projectiles = new ArrayList<Projectile>();
    private static ArrayList<Projectile> dead_projectiles = new ArrayList<Projectile>();

    private float speed;
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
        body.setUserData("projectile"); //TODO: differentiate between player projeciltes and enemy projectiles (to prevent enemy friendly fire)
    }

    public void init() {

    }

    public void destroy() {

    }
    //There may be no need as physics step may move all the projectiles anyways
    public static void updateAll() { //updates all active projectiles

    }
}
