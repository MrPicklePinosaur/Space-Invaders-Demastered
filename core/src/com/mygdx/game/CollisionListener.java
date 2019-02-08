package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {

    public static final String player_id = "player";
    public static final String enemy_id = "enemy";
    public static final String player_projectile_id = "player_projectile";
    public static final String enemy_projectile_id = "enemy_projectile";

    @Override
    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        //check that meaningful collisions are occuring
        if (fa == null || fb == null) { return; }
        if (fa.getBody().getUserData() == null || fb.getBody().getUserData() == null ) { return; }
        /*THERE ARE FOUR CASES FOR COLLISION:
            1. player hits an enemy
            2. player gets hit by enemy projectile (player takes dmg)
            3. enemy gets hit by player projectile (enemy takes dmg)
            4. enemy gets hit by enemy projectile (no one takes dmg and projectile <possibly> despawns)
        */
        if (CollisionListener.isCollision(fa,fb,CollisionListener.player_id,CollisionListener.enemy_id)) { //Case 1
            System.out.println("player hit enemy!");
            //both player and enemy takes contact dmg
        } else if (CollisionListener.isCollision(fa,fb,CollisionListener.player_id,CollisionListener.enemy_projectile_id)) { //Case 2
            System.out.println("player is hit by enemy projectile!");
            //player takes bullet damage
        } else if (CollisionListener.isCollision(fa,fb,CollisionListener.enemy_id,CollisionListener.player_projectile_id)) { //Case 3
            System.out.println("enemy is hit by player projecitle!");
            //enemy takes damage
        } else if (CollisionListener.isCollision(fa,fb,CollisionListener.enemy_id,CollisionListener.enemy_projectile_id)) { //Case 4
            System.out.println("enemy is hit by enemy projectile!");
            //TODO: decides what happens here
        }
        //System.out.println("COLLISION! fa: "+fa.getBody().getUserData()+" fb: "+fb.getBody().getUserData());
    }

    public static boolean isCollision(Fixture fa, Fixture fb,String id_1,String id_2) { //checks to see if the two fixtures have certain id's
        if (fa.getBody().getUserData().equals(id_1) && fb.getBody().getUserData().equals(id_2)) { return true; }
        if (fa.getBody().getUserData().equals(id_2) && fb.getBody().getUserData().equals(id_1)) { return true; }
        return false;
    }

    //Currently useless, but necessary, methods
    @Override
    public void endContact(Contact c) { }
    @Override
    public void preSolve(Contact c,Manifold m) { }
    @Override
    public void postSolve(Contact c,ContactImpulse i) { }
}
