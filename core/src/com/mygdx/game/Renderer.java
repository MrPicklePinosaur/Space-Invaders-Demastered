//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 ______     ______     __   __     _____     ______     ______     ______     ______
/\  == \   /\  ___\   /\ "-.\ \   /\  __-.  /\  ___\   /\  == \   /\  ___\   /\  == \
\ \  __<   \ \  __\   \ \ \-.  \  \ \ \/\ \ \ \  __\   \ \  __<   \ \  __\   \ \  __<
 \ \_\ \_\  \ \_____\  \ \_\\"\_\  \ \____-  \ \_____\  \ \_\ \_\  \ \_____\  \ \_\ \_\
  \/_/ /_/   \/_____/   \/_/ \/_/   \/____/   \/_____/   \/_/ /_/   \/_____/   \/_/ /_/
 */
//       'Your go-to class for anything graphic'

package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Renderer {
    OrthographicCamera cam;
    Box2DDebugRenderer debugCam;

    /*
    float WINDOW_SIZE_X;
    float WINDOW_SIZE_Y;
    float CAM_SIZE_X;
    float CAM_SIZE_Y;
    float RESOLUTION;
    Sprite sprite,background;
    int SHIP_SIZE = 64;
    int MAP_SIZE = 40000;
    EasyEnemy e;
    float speed = 10;
    */

    public Renderer(SpriteBatch batch) {
        cam = new OrthographicCamera(400/Global.PPM,400/Global.PPM); //NOTE: GET RID OF HARDCODED VALUES LATER
        debugCam = new Box2DDebugRenderer();
        /*
        WINDOW_SIZE_X = Gdx.graphics.getWidth();
        WINDOW_SIZE_Y = Gdx.graphics.getHeight();
        Global.CAM_SIZE_X = cam.viewportWidth;
        Global.CAM_SIZE_Y = cam.viewportHeight;
        Global.RESOLUTION = (WINDOW_SIZE_Y/WINDOW_SIZE_X);
        //System.out.println(CAM_SIZE_X+" "+CAM_SIZE_Y);

        //sprite = new Sprite(new Texture("ship-blue.png"));
        background = new Sprite(new Texture("space.jpg"));
        //sprite.setSize(SHIP_SIZE,SHIP_SIZE*(sprite.getHeight()/sprite.getWidth())); //ALSO GET SHIP RESOLUTION IN CASE THE SPRITE IS NOT A SQUARE
        background.setSize(MAP_SIZE,MAP_SIZE*RESOLUTION); //fix height with resolution

        Player player = new Player(new Texture("ship-blue.png"));
        e = new EasyEnemy(new Texture("ship-blue.png"),player);

        //sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f); //allows sprite to rotate around center
        //sprite.setPosition(CAM_SIZE_X/2f-sprite.getWidth()/2f,CAM_SIZE_Y/2f-sprite.getHeight()/2f); //set sprite as starting in center of screen
        //sprite.setRotation(-90);
        background.setPosition(0,0);

        cam.position.set(CAM_SIZE_X/2f-player.sprite.getWidth()/2f,CAM_SIZE_Y/2f-player.sprite.getHeight()/2f,0); //offset camera to center of screen
        cam.zoom = 3f;
        */
    }
    /*
    public void draw(SpriteBatch batch) {
        //handleInput();
        //System.out.println(sprite.getX()+" "+sprite.getY());
        e.move(sprite.getX(), sprite.getY());
        cam.update(); //update camera
        batch.setProjectionMatrix(cam.combined);
        background.draw(batch);
        sprite.draw(batch);
        e.draw(batch);
        //rest of draw will come here later, with more objs being added
        Box2DDebugRenderer debugCam;
    }
    */
    public void moveCamera(float px, float py) { //makes sure that camera cant zoom in or out too far and cant stray too far from player
        //Shift camera towards direction of mouse
        //NOTE: The code below is repetitive, possibly move to global
        //Find out how far mouse is from center of screen
        float mx = Gdx.input.getX()-Global.SCREEN_WIDTH/2f;
        float my = Global.SCREEN_HEIGHT/2f-Gdx.input.getY();
        float angle = MathUtils.atan2(my,mx); //calculate degree of mouse relative to center of screen
        float camShiftSpeed = 3f;
        this.cam.translate(camShiftSpeed*MathUtils.cos(angle)/Global.PPM,camShiftSpeed*MathUtils.sin(angle)/Global.PPM);
        //Camera follows player
        float maxCamDist = 150/Global.PPM; //TODO: GET RID OF HARDCODED STUFF
        this.cam.position.x = MathUtils.clamp(this.cam.position.x,px-maxCamDist,px+maxCamDist);
        this.cam.position.y = MathUtils.clamp(this.cam.position.y,py-maxCamDist,py+maxCamDist);
    }


    //JUST FOR FUN --> IMPLEMENT LATER
    public void screenShake() {

    }



}
