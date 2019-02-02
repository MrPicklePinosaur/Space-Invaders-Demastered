//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 __    __     ______     __     __   __
/\ "-./  \   /\  __ \   /\ \   /\ "-.\ \
\ \ \-./\ \  \ \  __ \  \ \ \  \ \ \-.  \
 \ \_\ \ \_\  \ \_\ \_\  \ \_\  \ \_\\"\_\
  \/_/  \/_/   \/_/\/_/   \/_/   \/_/ \/_/
 */

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
	Renderer renderer;
	Player player;
	//NOTE: USE ASSETMANAGER TO MAKE DISPOSING EASIER
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT); //change window resolution
		Global.world = new World(new Vector2(0,0),true);
		renderer = new Renderer();
		batch = new SpriteBatch();

		//Create Plater
		player = new Player(new Texture("ship-blue.png")); //create player object
		System.out.println(player.sprite.getX()+" "+player.sprite.getY());
		System.out.println(player.body.getPosition().x+" "+player.body.getPosition().y);
	}

	@Override
	public void render () {

		//DRAWING SPRITES TO SCREEN
		Gdx.gl.glClearColor(1, 0, 0, 1); //refresh screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		player.sprite.draw(batch);
		batch.end();

		Global.world.step(1/60f, 6, 2); //NOTE: GET RID OF HARDCODED VALUES LATER
		renderer.debugCam.render(Global.world,renderer.cam.combined);
		renderer.cam.update(); //refresh camera
	}

	public void handleInput() {
		//Accelerate / deccelerate

		//Rotate ship using mouse
	}

	@Override
	public void dispose () {
		batch.dispose();
	}


}
