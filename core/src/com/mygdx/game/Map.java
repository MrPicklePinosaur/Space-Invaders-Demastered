/*
 __    __     ______     ______
/\ "-./  \   /\  __ \   /\  == \
\ \ \-./\ \  \ \  __ \  \ \  _-/
 \ \_\ \ \_\  \ \_\ \_\  \ \_\
  \/_/  \/_/   \/_/\/_/   \/_/
 */
//Holds the position of various important landmarks/structures around the world

package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Map {

    private static BufferedImage mapPixels;
    private static int UpperBound=2,LowerBound=1;    //min/max amt of enemies spawn in a certain sector; inclusive
    private static Random randObj;//eNum,eX,eY;   //eNum amt of enemies spawning in sector, eX/eY is starting location
    private static float eNum,eX,eY,eAng;
    private static final int NOVICE = 0;
    private static final int ADEPT = 1;
    private static final int EXPERT = 2;
    private static final int LEGENDARY = 3;
    private static final int radius = 1500/Global.PPM;
    private static HashMap<Integer,Integer> mapHash = new HashMap<Integer,Integer>();

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();

    //private static boolean placed = false; //used to determine if the random enemy position is valid

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
                System.out.println("R: "+Math.round(colour.r*255)+" G: "+Math.round(colour.g*255)+" B: "+Math.round(colour.b*255));
                if(Math.round(colour.r*255)==0.0f && Math.round(colour.g*255)==0.0f && Math.round(colour.b*255)==255f){
                    map[c][r] = 0;
                }
                if(Math.round(colour.r*255)==0f && Math.round(colour.g*255)==255f && Math.round(colour.b*255)==0f){
                    map[c][r] = 1;
                }
                if(Math.round(colour.r*255)==255f && Math.round(colour.g*255)==175f && Math.round(colour.b*255)==0f){
                    map[c][r] = 2;
                }
                if(Math.round(colour.r*255)==255f && Math.round(colour.g*255)==150f && Math.round(colour.b*255)==150f){
                    map[c][r] = 3;
                }
                if(Math.round(colour.r*255)==255f && Math.round(colour.g*255)==0f && Math.round(colour.b*255)==0f){
                    map[c][r] = 4;
                }
            }
        }
        System.out.println(Arrays.deepToString(map));
    }

    public void generateEnemy(Player player) { //places certain events/objects such as asteroid or gas stations in a specific difficulty level
        Vector2 sector = Map.getSector(player.getX(),player.getY());
        int difficulty = Map.getDifficulty((int)sector.x,(int)sector.y);
        if(difficulty!=0){
            UpperBound = UpperBound*difficulty;
            eNum = randObj.nextInt((UpperBound - LowerBound) + 1) + LowerBound;
            for(int e=0;e<eNum;e++){
                //TODO: Logic for creating enemies randomly in the player's current sector that don't spawn inside any bodies
                /*
                pX and pY are TEMPORARY PARAMETERS/ARGUEMENTS
                Proper logic for getting eX,eY from randObj needs to be calculated

                UPDATE: replaced pX,pY with player to give use to place_enemy method in Enemy class


                LOGIC:
                choose position by circle of arbitrary radius with center being player coordinates

                FOR KILLLIST:
                instead of doing for Enemy e : enemyList
                loop by index (i.e. for loop or while with indexes) that goes in reverse to avoid crash

                When removing:
                trashcan.flagforpurge
                trashcan.removebody(e.getBody)
                THEN remove from enemy list


                MAKE SURE TO TAKE IT OUT OF WHILE LOOP WE DONT NEED TO CHECK SPAWN COLLIDE ANYMORE
                */
                /*while(!placed) {
                    //eX = randObj.nextInt((((int)player.getX() + Map.DIVISION_SIZE) - ((int)player.getX() - Map.DIVISION_SIZE)) + 1) + ((int)player.getX() - Map.DIVISION_SIZE);//((int)sector.x*Map.DIVISION_SIZE);  //how to convert from sector back to number
                    //eY = randObj.nextInt((((int)player.getY() + Map.DIVISION_SIZE) - ((int)player.getY() - Map.DIVISION_SIZE)) + 1) + ((int)player.getY() - Map.DIVISION_SIZE);
                    eAng = randObj.nextInt((int)(Math.toRadians((double)360)-Math.toRadians((double)0)+1));
                    //if (){//(eX + Enemy.SHIP_SIZE/2F)>Enemy.SHIP_SIZE) {
                        //enemyArrayList.add(new Enemy(,difficulty));
                        //Enemy.place_enemy(player,(float)Global.getDist(player.getX(),player.getY(),player.getX()-Global.SCREEN_WIDTH,player.getY()-Global.SCREEN_HEIGHT),difficulty);
                    float posX = Math.cos(eAng);
                    float posY = ;
                    Enemy.place_enemy(player,new Vector2(posX,posY),difficulty);
                    placed = true;
                    //}
                }*/
                eAng = randObj.nextInt((int)(Math.toRadians((double)360)-Math.toRadians((double)0)+1));
                float posX = (float)Math.cos(eAng)*radius;
                float posY = (float)Math.sin(eAng)*radius;
                Enemy.place_enemy(new Vector2(posX,posY),difficulty);

            }

        }else{
            //This should never be reached
            //ideally the generateEvent method should only be called when the player is not at the edges of the map
            //however, we can just warn the player and let the auto boundary-rejection handle the player
            System.out.println("You are near the border of the forcefield!");   //Right now this is printed in the console
                                                                                //Later will show up on-screen
        }
    }

    public void getBounds(Player player){
        Vector2 sector = Map.getSector(player.getX(),player.getY());
        int difficulty = Map.getDifficulty((int)sector.x,(int)sector.y);
        if(difficulty==0){
            //handle player rejection
            System.out.println("Reached edge of map.");
        }
    }

    //Getters
    public static Vector2 getSector(float px, float py) { //returns the current location of player
        float sectorX = px%Map.DIVISION_SIZE/Global.PPM;
        float sectorY = py%Map.DIVISION_SIZE/Global.PPM;
        return new Vector2(sectorX,sectorY);
    }
    public static int getDifficulty(int sx, int sy) { return Map.map[sy][sx]; }//takes in coordinates of a sector and returns the difficulty

}
