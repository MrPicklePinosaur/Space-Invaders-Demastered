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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.xml.soap.Text;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;//,uiBatch;
	Renderer r;
	Player player;
	Vector2 oldSector,currSector;
	Map map;
	Sprite mapSprite; //temp variable; clean up later
	UI ui;
	//NOTE: USE ASSETMANAGER TO MAKE DISPOSING EASIER
	Texture bg; TextureRegion tRegion;

	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT); //change window resolution
		Global.world = new World(new Vector2(0,0),true);
		Global.world.setContactListener(new CollisionListener());
		r = new Renderer(batch);
		batch = new SpriteBatch();

		try{
			Scanner inFile = new Scanner(new BufferedReader(new FileReader("highscore.txt")));
			int highscore;
			while(inFile.hasNextInt()){
				highscore = inFile.nextInt();
				Global.highscore = highscore;
			}
			inFile.close();
		}catch(IOException ioe){System.out.println("highscore.txt does not exist");}

		System.out.println("Width: "+Gdx.graphics.getWidth()+"\nHeight: "+Gdx.graphics.getHeight());
		//Create Player
		player = new Player(new Texture("ship-green.png"),200f); //create player object
		oldSector = Map.getSector(player);
		currSector = new Vector2(-1,-1);
		mapSprite = new Sprite(new Texture("space.png"),Map.DIVISION_SIZE*24/Global.PPM,Map.DIVISION_SIZE*24/Global.PPM);
		ui = new UI();
		map = new Map();

		Map.randomPlayerSpawn(player);

		bg = new Texture(Gdx.files.internal("repeatingSpace.png"));
		//bg.setSize(bg.getWidth()/Global.PPM,bg.getHeight()/Global.PPM);
		//bg.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
		bg.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
		tRegion = new TextureRegion(bg,0,0,100*400*24,100*400*24);



	}

	@Override
	public void render () {

		//DRAWING SPRITES TO SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1); //refresh screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.setProjectionMatrix(r.cam.combined);
		batch.draw(tRegion,0,0,100*24,100*24);
		mapSprite.draw(batch);
		player.sprite.draw(batch); //draw player
		Enemy.drawAll(batch); //draw enemy
		Projectile.drawAll(batch);
		batch.end();

		//Draw UI
		ui.draw(player);

		//Check for player shooting projectiles
		//TODO: make this more effieient and move it to player class later
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) { //spawn projectile
			//Create new projectile object
			Projectile.shoot(new Texture("player_bullet.png"),5f,Projectile.tag_player,player.getX(),player.getY(),player.getRotation());
		}

		//System.out.println("X: "+player.getX()*Global.PPM+" Y: "+player.getY()*Global.PPM);

		//UPDATE STUFF
		//Update Player
		player.handleInput();
		map.getBounds(player);

		//Update Enemies
		//enemy spawning
		currSector = Map.getSector(player);
		if(((int)currSector.x!=(int)oldSector.x || (int)currSector.y!=(int)oldSector.y || Enemy.enemies.size()==0) && currSector.x!=0 && currSector.y!=0){
			map.generateEnemy(player);

			oldSector = currSector;
			System.out.println("New sector");
		}
		Enemy.updateAll(player);

		//update projectiles
		Projectile.updateAll();

		//Update world and viewport
		Global.world.step(1/60f, 6, 2); //NOTE: GET RID OF HARDCODED VALUES LATER
		AssetLoader.sweepBodies();
		//r.debugCam.render(Global.world,r.cam.combined);
		r.moveCamera(player.sprite.getX(),player.sprite.getY());
		r.cam.update(); //refresh camera
		//r.screenShake(2f);
		Global.updateInput();
	}

	@Override
	public void dispose () {
		batch.dispose();
		bg.dispose();
		try {
			if(Global.currScore>Global.highscore){
				File file = new File("highscore.txt");
				if(file.delete()){System.out.println("highscore change process started");}else{System.out.println("highscore.txt doesnt exist");}
				if(file.createNewFile()){System.out.println("highscore.txt created");}else{System.out.println("highscore.txt already exists");}
				PrintWriter hsWriter = new PrintWriter(new BufferedWriter (new FileWriter ("highscore.txt")));
				Global.highscore = Global.currScore;
				hsWriter.println(Global.currScore);
				hsWriter.close();
			}
		}catch(IOException ioe){
			System.out.println("cannot write to highscore.txt");
		}
	}

}
