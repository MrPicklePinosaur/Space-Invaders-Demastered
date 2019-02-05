package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        //check that meaningful collisions are occuring
        System.out.println("HIT");
        /*
        if (fa == null || fb == null) { return; }
        if (fa.getUserData() == null || fb.getUserData() == null ) { return; }
        */
        /*
        if (fa.getUserData() != null && fa.getUserData().equals()) {

        }
        if (fb.getUserData() != null && fb.getUserData().equals()) {

        }
        */
    }

    @Override
    public void endContact(Contact c) {
        System.out.println("Collision ended");
    }
    @Override
    public void preSolve(Contact c,Manifold m) {
    }
    @Override
    public void postSolve(Contact c,ContactImpulse i) {
    }
}
