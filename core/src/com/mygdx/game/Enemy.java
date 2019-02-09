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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {

    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    private float max_hp;
    private int shoot_frq; //its weird, but the higher the number, the low the shoot rate
    String ai_type;
    private float turn_speed;
    private int contact_dmg;
    private String fire_pattern;
    private String bullet;

    private float hp;
    private float theta;
    private Random rand = new Random();

    public Enemy(Texture texture,float max_hp,int shoot_frq,String ai_type,float speed,float turn_speed,int contact_dmg,String fire_pattern,String bullet) {
        super(texture,speed);
        this.max_hp = max_hp;
        this.shoot_frq = shoot_frq;
        this.ai_type = ai_type;
        this.turn_speed = turn_speed;
        this.contact_dmg = contact_dmg;
        this.fire_pattern = fire_pattern;
        this.bullet = bullet;

        this.hp = this.max_hp;

        //Create body for enemy - it is assumed that enemy has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth()/2f);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body = this.create(fdef,BodyDef.BodyType.DynamicBody);

        this.body.setUserData(this);
    }

    //Enemy Creation
    public static void place_enemy(Vector2 pos) { //spawns an enemy between d and 2d from player
        Enemy e = AssetLoader.create_enemy("grunt");

        e.init(pos.x,pos.y,0f);
        enemies.add(e);
    }

    //AI stuffs
    public void move(Player player) {
        if (this.ai_type.equals(AssetLoader.ai_circle)) {
            this.move_circle(player);
        }
    }

    public void move_circle(Player player) { //enemy flies in direction of player, until it reaches a certain distance from player, then it will circle
        //TODO: enemies should have a variable called distFromPlayer, which is the distance away from the player they like to stay at
        //get angle ship needs to travel in
        float targetAngle = MathUtils.atan2(player.body.getPosition().y-this.body.getPosition().y,player.body.getPosition().x-this.body.getPosition().x);
        //System.out.println(true);
        //Update enemy
        this.rotate(targetAngle,this.turn_speed); //enemy tries to face player
        if(Global.getDist(player.getX(),player.getY(),this.getX(),this.getY())>(250/Global.PPM)){
            this.body.setLinearVelocity(this.speed*MathUtils.cos(targetAngle)/Global.PPM,this.speed*MathUtils.sin(targetAngle)/Global.PPM); //apply force towards that direction
        }else{
            this.body.getPosition().x = player.getX()+(float)Math.cos(Math.toRadians(this.theta))*(250/Global.PPM);
            this.body.getPosition().y = player.getY()+(float)Math.sin(Math.toRadians(this.theta))*(250/Global.PPM);
            this.theta++;
            if (this.theta > 360) {
                this.theta = 0;
            }

            this.shoot_at_player(targetAngle);
        }
    }
    public void move_drift(Player player) { //used for asteroids
        float targetAngle = MathUtils.atan2(player.body.getPosition().y-this.body.getPosition().y,player.body.getPosition().x-this.body.getPosition().x);
        float absang = (float)Math.toRadians(rand.nextInt(50+1));
        boolean isPositive = rand.nextBoolean();
        if(isPositive){this.theta = absang;}else{this.theta = absang*-1;}
        this.body.setLinearVelocity(this.speed*MathUtils.cos(targetAngle+this.theta)/Global.PPM,this.speed*MathUtils.sin(targetAngle+this.theta)/Global.PPM);
    }

    //Attacking AI - pass in player's angle relative to enemy
    public void shoot_at_player(float targetAngle) {//Give chance for enemy to shoot at player (if they are pointed at player of course)
        if (Math.abs(this.getRotation()-targetAngle)<10) { //if enemy is pointed with 10 degrees of player, shoot
            boolean shoot = Global.rand.nextInt(shoot_frq) == 0 ? true : false;
            if (shoot) {
                Projectile.shoot(this.bullet,this.fire_pattern,Projectile.tag_enemy, this.getX(), this.getY(), this.getRotation());
            }
        }
    }

    public static void drawAll(Batch batch) { //NOTE: possibly merge with updateAll
        for (Enemy e : Enemy.enemies) {
            e.sprite.draw(batch);
        }
    }

    public static void updateAll(Player player) {
        for(int i = Enemy.enemies.size()-1;i>=0;i--){
            Enemy e = Enemy.enemies.get(i);
            e.move(player);
            e.update();
            if(e.getHP()<=0 || e.getDistFromPlayer(player)>1500) {
                e.dispose();
                Global.currScore+=10;
            }
        }
    }

    public void dispose() { //safely deletes self
        AssetLoader.flagForPurge(this.body);
        Enemy.enemies.remove(this);
    }

    //Getters
    public float getHP(){
        return this.hp;
    }

    public double getDistFromPlayer(Player player){
        return Global.getDist(player.getX(),player.getY(),this.getX(),this.getY());
    }

    public void modHp(float deltaHp) {
        this.hp += deltaHp;
        MathUtils.clamp(this.hp,0,this.max_hp);
    }

}
