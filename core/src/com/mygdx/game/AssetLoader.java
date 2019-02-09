/*
 ______     ______     ______     ______     ______      __         ______     ______     _____     ______     ______
/\  __ \   /\  ___\   /\  ___\   /\  ___\   /\__  _\    /\ \       /\  __ \   /\  __ \   /\  __-.  /\  ___\   /\  == \
\ \  __ \  \ \___  \  \ \___  \  \ \  __\   \/_/\ \/    \ \ \____  \ \ \/\ \  \ \  __ \  \ \ \/\ \ \ \  __\   \ \  __<
 \ \_\ \_\  \/\_____\  \/\_____\  \ \_____\    \ \_\     \ \_____\  \ \_____\  \ \_\ \_\  \ \____-  \ \_____\  \ \_\ \_\
  \/_/\/_/   \/_____/   \/_____/   \/_____/     \/_/      \/_____/   \/_____/   \/_/\/_/   \/____/   \/_____/   \/_/ /_/
 */

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.HashMap;

public class AssetLoader {

    public AssetManager assetManager;
    //private JSONParser parser = new JSONParser();

   // public FileReader fileReader;
    private static ArrayList<Body> purge_body = new ArrayList<Body>();

    public AssetLoader() {
        this.assetManager = new AssetManager();
    }


    public static void importFromJSON(String path,String className) {
        JsonReader jsonReader = new JsonReader();
        HashMap<String,ArrayList> output = new HashMap<String, ArrayList>();
       // ArrayList block = new ArrayList();
        JsonValue input = jsonReader.parse(new FileHandle(path));

        for (JsonValue jv : input) { //for each 'object' in json, package it into a map
            //output.put(jv.get("name"),new ArrayList())
            System.out.println(jv.get("name"));
        }

    }

    /*
    public static void importFromJSON(String path,HashMap library) { //uploads class creation data to library
        Json json = new Json();
        ArrayList<JsonValue> input = json.fromJson(ArrayList.class,Gdx.files.internal(path));
        for (JsonValue jv : input) {
            library.put(jv.get("name"),1);
        }
        //System.out.println(AssetLoader.enemy_lib.get("asteroid"));
    }
    */
    //HARDCODED GARBAGE
    public static void create_basic_enemy() {

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
