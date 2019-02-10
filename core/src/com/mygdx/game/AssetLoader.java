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
    public static final String player_basic = "basic";

    //ENEMY CONSTANTS
    //enemy builds
    public static final String enemy_grunt = "grunt";
    public static final String enemy_twingunner = "twingunner";
    //enemy ai types
    public static final String ai_circle = "circle";

    //PROJECTILE CONSTATNS
    //Bullet types
    public static final String projectile_blueRay = "blueRay";
    public static final String projectile_yellowRay = "yellowRay";

    //Fire types / fire patterns
    public static final String fire_cannon = "cannon";
    public static final String fire_twin = "twin";

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
        float speed = 100;
        float turn_speed = 0.055f;
        int shoot_frq = 100;
        String ai_type = AssetLoader.ai_circle;
        int contact_dmg = 10;
        String fire_pattern = AssetLoader.fire_cannon;
        String bullet = AssetLoader.projectile_blueRay;

        if (enemyName.equals(AssetLoader.enemy_grunt)) { //the most basic enemy
            path = "1.png";
            max_hp = 50;
            speed = 75;
            turn_speed = 0.055f;
            shoot_frq = 200;
            ai_type = AssetLoader.ai_circle;
            contact_dmg = 10;
            fire_pattern = AssetLoader.fire_cannon;
            bullet = AssetLoader.projectile_blueRay;

        } if (enemyName.equals(AssetLoader.enemy_twingunner)) { //the most basic enemy
            path = "3.png";
            max_hp = 100;
            speed = 60;
            turn_speed = 0.050f;
            shoot_frq = 150;
            ai_type = AssetLoader.ai_circle;
            contact_dmg = 10;
            fire_pattern = AssetLoader.fire_twin;
            bullet = AssetLoader.projectile_yellowRay;
        }

        return new Enemy(new Texture(path),max_hp,shoot_frq,ai_type,speed,turn_speed,contact_dmg,fire_pattern,bullet);
    }

    public static Projectile create_projectile(String projectileName,int spawn_tag) {
        //Defaults
        String path = "missing_texture.png";
        int dmg = 20;
        float speed = 10f;
        float max_dist = 2f;
        int tag = spawn_tag;

        if (projectileName.equals(AssetLoader.projectile_blueRay)) {
            path = "player_bullet.png";
            dmg = 15;
            speed = 10f;
            max_dist = 2f;
        }
        if (projectileName.equals(AssetLoader.projectile_yellowRay)) {
            path = "yellow_shot.png";
            dmg = 20;
            speed = 10f;
            max_dist = 1.5f;
        }

        return new Projectile(new Texture(path),dmg,speed,max_dist,tag);
    }

    public static Player create_player(String playerName) {
        //Default stuffs
        String path = "missing_texture.png";
        float max_hp = 100;
        float speed = 100;
        float turn_speed = 0.055f;
        int shoot_frq = 100;
        int contact_dmg = 10;
        String fire_pattern = AssetLoader.fire_twin;
        String bullet = AssetLoader.projectile_blueRay;

        if (playerName.equals(AssetLoader.player_basic)) { //the most basic enemy
            path = "2.png";
            max_hp = 100;
            speed = 200;
            turn_speed = 0.07f;
            shoot_frq = 200;
            contact_dmg = 10;
            fire_pattern = AssetLoader.fire_cannon;
            bullet = AssetLoader.projectile_blueRay;
        }

        return new Player(new Texture(path),max_hp,shoot_frq,speed,turn_speed,contact_dmg,fire_pattern,bullet);
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
