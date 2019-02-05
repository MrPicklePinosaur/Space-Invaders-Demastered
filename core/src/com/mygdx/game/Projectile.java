/*
 ______   ______     ______       __     ______     ______     ______   __     __         ______
/\  == \ /\  == \   /\  __ \     /\ \   /\  ___\   /\  ___\   /\__  _\ /\ \   /\ \       /\  ___\
\ \  _-/ \ \  __<   \ \ \/\ \   _\_\ \  \ \  __\   \ \ \____  \/_/\ \/ \ \ \  \ \ \____  \ \  __\
 \ \_\    \ \_\ \_\  \ \_____\ /\_____\  \ \_____\  \ \_____\    \ \_\  \ \_\  \ \_____\  \ \_____\
  \/_/     \/_/ /_/   \/_____/ \/_____/   \/_____/   \/_____/     \/_/   \/_/   \/_____/   \/_____/
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Projectile{

    private static ArrayList<Projectile> active_projectiles = new ArrayList<Projectile>();
    private static ArrayList<Projectile> dead_projectiles = new ArrayList<Projectile>();

    private Sprite sprite;
    private float angle; //angle in radians
    private float speed;

    public Projectile(Texture texture,float angle,float speed) {
        this.sprite = new Sprite(texture);
        this.angle = angle;
        this.speed = speed;


    }

    //There may be no need as physics step may move all the projectiles anyways
    public static void updateAll() { //updates all active projectiles

    }
}
