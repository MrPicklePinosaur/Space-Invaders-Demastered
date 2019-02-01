package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Entity {
    private String name;
    private float max_hp;
    private float hp;

    public Player(Texture texture) {
        super(texture);

        //=-=-=-=-=-= Define fixture for body =-=-=-=-=-=-=-
        //Figure out fixrure size depending on size of sprite etc
        //fdef = new fdef blah blah, fdef.density = 10 blach blah
        //this.body = create(world,fdef)
    }


    @Override
    public void update() {
    }

    @Override
    public void destroy() {
        //game over or respawn
    }

    //Getters
    public float getHp() { return this.hp; }

    //Setters
}