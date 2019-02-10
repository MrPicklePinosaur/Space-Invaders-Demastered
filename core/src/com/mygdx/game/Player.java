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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import oracle.jrockit.jfr.ActiveSettingEvent;

import java.util.HashMap;

public class Player extends Entity {
    //private static HashMap<String,String> levelTree = new HashMap<String,String>();

    private float max_hp;
    private int dmg;
    private int shoot_frq;
    private float turn_speed;
    private int contact_dmg;
    private String fire_pattern;
    private String bullet;

    private float hp;
    private int xp;
    private int lvl;
    private int reload_counter = 0;
    private boolean reload; //is the player currently reloading
    private boolean toggleAutoFire = false;

    //Keep track of the amount of points the player has put into each stat
    private int hp_points;
    private int dmg_points;
    private int spd_points;
    private int turn_points;
    private int reload_points;
    private int contact_points;

    private Vector2 force;

    public Player(Texture texture,float speed,String className) {
        super(texture,speed);
        AssetLoader.switchClasses(this,className);

        this.hp = this.max_hp;
        this.xp = 0;
        this.lvl = 4;

        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth()/2f); //The fixture for the player is a circle with radius spriteWidth/2
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body = this.create(fdef,BodyDef.BodyType.DynamicBody);
        this.body.createFixture(fdef);

        this.body.setUserData(this);
        circle.dispose();
    }

    public void handleInput() {
        //Accelerate / deccelerate
        //  NOTE: There may be no need for acceleration / decceleration, instead possibly use impulses and linear damping

        //Rotate ship using mouse
        this.rotate(Global.angle, 0.055f);
        //Move player depending on the direction its facing
        float vx = this.speed * MathUtils.cos(this.body.getAngle());
        float vy = this.speed * MathUtils.sin(this.body.getAngle());

        this.body.setLinearVelocity(vx / Global.PPM, vy / Global.PPM);
        this.update(); //sync texture with body

        if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT) || this.toggleAutoFire) && this.reload == false) { //spawn projectile
            //Create new projectile object
            Projectile.shoot(this.dmg, this.bullet, this.fire_pattern, Projectile.tag_player, this.getX(), this.getY(), this.getRotation());
            this.reload = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { //player uses ability
            this.useAbility();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) { //player toggles auto fire
            this.toggleAutoFire = this.toggleAutoFire == true ? false : true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) { //player toggles camera lock mode
            Global.r.toggleLockedCamera();
        }

        if (this.reload==true) { reload_counter++; }
        if (reload_counter >= this.shoot_frq) {
            this.reload_counter = 0;
            this.reload = false;
        }
    }

    public void useAbility() {
        //this.body.setTransform(this.getX()+Global.mx/Global.PPM,this.getY()+Global.my/Global.PPM,this.getRotation());

    }

    //Getters
    public int getXp() { return this.xp; }
    public int getLvl() {return this.lvl; }
    public float getHp() { return this.hp; }
    public float getDmg() { return this.dmg; }
    public int getContactDmg() { return this.contact_dmg; }

    //Setters
    public void modHp(float deltaHp) {
        this.hp += deltaHp;
        this.hp = MathUtils.clamp(this.hp,0,this.max_hp);
    }

    //Stuff for leveling up
    public void addXp(float xpAmount) { //handles leveling up
        int lvlupReq = this.lvl*100;  //amount of xp required to level up

        if (this.xp+xpAmount >= lvlupReq) { //if the player levels up
            //hp goes back to full
            this.hp = this.max_hp;
            //TODO: level caps at 45 or sm
            this.lvl += 1;
            this.xp = (int)(this.xp+xpAmount)%1000; //additional xp carries over
            //this.choosePoint(1,0,0,0,0,0);
            if (this.lvl >= 5) {
                AssetLoader.switchClasses(this,AssetLoader.class_rammer);
            }
            //let player choose a stat to level up


        } else { //otherwise gain xp like normal
            this.xp += xpAmount;
        }
    }
    public void choosePoint(int hp_points,int dmg_points,int spd_points,int turn_points,int reload_points,int contact_points) { //user chooses a stat to put points into
        this.hp_points+=hp_points*5;
        this.dmg_points+=dmg_points*2;
        this.spd_points+=spd_points*15;
        this.turn_points+=turn_points*0.005;
        this.reload_points-=reload_points*2;
        this.reload_points = MathUtils.clamp(this.reload_points,0,1000000000);
        this.contact_points+=contact_points*2;
    }
    public void setStats(String path,float max_hp,int dmg,int shoot_frq,float speed,float turn_speed,int contact_dmg,String fire_pattern,String bullet) {
        this.sprite = new Sprite(new Texture(path));
        this.sprite.setSize(this.sprite.getWidth()/Global.PPM,this.sprite.getHeight()/Global.PPM);
        this.sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f); //allows sprite to rotate around center
        this.max_hp = max_hp;
        this.dmg = dmg;
        this.shoot_frq = shoot_frq;
        this.speed = speed;
        this.turn_speed = turn_speed;
        this.contact_dmg = contact_dmg;
        this.fire_pattern = fire_pattern;
        this.bullet = bullet;
    }
    /*
    public static void initLvlTree() {
        levelTree.put("base","base");
        levelTree.put("sniper","base");
        levelTree.put("rammer","base");
        levelTree.put("gunner","base");
        levelTree.put("shotgunist","base");
    }
    */
    public void regen() {
        this.modHp(0.1f);
    }
    public void reset() {
        hp = this.max_hp;
        xp = 0;
        this.lvl = 1;
        reload_counter = 0;
        reload = false;
        toggleAutoFire = false;

        hp_points = 0;
        dmg_points = 0;
        spd_points = 0;
        turn_points = 0;
        reload_points = 0;
        contact_points = 0;
        AssetLoader.switchClasses(this,AssetLoader.class_base);
    }
}