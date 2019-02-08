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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import jdk.nashorn.internal.ir.PropertyKey;

import java.util.ArrayList;

public class Projectile extends Entity{

    private static ArrayList<Projectile> active_projectiles = new ArrayList<Projectile>();
    private static ArrayList<Projectile> purge_projectiles = new ArrayList<Projectile>(); //projectiles that have despawned, ready to be disposed

    private Vector2 spawn_pos;
    private float max_dist = 2f; //max distance projectile can travel before despawning

    public Projectile(Texture texture,float speed) {
        super(texture,speed);
        //save spawn pos in constructor

        //Create body for projectile - it is assumed that all projectiles have a rectangular fixtures
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(this.sprite.getWidth(),this.sprite.getHeight());
        FixtureDef fdef = new FixtureDef();
        fdef.shape = rect;
        fdef.isSensor = true;
        this.body = this.create(fdef, BodyDef.BodyType.KinematicBody);
        body.setUserData(CollisionListener.player_projectile_id); //TODO: differentiate between player projeciltes and enemy projectiles (to prevent enemy friendly fire)
    }

    public static void updateAll() { //TODO: rewrite update all to loop in reverse
        for (int i = Projectile.active_projectiles.size()-1; i >= 0; i--) { //loops through enemies in reverse
            Projectile p = Projectile.active_projectiles.get(i);
            //update projectile
            p.update(); //sync sprite

            //check to see if projectile is to be deleted (either it hit something or it has reached its max range)
            if (Math.hypot(p.getX()-p.spawn_pos.x,p.getY()-p.spawn_pos.y) >= p.max_dist) { //if the projectile has travelled past its max range
                TrashCan.flagForPurge(p.getBody()); //add body to purge list
                Projectile.active_projectiles.remove(p);
            }
        }
    }

    //NOTEL i dont like drawing in class, possibly relocate (this code is also redundant)
    public static void drawAll(Batch batch) {
        for (Projectile p : Projectile.active_projectiles) {
            p.sprite.draw(batch);
        }
    }


    public void destroy() {
    }

    //Setters
    @Override
    public void init(float posX,float posY,float angle) {
        super.init(posX,posY,angle);
        this.spawn_pos = new Vector2(posX,posY); //save spawn location of projectile (to determine travel distance and such)

        //Place projectile in world
        this.body.setTransform(this.spawn_pos.x,this.spawn_pos.y,angle);
        this.body.setLinearVelocity(this.speed*MathUtils.cos(angle),this.speed*MathUtils.sin(angle));
        Projectile.active_projectiles.add(this); //add this projectile to active prokectile list
    }
}
