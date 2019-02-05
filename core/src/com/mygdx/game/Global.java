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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Global {
    public static int SCREEN_WIDTH = 1200;
    public static int SCREEN_HEIGHT = 800;
    public static final int PPM = 100; //pixels per meter
    public static World world;
    public static final int MAP_SIZE = 40000;
    public static float CAM_SIZE_X;
    public static float CAM_SIZE_Y;
    public static float RESOLUTION;

    //user input
    public static float mx;
    public static float my;
    public static float angle; //the angle the mouse makes with the center of the screen

    public static void handleInput() { //call in every single render loop to update
        //Find out how far mouse is from center of screen
        Global.mx = Gdx.input.getX()-Global.SCREEN_WIDTH/2f;
        Global.my = Global.SCREEN_HEIGHT/2f-Gdx.input.getY();
        Global.angle = MathUtils.atan2(my,mx); //calculate degree of mouse relative to center of screen
    }


}
