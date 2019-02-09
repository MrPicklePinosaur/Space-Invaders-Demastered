package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import jdk.nashorn.internal.parser.JSONParser;

import java.util.ArrayList;

public class AssetLoader {
    public AssetManager assetManager;
    private JSONParser parser = new JSONParser();

    private static ArrayList<Body> purge_body = new ArrayList<Body>();

    public AssetLoader() {
        assetManager = new AssetManager();
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
