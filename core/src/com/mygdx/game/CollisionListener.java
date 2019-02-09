package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.sun.org.apache.bcel.internal.generic.ObjectType;

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
        if (fa == null || fb == null) return;
        if (fa.getBody().getUserData() == null || fb.getBody().getUserData() == null) return;

        /*THERE ARE FOUR CASES FOR COLLISION:
            1. player hits an enemy
            2. player gets hit by enemy projectile (player takes dmg)
            3. enemy gets hit by player projectile (enemy takes dmg)
            4. enemy gets hit by enemy projectile (no one takes dmg and projectile <possibly> despawns)
        */
        //System.out.println(enemyIsHit(fa,fb));
        if (enemyIsHit(fa,fb) != null) {
            Enemy e = (Enemy) enemyIsHit(fa,fb).getBody().getUserData();
            e.modHp(-10); //deal damage to enemy
            System.out.println("Enemy hp: "+e.getHP());
        }

    }

    //Helper methods
    public Fixture enemyIsHit(Fixture fa,Fixture fb) {
        if (fa.getBody().getUserData() instanceof Projectile && fb.getBody().getUserData() instanceof Enemy) return fb;
        if (fb.getBody().getUserData() instanceof Projectile && fa.getBody().getUserData() instanceof Enemy) return fa;
        return null;
    }

    //Currently useless, but necessary, methods
    @Override
    public void endContact(Contact c) { }
    @Override
    public void preSolve(Contact c,Manifold m) { }
    @Override
    public void postSolve(Contact c,ContactImpulse i) { }
}
