/*
 __    __     ______     ______
/\ "-./  \   /\  __ \   /\  == \
\ \ \-./\ \  \ \  __ \  \ \  _-/
 \ \_\ \ \_\  \ \_\ \_\  \ \_\
  \/_/  \/_/   \/_/\/_/   \/_/
 */
//Holds the position of various important landmarks/structures around the world

package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.graphics.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Map {

    private static BufferedImage mapPixels;
    private static int UpperBound=10,LowerBound=5;    //min/max amt of enemies spawn in a certain sector; inclusive
    private static Random randObj;//eNum,eX,eY;   //eNum amt of enemies spawning in sector, eX/eY is starting location
    private static float eNum,eX,eY,eAng;
    private static final int radius = 500/Global.PPM;
    private static HashMap<Integer,Integer> mapHash = new HashMap<Integer,Integer>();

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();

    //private static boolean placed = false; //used to determine if the random enemy position is valid

    public static final int DIVISION_SIZE = 100;//1024; //size of each sector of map (in meters)
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
        int row = 24;int column = 24;
        Color colour;
        for(int r=0;r<row;r++){
            for(int c=0;c<column;c++){
                colour = new Color(mapPixels.getRGB(c,r));
                //System.out.println("R: "+Math.round(colour.getRed()*255)+" G: "+Math.round(colour.getGreen()*255)+" B: "+Math.round(colour.getBlue()*255));
                if(colour.getRed()==0 && colour.getGreen()==0 && colour.getBlue()==255){
                    map[r][c] = 0;
                }
                if(colour.getRed()==0 && colour.getGreen()==255 && colour.getBlue()==0){
                    map[r][c] = 1;
                }
                if(colour.getRed()==255 && colour.getGreen()==175 && colour.getBlue()==0){
                    map[r][c] = 2;
                }
                if(colour.getRed()==255 && colour.getGreen()==150 && colour.getBlue()==150){
                    map[r][c] = 3;
                }
                if(colour.getRed()==255 && colour.getGreen()==0 && colour.getBlue()==0){
                    map[r][c] = 4;
                }
            }
        }
        //System.out.println(Arrays.deepToString(map));
    }

    public void generateEnemy(Player player) { //places certain events/objects such as asteroid or gas stations in a specific difficulty level
        randObj = new Random();
        Vector2 sector = Map.getSector(player);
        int difficulty = Map.getDifficulty((int)sector.x,(int)sector.y);
        if(difficulty!=0){
            //System.out.println("Enemy generation process started");
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
                eAng = Global.rand.nextInt(360)* MathUtils.degreesToRadians;
                float posX = player.getX()+(float)Math.cos(eAng)*radius;
                float posY = player.getY()+(float)Math.sin(eAng)*radius;
                Enemy.place_enemy(new Vector2(posX,posY));

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
        Vector2 sector = Map.getSector(player);
        int difficulty = Map.getDifficulty((int)sector.x,(int)sector.y);
        if(difficulty==0){
            //handle player rejection
            //System.out.println("Reached edge of map.");
        }
    }

    public static void randomPlayerSpawn(Player player){
        //System.out.println(Arrays.deepToString(map));
        ArrayList<Vector2> sectors = new ArrayList<Vector2>();
        for(int i=0;i<24;i++){  //TODO: remove hardcoded dimensions
            for(int j=0;j<24;j++){
                //System.out.println(map[i][j]);
                if(map[j][i]==1){   //TODO: changed map[j][i] to map[i][j]. Why does this do anything
                    sectors.add(new Vector2(j,i));
                }
            }
        }

        //System.out.println(sectors);
        int randChoice = Global.rand.nextInt(sectors.size());
        //System.out.println(randChoice+"\n"+sectors.get(randChoice));
        Vector2 startingSector = sectors.get(randChoice);
        //System.out.println(startingSector);
        player.init((startingSector.x)*DIVISION_SIZE+DIVISION_SIZE/2,(startingSector.y)*DIVISION_SIZE+DIVISION_SIZE/2,0);
        //sector + divisionsize/2
        //System.out.println(startingSector.x+" "+startingSector.y);

        //System.out.println(player.getX()+" "+player.getY());
    }

    //Getters
    public static Vector2 getSector(Player player) { //returns the current location of player
        float sectorX = (float)(player.sprite.getX()/Map.DIVISION_SIZE);
        float sectorY = (float)(player.sprite.getY()/Map.DIVISION_SIZE);
        //System.out.println(sectorX+" "+sectorY);
        return new Vector2(sectorX,sectorY);
    }
    public static int getDifficulty(int sx, int sy) { return map[sy][sx]; }//takes in coordinates of a sector and returns the difficulty

}
