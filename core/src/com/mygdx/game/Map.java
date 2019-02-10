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
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Map {

    private static BufferedImage mapPixels;
    private static Random randObj;//eNum,eX,eY;   //eNum amt of enemies spawning in sector, eX/eY is starting location
    private static float eNum,eAng;
    private static final int radius = 500/Global.PPM;

    public static final int DIVISION_SIZE = 10;//1024; //size of each sector of map (in meters)
                                    //changed to 1200 over 1024 as window size is 1200x800
                                    //could revert to 1024 if window is changed to 1024x768

    private static int[][] map =  new int[24][24];

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
    }

    public void generateEnemy(Player player) { //places certain events/objects such as asteroid or gas stations in a specific difficulty level
        randObj = new Random();
        Vector2 sector = Map.getSector(player);
        int difficulty = Map.getDifficulty((int)sector.x,(int)sector.y);

        int upperBound = 0;
        int lowerBound = 0;
        String[] enemy_pool = new String[1];

        //Determine enemy spawns based on difficulty level
        if (difficulty == 0) { //if difficult is zero, dont spawn any enemies
            return;
        } else if (player.getLvl()<5) {
            upperBound = 10; lowerBound = 3;
            enemy_pool = AssetLoader.difficulty_one;
        } else if (player.getLvl()>=5) {
            upperBound = 15; lowerBound = 5;
            enemy_pool = AssetLoader.difficulty_two;
        } else if (player.getLvl()>=10) {
            upperBound = 20; lowerBound = 7;
            enemy_pool = AssetLoader.difficulty_three;
        } else if (player.getLvl()>=15) {
            upperBound = 30; lowerBound = 10;
            enemy_pool = AssetLoader.difficulty_four;
        }

        upperBound = upperBound*difficulty;
        eNum = randObj.nextInt((upperBound - lowerBound) + 1) + lowerBound;

        //Spawn enemies
        for(int e=0;e<eNum;e++){
            eAng = Global.rand.nextInt(360)* MathUtils.degreesToRadians;
            float posX = player.getX()+(float)Math.cos(eAng)*radius;
            float posY = player.getY()+(float)Math.sin(eAng)*radius;
            Enemy.place_enemy(new Vector2(posX,posY),enemy_pool[Global.rand.nextInt(enemy_pool.length)]); //Choose a random enemy from the enemy pool
        }
    }

    public void getBounds(Player player){
        Vector2 sector = Map.getSector(player);
        int difficulty = Map.getDifficulty((int)sector.x,(int)sector.y);
        if(difficulty==0){
            //handle player rejection
        }
    }

    public static void randomPlayerSpawn(Player player){
        ArrayList<Vector2> sectors = new ArrayList<Vector2>();
        for(int i=0;i<24;i++){  //TODO: remove hardcoded dimensions
            for(int j=0;j<24;j++){
                if(map[j][i]==1){   //TODO: changed map[j][i] to map[i][j]. Why does this do anything
                    sectors.add(new Vector2(j,i));
                }
            }
        }

        int randChoice = Global.rand.nextInt(sectors.size());
        Vector2 startingSector = sectors.get(randChoice);
        player.init((startingSector.x)*DIVISION_SIZE+DIVISION_SIZE/2,(startingSector.y)*DIVISION_SIZE+DIVISION_SIZE/2,0);
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
