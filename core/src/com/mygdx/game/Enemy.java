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
// The higher your level, the more enemies spawn, and the harder they are (up to a certain level)

package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import java.util.ArrayList;

public class Enemy extends Entity {

    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    private float max_hp;
    private int dmg;
    private int shoot_frq; //its weird, but the higher the number, the low the shoot rate
    String ai_type; //constant that determines the enmies ai (can be found in AssetLoader)
    private float turn_speed;
    private int contact_dmg;
    private String fire_pattern;
    private String bullet;
    private int xp; //amount of xp rewarded for killing this enemy

    private float hp;
    private float theta;

    public Enemy(Texture texture,float max_hp,int dmg,int shoot_frq,String ai_type,float speed,float turn_speed,int contact_dmg,String fire_pattern,String bullet,int xp) {
        super(texture,speed);
        this.max_hp = max_hp;
        this.dmg = dmg;
        this.shoot_frq = shoot_frq;
        this.ai_type = ai_type;
        this.turn_speed = turn_speed;
        this.contact_dmg = contact_dmg;
        this.fire_pattern = fire_pattern;
        this.bullet = bullet;
        this.xp = xp;

        this.hp = this.max_hp;

        //Create body for enemy - it is assumed that enemy has a circular fixture
        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth()/2f); //create a circular fixture that encompasses the enemies sprite
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        this.body = this.create(fdef,BodyDef.BodyType.DynamicBody);

        this.body.setUserData(this); //set identifier for enemy
        circle.dispose();
    }

    //Enemy Creation
    public static void place_enemy(Vector2 pos,String enemyType) { //spawns an enemy between d and 2d from player
        Enemy e = AssetLoader.create_enemy(enemyType); //create enemy body from AssetLoader prefab
        e.init(pos.x,pos.y,0f); //place enemy in world
        enemies.add(e); //add enemy to active list
    }

    //AI stuffs
    public void move(Player player) {
        //determine enemy ai
        if (this.ai_type.equals(AssetLoader.ai_circle)) {
            this.move_circle(player);
        } else if (this.ai_type.equals(AssetLoader.ai_kamikazi)) {
            this.move_target(player);
        } else if (this.ai_type.equals(AssetLoader.ai_rammer)) {
            this.move_target(player);
        }
    }

    public void move_circle(Player player) { //enemy flies in direction of player, until it reaches a certain distance from player, then it will circle
        //TODO: enemies should have a variable called distFromPlayer, which is the distance away from the player they like to stay at
        //Done
        //get angle ship needs to travel in
        float targetAngle = MathUtils.atan2(player.body.getPosition().y-this.body.getPosition().y,player.body.getPosition().x-this.body.getPosition().x);
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

            this.shoot_at_player(targetAngle); //shoot at player
        }
    }
    public void move_drift(Player player) { //used for asteroids
        float targetAngle = MathUtils.atan2(player.body.getPosition().y-this.body.getPosition().y,player.body.getPosition().x-this.body.getPosition().x);
        float absang = (float)Math.toRadians(Global.rand.nextInt(50+1));
        boolean isPositive = Global.rand.nextBoolean();
        if(isPositive){this.theta = absang;}else{this.theta = absang*-1;}
        this.body.setLinearVelocity(this.speed*MathUtils.cos(targetAngle+this.theta)/Global.PPM,this.speed*MathUtils.sin(targetAngle+this.theta)/Global.PPM);
    }
    public void move_target(Player player) {
        //get angle of player relative to player
        float targetAngle = MathUtils.atan2(player.body.getPosition().y-this.body.getPosition().y,player.body.getPosition().x-this.body.getPosition().x);
        this.body.setLinearVelocity(this.speed*MathUtils.cos(targetAngle+this.theta)/Global.PPM,this.speed*MathUtils.sin(targetAngle+this.theta)/Global.PPM);
    }

    //Attacking AI - pass in player's angle relative to enemy
    public void shoot_at_player(float targetAngle) {//Give chance for enemy to shoot at player (if they are pointed at player of course)
        if (Math.abs(this.getRotation()-targetAngle)<10) { //if enemy is pointed with 10 degrees of player, shoot
            boolean shoot = Global.rand.nextInt(shoot_frq) == 0 ? true : false; //shoot delay
            if (shoot) {
                Projectile.shoot(this.dmg,this.bullet,this.fire_pattern,Projectile.tag_enemy, this.getX(), this.getY(), this.getRotation());
            }
        }
    }

    public static void drawAll(Batch batch) { //NOTE: possibly merge with updateAll
        for (Enemy e : Enemy.enemies) {
            e.sprite.draw(batch); //simply draws all enemies in active list
        }
    }

    public static void updateAll(Player player) {
        for(int i = Enemy.enemies.size()-1;i>=0;i--){ //iterate throguh enemies backwards to avoid list remobal issues
            Enemy e = Enemy.enemies.get(i);
            e.move(player); //perform ai
            e.update(); //sync sprite with fixture
            if(e.getHP()<=0 || e.getDistFromPlayer(player)>800f/Global.PPM) {
                if(!Global.isDead) {
                    player.addXp(e.xp); //give player xp
                }
                //Give chance to spawn hp box where enemy dies

                int spawnHpBox = Global.rand.nextInt(Global.hpBoxChance);
                if (spawnHpBox == 0) {
                    player.modHp(20); //restore 20 hp
                }
                e.dispose();
            }
            if(i==0 && Global.isDead){Global.isDead = false;} //figure out if all enemies in world are dead
        }
    }

    public void dispose() { //safely deletes self
        AssetLoader.flagForPurge(this.body);
        Enemy.enemies.remove(this); //remvoe enemy from existance
    }

    //Getters
    public float getHP() { return this.hp; }
    public float getDmg() { return this.dmg; }
    public int getContactDmg() { return this.contact_dmg; }
    public double getDistFromPlayer(Player player){ return Global.getDist(player.getX(),player.getY(),this.getX(),this.getY()); }

    //Setters
    public void modHp(float deltaHp) {
        this.hp += deltaHp;
        this.hp = MathUtils.clamp(this.hp,0,this.max_hp); //clamp hp
    }

}
