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
import com.badlogic.gdx.physics.box2d.World;

public class Global {
    public static int SCREEN_WIDTH = 1200;
    public static int SCREEN_HEIGHT = 800;
    public static final int PPM = 100; //pixels per meter
    public static World world;

}
