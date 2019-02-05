/*
 __    __     ______     ______
/\ "-./  \   /\  __ \   /\  == \
\ \ \-./\ \  \ \  __ \  \ \  _-/
 \ \_\ \ \_\  \ \_\ \_\  \ \_\
  \/_/  \/_/   \/_/\/_/   \/_/
 */
//Holds the position of various important landmarks/structures around the world

package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Map {

    private static final int NOVICE = 0;
    private static final int ADEPT = 1;
    private static final int EXPERT = 2;
    private static final int LEGENDARY = 3;

    private static int DIVISION_SIZE = 1024; //size of each sector of map (in meters)

    private static int[][] map =   {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //this is the difficulty distribution for the map, with 0 being the easiest and
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, // 3 being the hardest
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                    {0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
                                    {0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
                                    {0,0,0,1,1,2,2,2,2,2,2,1,1,0,0,0},
                                    {0,0,0,1,1,2,2,3,3,2,2,1,1,0,0,0},
                                    {0,0,0,1,1,2,2,3,3,2,2,1,1,0,0,0},
                                    {0,0,0,1,1,2,2,2,2,2,2,1,1,0,0,0},
                                    {0,0,0,1,1,2,2,2,2,2,2,1,1,0,0,0},
                                    {0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
                                    {0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};

    private ArrayList<Vector2> gas_locations = new ArrayList<Vector2>(); //holds locations of gas stations
    private ArrayList<Vector2> asteroid_locations = new ArrayList<Vector2>(); //asteroid belts/clouds

    public Map() {
        //Generate gas station locations
    }

    public void generateEvent(int difficulty, int event, int numOfEvents) { //places certain events/objects such as asteroid or gas stations in a specific difficulty level

    }
    //Getters
    public Vector2 getSector(float px, float py) { //returns the current location of player
        float sectorX = px%Map.DIVISION_SIZE/Global.PPM;
        float sectorY = py%Map.DIVISION_SIZE/Global.PPM;
        return new Vector2(sectorX,sectorY);
    }
    public int getDifficulty(int sx, int sy) { return Map.map[sy][sx]; }//takes in coordinates of a sector and returns the difficulty

}
