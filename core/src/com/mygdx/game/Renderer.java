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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;

public class Renderer {
    OrthographicCamera cam;
    Box2DDebugRenderer debugCam;

    public Renderer() {
        cam = new OrthographicCamera(400/Global.PPM,400/Global.PPM); //NOTE: GET RID OF HARDCODED VALUES LATER
        debugCam = new Box2DDebugRenderer();
    }

    public void clampCamera() { //makes sure that camera cant zoom in or out too far

    }

    public void shiftCamera() { //camera shifts in direction of mouse

    }

    //JUST FOR FUN --> IMPLEMENT LATER
    public void screenShake() {

    }



}
