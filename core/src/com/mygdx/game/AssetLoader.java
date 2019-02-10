/*
 ______     ______     ______     ______     ______      __         ______     ______     _____     ______     ______
/\  __ \   /\  ___\   /\  ___\   /\  ___\   /\__  _\    /\ \       /\  __ \   /\  __ \   /\  __-.  /\  ___\   /\  == \
\ \  __ \  \ \___  \  \ \___  \  \ \  __\   \/_/\ \/    \ \ \____  \ \ \/\ \  \ \  __ \  \ \ \/\ \ \ \  __\   \ \  __<
 \ \_\ \_\  \/\_____\  \/\_____\  \ \_____\    \ \_\     \ \_____\  \ \_____\  \ \_\ \_\  \ \____-  \ \_____\  \ \_\ \_\
  \/_/\/_/   \/_____/   \/_____/   \/_____/     \/_/      \/_____/   \/_____/   \/_/\/_/   \/____/   \/_____/   \/_/ /_/
 */

package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.ArrayList;

public class AssetLoader {

    public AssetManager assetManager;
    private static ArrayList<Body> purge_body = new ArrayList<Body>();

    //PLAYER CLASSES
    public static final String class_base = "base";
    public static final String class_sniper = "sniper";
    public static final String class_rammer = "rammer";
    public static final String class_gunner = "gunner";
    public static final String class_shotgunist = "shotgunist";

    //ENEMY CONSTANTS
    //enemy builds
    public static final String enemy_grunt = "grunt";
    public static final String enemy_twingunner = "twingunner";
    public static final String enemy_bomber = "bomber";
    //enemy ai types
    public static final String ai_circle = "circle";
    public static final String ai_kamikazi = "kamikazi";

    //PROJECTILE CONSTATNS
    //Bullet types
    public static final String projectile_blueRay = "blueRay";
    public static final String projectile_yellowRay = "yellowRay";
    public static final String projectile_longYellowRay = "longYellowRay";

    //Fire types / fire patterns
    public static final String fire_cannon = "cannon";
    public static final String fire_twin = "twin";
    public static final String fire_shotgun = "shotgun";

    //SPAWN-POOL CONSTANTS
    public static final String[] difficulty_one = {enemy_grunt,enemy_twingunner};
    public static final String[] difficulty_two = {enemy_grunt};
    public static final String[] difficulty_three = {enemy_grunt};
    public static final String[] difficulty_four = {enemy_grunt};

    public AssetLoader() {
        this.assetManager = new AssetManager();
    }

    //HARDCODED GARBAGE HELL (because stinking json wont work)
    public static Enemy create_enemy(String enemyName) {
        //Default stuffs
        String path = "missing_texture.png";
        float max_hp = 100;
        int dmg = 10;
        float speed = 100;
        float turn_speed = 0.055f;
        int shoot_frq = 100;
        String ai_type = AssetLoader.ai_circle;
        int contact_dmg = 3;
        String fire_pattern = AssetLoader.fire_cannon;
        String bullet = AssetLoader.projectile_blueRay;

        if (enemyName.equals(AssetLoader.enemy_grunt)) { //the most basic enemy
            path = "1.png";
            max_hp = 50;
            dmg = 10;
            speed = 75;
            turn_speed = 0.055f;
            shoot_frq = 200;
            ai_type = AssetLoader.ai_circle;
            contact_dmg = 3;
            fire_pattern = AssetLoader.fire_cannon;
            bullet = AssetLoader.projectile_blueRay;
        } else if (enemyName.equals(AssetLoader.enemy_twingunner)) { //a bit stronger than the normal enemy
            path = "4.png";
            max_hp = 100;
            dmg = 10;
            speed = 60;
            turn_speed = 0.050f;
            shoot_frq = 150;
            ai_type = AssetLoader.ai_circle;
            contact_dmg = 5;
            fire_pattern = AssetLoader.fire_twin;
            bullet = AssetLoader.projectile_blueRay;
        } else if (enemyName.equals(AssetLoader.enemy_bomber)) { //suicide bomber
            path = "3.png";
            max_hp = 50;
            dmg = 0;
            speed = 100;
            turn_speed = 0.065f;
            shoot_frq = 10000000;
            ai_type = AssetLoader.ai_kamikazi;
            contact_dmg = 50;
            fire_pattern = AssetLoader.fire_cannon;
            bullet = AssetLoader.projectile_blueRay;
        }


        return new Enemy(new Texture(path),max_hp,dmg,shoot_frq,ai_type,speed,turn_speed,contact_dmg,fire_pattern,bullet);
    }

