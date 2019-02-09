package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.*;
import com.badlogic.gdx.utils.JsonValue;

import java.io.FileReader;
import java.util.ArrayList;

public class AssetLoader {
    public AssetManager assetManager;
    //private JSONParser parser = new JSONParser();

   // public FileReader fileReader;
    private static ArrayList<Body> purge_body = new ArrayList<Body>();

    public AssetLoader() {
        this.assetManager = new AssetManager();
    }

    public static ArrayList<Object> importFromJSON(String path) {
        Json json = new Json();
        ArrayList<Object> outputList = new ArrayList<Object>(); //the data the user gets
        ArrayList<JsonValue> inputList = json.fromJson(ArrayList.class, Gdx.files.internal(path)); //the data taken from the .json file

        for (JsonValue v : inputList) {
            outputList.add(json.readValue(Object.class,v));
        }
        return outputList;
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
