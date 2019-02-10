/*
 ______     ______     __         __         __     ______     __     ______     __   __        __         __     ______     ______   ______     __   __     ______     ______
/\  ___\   /\  __ \   /\ \       /\ \       /\ \   /\  ___\   /\ \   /\  __ \   /\ "-.\ \      /\ \       /\ \   /\  ___\   /\__  _\ /\  ___\   /\ "-.\ \   /\  ___\   /\  == \
\ \ \____  \ \ \/\ \  \ \ \____  \ \ \____  \ \ \  \ \___  \  \ \ \  \ \ \/\ \  \ \ \-.  \     \ \ \____  \ \ \  \ \___  \  \/_/\ \/ \ \  __\   \ \ \-.  \  \ \  __\   \ \  __<
 \ \_____\  \ \_____\  \ \_____\  \ \_____\  \ \_\  \/\_____\  \ \_\  \ \_____\  \ \_\\"\_\     \ \_____\  \ \_\  \/\_____\    \ \_\  \ \_____\  \ \_\\"\_\  \ \_____\  \ \_\ \_\
  \/_____/   \/_____/   \/_____/   \/_____/   \/_/   \/_____/   \/_/   \/_____/   \/_/ \/_/      \/_____/   \/_/   \/_____/     \/_/   \/_____/   \/_/ \/_/   \/_____/   \/_/ /_/
 */
package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {

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
        */
        if (CollisionListener.fixtureMatch(fa,fb,Player.class,Enemy.class)) { //CASE 1: Player and enemy hit each other
            Player u = (Player) CollisionListener.isInstace(fa,fb,Player.class).getBody().getUserData(); //get the object of the fixtures
            Enemy e = (Enemy) CollisionListener.isInstace(fa,fb,Enemy.class).getBody().getUserData();
            u.modHp(-1*e.getContactDmg()); //deal damage to player
            e.modHp(-1*u.getContactDmg()); //deal damage to enemy
        }
        if (CollisionListener.fixtureMatch(fa,fb,Player.class,Projectile.class)) { //CASE 2: Player gets hit by enemy projectile
            Player u = (Player) CollisionListener.isInstace(fa,fb,Player.class).getBody().getUserData(); //get the object of the fixtures
            Projectile p = (Projectile) CollisionListener.isInstace(fa,fb,Projectile.class).getBody().getUserData();
            if (p.getTag() == Projectile.tag_enemy) { //make sure that player is actually hit by a enemy's projectile
                u.modHp(-1*p.getDMG()); //deal damage to player
            }
        }
        if (CollisionListener.fixtureMatch(fa,fb,Enemy.class,Projectile.class)) { //CASE 3: Enemy gets hit by player projectile
            Enemy e = (Enemy) CollisionListener.isInstace(fa,fb,Enemy.class).getBody().getUserData(); //get the object of the fixtures
            Projectile p = (Projectile) CollisionListener.isInstace(fa,fb,Projectile.class).getBody().getUserData();
            if (p.getTag() == Projectile.tag_player) { //make sure that enemy is actually hit by a player's projectile
                e.modHp(-1*p.getDMG()); //deal damage to enemy
            }
        }
    }

    //Helper methods
    public static Boolean fixtureMatch(Fixture fa,Fixture fb,Class<?> cls1,Class<?> cls2) { //checks to see if two fixtures are two certain object types
        if (cls1.isInstance(fa.getBody().getUserData()) && cls2.isInstance(fb.getBody().getUserData())) return true;
        if (cls2.isInstance(fa.getBody().getUserData()) && cls1.isInstance(fb.getBody().getUserData())) return true;
        return false;
    }

    public static Fixture isInstace(Fixture fa,Fixture fb,Class<?> cls) { //finds out which of the two fixtures is the type you want
        if (cls.isInstance(fa.getBody().getUserData())) return fa;
        if (cls.isInstance(fb.getBody().getUserData())) return fb;
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
