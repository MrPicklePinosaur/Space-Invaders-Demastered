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
    private static int UpperBound=2,LowerBound=1;    //min/max amt of enemies spawn in a certain sector; inclusive
    private static Random randObj;//eNum,eX,eY;   //eNum amt of enemies spawning in sector, eX/eY is starting location
    private static int eNum,eX,eY;
    private static final int NOVICE = 0;
    private static final int ADEPT = 1;
    private static final int EXPERT = 2;
    private static final int LEGENDARY = 3;
    private static HashMap<Integer,Integer> mapHash = new HashMap<Integer,Integer>();

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();

    private static boolean placed = false; //used to determine if the random enemy position is valid

    private static int DIVISION_SIZE = 1200;//1024; //size of each sector of map (in meters)
                                    //changed to 1200 over 1024 as window size is 1200x800
                                    //could revert to 1024 if window is changed to 1024x768

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
                if(colour.r==0f && colour.g==255f && colour.b==0f){
                    map[c][r] = 1;
                }
                if(colour.r==255f && colour.g==175f && colour.b==0f){
                    map[c][r] = 2;
                }
                if(colour.r==255f && colour.g==150f && colour.b==150f){
                    map[c][r] = 3;
                }
                if(colour.r==255f && colour.g==0f && colour.b==0f){
                    map[c][r] = 4;
                }
            }
        }
    }

    public void generateEvent(Player player,Vector2 sector, int event, int numOfEvents) { //places certain events/objects such as asteroid or gas stations in a specific difficulty level
        if(event==1){   //generate enemies!
            int difficulty = map[(int)sector.x][(int)sector.y];
            if(difficulty!=0){

                UpperBound = UpperBound*difficulty;
                eNum = randObj.nextInt((UpperBound - LowerBound) + 1) + LowerBound;
                for(int e=0;e<eNum;e++){
                    //TODO: Logic for creating enemies randomly in the player's current sector that don't spawn inside any bodies
                    /*
                    pX and pY are TEMPORARY PARAMETERS/ARGUEMENTS
                    Proper logic for getting eX,eY from randObj needs to be calculated

                    UPDATE: replaced pX,pY with player to give use to place_enemy method in Enemy class
                    */
                    while(!placed) {
                        eX = randObj.nextInt((((int)player.getX() + Map.DIVISION_SIZE) - ((int)player.getX() - Map.DIVISION_SIZE)) + 1) + ((int)player.getX() - Map.DIVISION_SIZE);//((int)sector.x*Map.DIVISION_SIZE);  //how to convert from sector back to number
                        eY = randObj.nextInt((((int)player.getY() + Map.DIVISION_SIZE) - ((int)player.getY() - Map.DIVISION_SIZE)) + 1) + ((int)player.getY() - Map.DIVISION_SIZE);
                        if ((eX + Enemy.SHIP_SIZE/2F)>Enemy.SHIP_SIZE) {
                            //enemyArrayList.add(new Enemy(,difficulty));
                            Enemy.place_enemy(player,(float)Global.getDist(player.getX(),player.getY(),player.getX()-Global.SCREEN_WIDTH,player.getY()-Global.SCREEN_HEIGHT),difficulty);
                            placed = true;
                        }
                    }
                }

            }else{
                //This should never be reached
                //ideally the generateEvent method should only be called when the player is not at the edges of the map
                //however, we can just warn the player and let the auto boundary-rejection handle the player
                System.out.println("You are near the border of the forcefield!");   //Right now this is printed in the console
                                                                                    //Later will show up on-screen
            }
        }
        if(event==0){   //kill enemies not being used!

            for(Enemy e : enemyArrayList){
                //AssetManager use goes HERE
            }

        }
    }
    //Getters
    public Vector2 getSector(float px, float py) { //returns the current location of player
        float sectorX = px%Map.DIVISION_SIZE/Global.PPM;
        float sectorY = py%Map.DIVISION_SIZE/Global.PPM;
        return new Vector2(sectorX,sectorY);
    }
    public int getDifficulty(int sx, int sy) { return Map.map[sy][sx]; }//takes in coordinates of a sector and returns the difficulty

}
