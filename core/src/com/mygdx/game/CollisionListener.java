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
        System.out.println("COLLISION! fa: "+fa.getUserData()+" fb: "+fb.getUserData());
    }
    //Helpers
    /*THERE ARE THREE CASES FOR COLLISION:
            1. player gets hit by enemy projectile (player takes dmg)
            2. enemy gets hit by player projectile (enemy takes dmg)
            3. enemy gets hit by enemy projectile (no one takes dmg and projectile <possibly> despawns)
    */
    public boolean isPlayerHit(Fixture fa, Fixture fb) { //CASE 1: check to see if the PLAYER was hit by an ENEMY BULLET
        if (fa.getUserData().equals(player_id) && fb.getUserData().equals(enemy_projectile_id)) { return true; }
        if (fa.getUserData().equals(enemy_projectile_id) && fb.getUserData().equals(player_id)) { return true; }
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
