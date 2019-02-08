package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

public class TrashCan {

    public AssetManager assetManager;

    private static ArrayList<Body> purge_body = new ArrayList<Body>();

    public TrashCan() {
        assetManager = new AssetManager();
    }

    public static void sweepBodies() { //removes all bodies safely
        for (Body b : TrashCan.purge_body) {
            if (b != null) {
                Global.world.destroyBody(b);
                b.setUserData(null);
                b = null;
            }
        }
        TrashCan.purge_body.clear(); //MAY NOT BE SAFE
    }

    public static void flagForPurge(Body body) { TrashCan.purge_body.add(body); }
}
