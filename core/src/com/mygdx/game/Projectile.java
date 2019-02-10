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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import oracle.jrockit.jfr.ActiveSettingEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Projectile extends Entity{

    private static ArrayList<Projectile> active_projectiles = new ArrayList<Projectile>();
    private static ArrayList<Projectile> purge_projectiles = new ArrayList<Projectile>();
    public static final int tag_player = 0;
    public static final int tag_enemy = 1;

    private int dmg;
    private float max_dist; //max distance projectile can travel before despawning

    private Vector2 spawn_pos;
    private int tag; //determines who shot the projectile

    public Projectile(Texture texture,int dmg,float speed,float max_dist,int tag) {
        super(texture,speed);
        this.dmg = dmg;
        this.max_dist = max_dist;

        this.tag = (tag == tag_player) ?  tag_player : tag_enemy; //determine who shot the projecilte

        //Create body for projectile - it is assumed that all projectiles have a rectangular fixtures
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(this.sprite.getHeight(),this.sprite.getWidth());
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;
        fdef.shape = rect;
        this.body = this.create(fdef,BodyDef.BodyType.KinematicBody);
        this.body.createFixture(fdef); //DONT FORGET TO DISPOSE OF fdef
        body.setUserData(this); //TODO: differentiate between player projeciltes and enemy projectiles (to prevent enemy friendly fire)
    }

    public static void updateAll() { //TODO: rewrite update all to loop in reverse
        for (int i = Projectile.active_projectiles.size()-1; i >= 0; i--) { //loops through enemies in reverse
            Projectile p = Projectile.active_projectiles.get(i);
            //update projectile
            p.update(); //sync sprite

            //check to see if projectile is to be deleted (either it hit something or it has reached its max range)
            if (Math.hypot(p.getX()-p.spawn_pos.x,p.getY()-p.spawn_pos.y) >= p.max_dist) { //if the projectile has travelled past its max range
                p.dispose();
            }
        }
    }

    //NOTEL i dont like drawing in class, possibly relocate (this code is also redundant)
    public static void drawAll(Batch batch) {
        for (Projectile p : Projectile.active_projectiles) {
            p.sprite.draw(batch);
        }
    }

    //FIRE PATTERNS:  spawns projectiles based on the current 'weapon' selected
    public static void shoot(int dmg,String bullet,String fire_pattern,int tag,float x,float y,float angle) {
        ArrayList<Vector3> spawnList = new ArrayList<Vector3>();

        //Determine which firepattern player/enemy has
        if (fire_pattern.equals(AssetLoader.fire_cannon)) {
            Projectile.shoot_cannon(spawnList, x, y, angle);
        } else if (fire_pattern.equals(AssetLoader.fire_twin)) {
            Projectile.shoot_twin(spawnList, x, y, angle);
        } else if (fire_pattern.equals(AssetLoader.fire_shotgun)) {
            Projectile.shoot_shotgun(spawnList, x, y, angle);
        } else if (fire_pattern.equals(AssetLoader.fire_circle)) {
            Projectile.shoot_circle(spawnList, x, y);
        }

        for (Vector3 p_data : spawnList) { //for each projecctile to be spawned
            Projectile p = AssetLoader.create_projectile(bullet,dmg,tag);
            p.init(p_data.x,p_data.y,p_data.z);
        }
    }

    public static void shoot_cannon(ArrayList<Vector3> spawnList,float x, float y, float angle) { //shoots a basic bulley in direction enetiy is facing
        spawnList.add(new Vector3(x,y,angle));
    }
    public static void shoot_twin(ArrayList<Vector3> spawnList,float x, float y, float angle) { //shoots two bullets, 10 degrees apart
        spawnList.add(new Vector3(x,y,angle+5*MathUtils.degreesToRadians));
        spawnList.add(new Vector3(x,y,angle-5*MathUtils.degreesToRadians));
    }
    public static void shoot_shotgun(ArrayList<Vector3> spawnList,float x, float y, float angle) {
        for (int i = -2; i < 3; i++) {
            spawnList.add(new Vector3(x,y,angle+Global.rand.nextInt(20)*i*MathUtils.degreesToRadians));
        }
    }
    public static void shoot_circle(ArrayList<Vector3> spawnList,float x, float y) {
        for (int i = 0; i < 12; i++) {
            spawnList.add(new Vector3(x,y,(30*i)*MathUtils.degreesToRadians));
        }
    }

    public void dispose() {
        AssetLoader.flagForPurge(this.getBody()); //add body to purge list
        Projectile.active_projectiles.remove(this);
    }

    //Getters
    public int getTag() { return this.tag; }
    public float getDMG(){return this.dmg;}

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
