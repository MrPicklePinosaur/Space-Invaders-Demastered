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
	SpriteBatch batch;//,uiBatch;
	Renderer r;
	Player player;
	Vector2 oldSector,currSector;
	Map map;
	Sprite mapSprite; //temp variable; clean up later
	//NOTE: USE ASSETMANAGER TO MAKE DISPOSING EASIER
	Enemy e;
	UI ui;

	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT); //change window resolution
		Global.world = new World(new Vector2(0,0),true);
		Global.world.setContactListener(new CollisionListener());
		r = new Renderer(batch);
		batch = new SpriteBatch();

		System.out.println("Width: "+Gdx.graphics.getWidth()+"\nHeight: "+Gdx.graphics.getHeight());
		//Create Player
		player = new Player(new Texture("ship-green.png"),200f); //create player object
		e = new Enemy(new Texture("2.png"),150f,1);
		e.init(2f,2f,0); //place enemy in certain spot in world (replace later with spawning code)
		mapSprite = new Sprite(new Texture("space.png"));
		ui = new UI();
		map = new Map();
		//player = new Player(new Texture("ship-green.png"),300f); //create player object
		oldSector = Map.getSector(player.getX(),player.getY());
	}

	@Override
	public void render () {

		//DRAWING SPRITES TO SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1); //refresh screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.setProjectionMatrix(r.cam.combined);
		mapSprite.draw(batch);
		player.sprite.draw(batch); //draw player
		Enemy.drawAll(batch); //draw enemy
		Projectile.drawAll(batch);
		batch.end();


		//UI DRAWING
		//UI.batch.begin();
		ui.draw(player);
		//UI.batch.end();


		//UI things & tests
		if(Gdx.input.isKeyJustPressed(Input.Keys.O) && player.getHp()-10>=0){
			player.changeHp(-10);
			UI.updateHealth(player);
			System.out.println("Health decreased.");
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.P) && player.getHp()+10<=Player.max_hp){
			player.changeHp(10);
			UI.updateHealth(player);
			System.out.println("Health increased.");
		}

		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			Global.highscore++;
			System.out.println("Highscore increased.");
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Global.highscore-1>=0){
			Global.highscore--;
			System.out.println("Highscore decreased.");
		}

		//Check for player shooting projectiles
		//TODO: make this more effieient and move it to player class later
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) { //spawn projectile
			//Create new projectile object
			Projectile.shoot(new Texture("player_bullet.png"),5f,Projectile.tag_player,player.getX(),player.getY(),player.getRotation());
		}

		//UPDATE STUFF
		//Update Entities
		//System.out.println(AssetLoader.importFromJSON("data/entity_stats.json"));
		//Update Player
		player.handleInput();

		//Update Enemies
		e.move(player);
		Enemy.updateAll(player);

		//update projectiles
		Projectile.updateAll();

		//enemy spawning
		currSector = Map.getSector(player.getX(),player.getY());
		if((int)currSector.x!=(int)oldSector.x || (int)currSector.y!=(int)oldSector.y){
			//map.generateEvent();

			oldSector = currSector;
			System.out.println("New sector");
		}

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
