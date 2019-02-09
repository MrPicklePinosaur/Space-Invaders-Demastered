package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;

//import org.json.simple.parser.*;

import java.io.FileReader;
import java.util.ArrayList;

public class AssetLoader {
    public AssetManager assetManager;
   // public FileReader fileReader;
    private static ArrayList<Body> purge_body = new ArrayList<Body>();

    public AssetLoader() {
        this.assetManager = new AssetManager();
        //this.parser = new JSONParser();
    }
    /*
    public void importFromJSON(String path) {
        try {
            FileReader fileReader = new FileReader(path);

        }
        catch() {

        }
    }
    */


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
