package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class BoxBodyBuilder {

    /*
    wrapper to create circle box2d body objects
    maybe use? I think it'll make our naming conventions too ugly, but idk
    also is this too similar to Entity.java?

    pulled from rotatingcanvas.com
    */

    public static float WORLD_TO_BOX=0.01f;
    public static float BOX_TO_WORLD=100f;

    static float ConvertToBox(float x){
        return x*WORLD_TO_BOX;
    }

    static float ConvertToWorld(float x){
        return x*BOX_TO_WORLD;
    }

    public Body CreateCircleBody(World world, BodyDef.BodyType bodyType, float posx, float posy,
                                 float radius){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(ConvertToBox(posx),ConvertToBox(posy));
        bodyDef.angle=0;

        Body body = world.createBody(bodyDef);
        MakeCircleBody(body,radius,bodyType,1,1,0,0);
        return body;
    }

    void MakeCircleBody(Body body, float radius, BodyDef.BodyType bodyType,
                        float density, float restitution, float angle, float friction){

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=density;
        fixtureDef.restitution=restitution;
        fixtureDef.friction=friction;
        fixtureDef.shape=new CircleShape();
        fixtureDef.shape.setRadius(ConvertToBox(radius));

        body.createFixture(fixtureDef);
        fixtureDef.shape.dispose();
    }
}
