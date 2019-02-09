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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Renderer r;
	Player player;
	Sprite map; //temp variable; clean up later
	//NOTE: USE ASSETMANAGER TO MAKE DISPOSING EASIER
	Enemy e;

	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT); //change window resolution
		Global.world = new World(new Vector2(0,0),true);
		Global.world.setContactListener(new CollisionListener());
		r = new Renderer(batch);
		batch = new SpriteBatch();

		System.out.println("Width: "+Gdx.graphics.getWidth()+"\nHeight: "+Gdx.graphics.getHeight());
		//Create Player
		player = new Player(new Texture("ship-green.png"),300f); //create player object
		e = new Enemy(new Texture("2.png"),150f,1);
		e.init(2f,2f,0); //place enemy in certain spot in world (replace later with spawning code)
		map = new Sprite(new Texture("space.jpg"));
	}

	@Override
	public void render () {

		//DRAWING SPRITES TO SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1); //refresh screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.setProjectionMatrix(r.cam.combined);
		map.draw(batch);
		player.sprite.draw(batch); //draw player
		e.sprite.draw(batch); //draw enemy
		Projectile.drawAll(batch);
		batch.end();

		//UPDATE STUFF
		//Update Entities
		player.handleInput();
		e.move(player);

		//Check for player shooting projectiles
		//TODO: make this more effieient and move it to player class later
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) { //spawn projectile
			//Create new projectile object
			Projectile p = new Projectile(new Texture("player_bullet.png"),5f);
			p.init(player.getX(),player.getY(),player.getRotation());
		}
		//update projectiles
		Projectile.updateAll();

		//Update world and viewport
		Global.world.step(1/60f, 6, 2); //NOTE: GET RID OF HARDCODED VALUES LATER
		AssetLoader.sweepBodies();
		r.debugCam.render(Global.world,r.cam.combined);
		r.moveCamera(player.sprite.getX(),player.sprite.getY());
		//r.screenShake(2f);
		r.cam.update(); //refresh camera
		Global.updateInput();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}


}
