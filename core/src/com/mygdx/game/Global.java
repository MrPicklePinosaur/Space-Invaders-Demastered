//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 ______     __         ______     ______     ______     __
/\  ___\   /\ \       /\  __ \   /\  == \   /\  __ \   /\ \
\ \ \__ \  \ \ \____  \ \ \/\ \  \ \  __<   \ \  __ \  \ \ \____
 \ \_____\  \ \_____\  \ \_____\  \ \_____\  \ \_\ \_\  \ \_____\
  \/_____/   \/_____/   \/_____/   \/_____/   \/_/\/_/   \/_____/
 */
//Holds all 'universal' constants and global variables (variables that needs to be access from anywhere in project)

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Random;

public class Global {
    public static Random rand = new Random();

    public static World world;
    public static AssetLoader assetLoader = new AssetLoader();
    public static Renderer r;
    //Some random vars
    public static int SCREEN_WIDTH = 1200;
    public static int SCREEN_HEIGHT = 800;
    public static final int PPM = 100; //pixels per meter
    public static final int MAP_SIZE = 40000;
    public static float CAM_SIZE_X = 400; //in meters, dont forget to divide by PPM later
    public static float CAM_SIZE_Y = 400;
    public static float RESOLUTION = Global.CAM_SIZE_Y/Global.CAM_SIZE_X;
    public static float delta = 1/60f;
    public static int hpBoxChance = 15;

    //user input + data
    public static float mx;
    public static float my;
    public static float angle; //the angle the mouse makes with the center of the screen
    public static int highscore;
    public static int currScore=0;

    public static void updateInput() { //call in every single render loop to update
        //Find out how far mouse is from center of screen
        Global.mx = Gdx.input.getX()-Global.SCREEN_WIDTH/2f;
        Global.my = Global.SCREEN_HEIGHT/2f-Gdx.input.getY();
        Global.angle = MathUtils.atan2(my,mx); //calculate degree of mouse relative to center of screen
    }

    public static double getDist(double x1,double y1,double x2,double y2){
        return Math.hypot((x2-x1),(y2-y1));
    }

}
