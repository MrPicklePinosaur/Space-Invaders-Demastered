package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Main extends ApplicationAdapter {
	World world;
	SpriteBatch batch;
	Texture img;
	//NOTE: USE ASSETMANAGER TO MAKE DISPOSING EASIER
	
	@Override
	public void create () {

		Global.world = new World(new Vector2(0,0),true);
		Renderer r =
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	public void handleInput() {
		//Accelerate / deccelerate

		//Rotate ship using mouse
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}


}