    public static Projectile create_projectile(String projectileName,int dmg,int spawn_tag) {
        //Defaults
        String path = "missing_texture.png";
        int p_dmg = dmg;
        float speed = 10f;
        float max_dist = 2f;
        int tag = spawn_tag;

        if (projectileName.equals(AssetLoader.projectile_blueRay)) {
            path = "player_bullet.png";
            speed = 10f;
            max_dist = 1.5f;
        } else if (projectileName.equals(AssetLoader.projectile_yellowRay)) {
            path = "yellow_shot.png";
            speed = 10f;
            max_dist = 1f;
        }
        else if (projectileName.equals(AssetLoader.projectile_longYellowRay)) {
            path = "long_yellow_shot.png";
            speed = 15f;
            max_dist = 2.5f;
        }

        return new Projectile(new Texture(path),p_dmg,speed,max_dist,tag);
    }

    public static Player create_player(String className) {
        String path = "2.png";
        float speed = 120;
        return new Player(new Texture(path),speed,className);
    }

    public static void switchClasses(Player player,String className) {
        String path = "1.png";
        float max_hp = 100;
        int dmg = 20;
        float speed = 120;
        float turn_speed = 0.05f;
        int shoot_frq = 100;
        int contact_dmg = 10;
        String fire_pattern = AssetLoader.fire_cannon;
        String bullet = AssetLoader.projectile_blueRay;

        if (className.equals(AssetLoader.class_base)) { }
        else if (className.equals(AssetLoader.class_sniper)) {
            path = "2.png";
            max_hp = 85;
            dmg = 40;
            speed = 140;
            turn_speed = 0.07f;
            shoot_frq = 150;
            contact_dmg = 5;
            fire_pattern = AssetLoader.fire_cannon;
            bullet = AssetLoader.projectile_longYellowRay;
        } else if (className.equals(AssetLoader.class_rammer)) {
            path = "3.png";
            max_hp = 150;
            dmg = 0;
            speed = 150;
            turn_speed = 0.06f;
            shoot_frq = 10000000;
            contact_dmg = 25;
            fire_pattern = AssetLoader.fire_cannon;
            bullet = AssetLoader.projectile_blueRay;
        } else if (className.equals(AssetLoader.class_gunner)) {
            path = "4.png";
            max_hp = 130;
            dmg = 12;
            speed = 120;
            turn_speed = 0.055f;
            shoot_frq = 100;
            contact_dmg = 10;
            fire_pattern = AssetLoader.fire_twin;
            bullet = AssetLoader.projectile_blueRay;
        } else if (className.equals(AssetLoader.class_shotgunist)) {
            path = "4.png";
            max_hp = 140;
            dmg = 7;
            speed = 140;
            turn_speed = 0.06f;
            shoot_frq = 120;
            contact_dmg = 10;
            fire_pattern = AssetLoader.fire_shotgun;
            bullet = AssetLoader.projectile_yellowRay;
        }

        player.setStats(path,max_hp,dmg,shoot_frq,speed,turn_speed,contact_dmg,fire_pattern,bullet);
    }

    //MAnage bodies
    public static void sweepBodies() { //removes all bodies safely
        for (Body b : AssetLoader.purge_body) {
            if (b != null) {
                Global.world.destroyBody(b);
                b.setUserData(null);
                b = null;
            }
        }
        AssetLoader.purge_body.clear(); //MAY NOT BE SAFE
    }

    public static void flagForPurge(Body body) { AssetLoader.purge_body.add(body); }
}
