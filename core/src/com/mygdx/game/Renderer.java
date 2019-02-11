//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 ______     ______     __   __     _____     ______     ______     ______     ______
/\  == \   /\  ___\   /\ "-.\ \   /\  __-.  /\  ___\   /\  == \   /\  ___\   /\  == \
\ \  __<   \ \  __\   \ \ \-.  \  \ \ \/\ \ \ \  __\   \ \  __<   \ \  __\   \ \  __<
 \ \_\ \_\  \ \_____\  \ \_\\"\_\  \ \____-  \ \_____\  \ \_\ \_\  \ \_____\  \ \_\ \_\
  \/_/ /_/   \/_____/   \/_/ \/_/   \/____/   \/_____/   \/_/ /_/   \/_____/   \/_/ /_/
 */
//       'Your go-to class for anything graphic'
//     Special effects like camera shift and screenshake

package com.mygdx.game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Renderer {
    OrthographicCamera cam;
    Box2DDebugRenderer debugCam;
    private boolean toggleLockedCamera = false;

    public Renderer(SpriteBatch batch) {
        cam = new OrthographicCamera(400/Global.PPM,400/Global.PPM); //NOTE: GET RID OF HARDCODED VALUES LATER
        debugCam = new Box2DDebugRenderer();
    }

    public void moveCamera(Player player) { //makes sure that camera cant zoom in or out too far and cant stray too far from player
        //Shift camera towards direction of mouse
        float px = player.getX();
        float py = player.getY();
        float camShiftSpeed = player.getSpeed()/256f/Global.PPM;
        this.cam.translate(camShiftSpeed*Global.mx/Global.PPM,camShiftSpeed*Global.my/Global.PPM); //shift camera depending on direction of mouse
        //Lock camera to not go beyond a certain distance away from player
        float maxCamDist = 150/Global.PPM; //TODO: GET RID OF HARDCODED STUFF
        if (this.toggleLockedCamera) { maxCamDist = 0; }
        this.cam.position.x = MathUtils.clamp(this.cam.position.x,px-maxCamDist,px+maxCamDist);
        this.cam.position.y = MathUtils.clamp(this.cam.position.y,py-maxCamDist,py+maxCamDist);
    }

    public void screenShake(float intensity) { //intensity is the amount the camera shifts (in meters)
        float shakeAngle = Global.rand.nextInt(360)*MathUtils.degreesToRadians;
        this.cam.translate(intensity*MathUtils.cos(shakeAngle)/Global.PPM,intensity*MathUtils.sin(shakeAngle)/Global.PPM);
    }
    public void toggleLockedCamera() {
        this.toggleLockedCamera = this.toggleLockedCamera == true ? false : true;
    }



}
