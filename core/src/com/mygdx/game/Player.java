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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Entity {
    private static float max_hp;
    private static float shoot_frq;
    private float turn_speed;
    private int contact_dmg;
    private String fire_pattern;
    private String bullet;

    private float hp;
    private int xp;
    private int lvl;

    private Vector2 force;

    public Player(Texture texture,float max_hp,int shoot_frq,float speed,float turn_speed,int contact_dmg,String fire_pattern,String bullet) {
        super(texture,speed);
        this.max_hp = max_hp;
        this.shoot_frq = shoot_frq;
        this.turn_speed = turn_speed;
        this.contact_dmg = contact_dmg;
        this.fire_pattern = fire_pattern;
        this.bullet = bullet;

        this.hp = this.max_hp;
        this.xp = 0;
        this.lvl = 0;

        //Create body for player - it is assumed that player has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth()/2f); //The fixture for the player is a circle with radius spriteWidth/2
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body = this.create(fdef,BodyDef.BodyType.DynamicBody);
        this.body.createFixture(fdef);

        this.body.setUserData(this);
    }

    public void handleInput() {
        //Accelerate / deccelerate
        //  NOTE: There may be no need for acceleration / decceleration, instead possibly use impulses and linear damping

        //Rotate ship using mouse
        this.rotate(Global.angle,0.055f);
        //Move player depending on the direction its facing
        float vx = this.speed*MathUtils.cos(this.body.getAngle());
        float vy = this.speed*MathUtils.sin(this.body.getAngle());

        this.body.setLinearVelocity(vx/Global.PPM,vy/Global.PPM);
        this.update(); //sync texture with body

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) { //spawn projectile
            //Create new projectile object
            Projectile.shoot(this.bullet,this.fire_pattern,Projectile.tag_player,this.getX(),this.getY(),this.getRotation());
        }
    }

    //Getters
    public int getXp() { return this.xp; }
    public int getLvl() {return this.lvl; }
    public float getHp() { return this.hp; }

    //Setters
    public void addXp(float xpAmount) { //handles leveling up
        int lvlupReq = 1000; //amount of xp required to level up (its fixed for now, but make it so that the higher level you are, the harder it is to lvl up)

        if (this.xp+xpAmount >= lvlupReq) { //if the player levels up
            //hp goes back to full
            this.hp = this.max_hp;
            //TODO: level caps at 45 or sm
            this.lvl += 1;
            this.xp = (int)(this.xp+xpAmount)%1000; //additional xp carries over
        } else { //otherwise level up like normal
            this.xp += xpAmount;
        }
    }
    public void modHp(float deltaHp) {
        this.hp += deltaHp;
        MathUtils.clamp(this.hp,0,this.max_hp);
    }
}