/*
 __    __     ______     ______
/\ "-./  \   /\  __ \   /\  == \
\ \ \-./\ \  \ \  __ \  \ \  _-/
 \ \_\ \ \_\  \ \_\ \_\  \ \_\
  \/_/  \/_/   \/_/\/_/   \/_/
 */
//Holds the position of various important landmarks/structures around the world

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.*;

public class Map {

    private static BufferedImage mapPixels;
    private static final int NOVICE = 0;
    private static final int ADEPT = 1;
    private static final int EXPERT = 2;
    private static final int LEGENDARY = 3;
    private static HashMap<Integer,Integer> mapHash = new HashMap<Integer,Integer>();

    private static int DIVISION_SIZE = 1024; //size of each sector of map (in meters)

    private static int[][] map =  new int[24][24];/*{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //this is the difficulty distribution for the map, with 0 being the easiest and
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
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};*/

    private ArrayList<Vector2> gas_locations = new ArrayList<Vector2>(); //holds locations of gas stations
    private ArrayList<Vector2> asteroid_locations = new ArrayList<Vector2>(); //asteroid belts/clouds

    public Map() {
        //Generate gas station locations
        try{
            mapPixels = ImageIO.read(new File("map.png"));
        }catch(IOException e){
            System.out.println("map.png isn't where it should be.");
        }
        int row = 24;int column = 24;Color colour;
        for(int r=0;r<row;r++){
            for(int c=0;c<column;c++){
                colour = new Color(mapPixels.getRGB(c,r));
                if(colour.r==0.0f && colour.g==0.0f && colour.b==255f){
                    map[c][r] = 0;
                }
                if(colour.r==0f && colour.g==0f && colour.b==255f){
                    map[c][r] = 1;
                }
                if(colour.r==0f && colour.g==255f && colour.b==0f){
                    map[c][r] = 2;
                }
                if(colour.r==255f && colour.g==175f && colour.b==0f){
                    map[c][r] = 3;
                }
                if(colour.r==255f && colour.g==150f && colour.b==150f){
                    map[c][r] = 4;
                }
                if(colour.r==255f && colour.g==0f && colour.b==0f){
                    map[c][r] = 5;
                }
            }
        }
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
