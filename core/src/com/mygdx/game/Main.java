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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.io.*;
import java.util.Scanner;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Renderer r;
	Player player;
	Vector2 oldSector,currSector;
	Map map;
	UI ui;
	Texture bg; TextureRegion tRegion;

	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT); //change window resolution
		Global.world = new World(new Vector2(0,0),true);
		Global.world.setContactListener(new CollisionListener());
		r = new Renderer(batch);
		Global.r = r;

		batch = new SpriteBatch();

		try{ //read save file
			Scanner inFile = new Scanner(new BufferedReader(new FileReader("highscore.txt")));
			int highscore;
			while(inFile.hasNextInt()){
				highscore = inFile.nextInt();
				Global.highscore = highscore;
			}
			inFile.close();
		}catch(IOException ioe){System.out.println("highscore.txt does not exist");}

		//Create Player
		player = AssetLoader.create_player(AssetLoader.class_base); //create player object
		player.choosePoint(0,0,0,0,0,0);
		oldSector = Map.getSector(player);
		currSector = new Vector2(-1,-1);
		//mapSprite = new Sprite(new Texture("space.png"),Map.DIVISION_SIZE*24/Global.PPM,Map.DIVISION_SIZE*24/Global.PPM);
		ui = new UI();
		UI.isPaused = true;
		map = new Map();

		Map.randomPlayerSpawn(player);

		bg = new Texture(Gdx.files.internal("repeatingSpace.png"));
		bg.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
		tRegion = new TextureRegion(bg,0,0,100*400*24,100*400*24);
	}

	@Override
	public void render () {
		if (UI.opening == false) { MusicPlayer.setSong(MusicPlayer.music_title); }
		if (UI.opening == true) { MusicPlayer.setSong(MusicPlayer.music_battle); }

		//DRAWING SPRITES TO SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1); //refresh screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.setProjectionMatrix(Global.r.cam.combined);
		batch.draw(tRegion,0,0,100*24,100*24);
		//mapSprite.draw(batch);
		player.sprite.draw(batch); //draw player
		Enemy.drawAll(batch); //draw enemy
		Projectile.drawAll(batch);
		batch.end();

		//Draw UI for the game
		ui.draw(player);

		if(UI.isPaused()){	//if the game needs to be paused for any reason, handle whatever needs to be done
			if(!UI.opening){
				UI.opening();	//if the opening screen hasn't been ran yet (at start of game), do that
			}
			else{
				if(player.getHp()>0){	//if player is alive
					if(Global.mustLevelUp==false){
						//we only let camera move during this method
						//because it looks cool
						//but in the other places where we could, it would be really distracting
						//as in those places the choices made by the player are important
						//this is just an aesthetic decision
						UI.pauseMenu();
						Global.r.moveCamera(player);
						Global.r.cam.update();
					}
					if(Global.mustLevelUp==true && (player.getLvl()!=5 || UI.isClassPicked==true)) {
						UI.statMenu(player);    //if the player needs to level up
												//(while they don't have to worry about choosing their class)
												// bring up the level up stat screen
					}
					if(Global.mustLevelUp==true && player.getLvl()==5 && UI.isClassPicked==false){
						//if the player needs to pick their class, then they must have just reached level 5
						//therefore, let the pick their class BEFORE they choose the stat they want to level up,
						//as the stats they choose to improve will depend on the class they choose
						UI.pickClass(player);
						if(UI.isClassPicked==true){
							UI.isPaused = true;
							UI.statMenu(player);
						}
					}
				}else{	//if player isn't alive, run the death screen
					UI.Death(player);
				}
			}
			Global.updateInput();
		}else {	//run the game

			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				UI.isPaused = true;	//if the player wants to pause the game, let them
			}

			//UPDATE STUFF
			//Update Player
			player.handleInput();
			player.regen();

			//Update Enemies -- enemy spawning
			currSector = Map.getSector(player);
			if (Enemy.enemies.size() == 0 && Map.getDifficulty((int)currSector.x,(int)currSector.y) !=0 ) {
				map.generateEnemy(player);
				oldSector = currSector;
			}
			Enemy.updateAll(player);
			if(player.getHp()<=0){
				UI.isPaused = true;	//if the player's health falls below 0, call the death menu by pausing the game
			}

			//update projectiles
			Projectile.updateAll();

			//Update world and viewport
			Global.world.step(Global.delta, 6, 2); //NOTE: GET RID OF HARDCODED VALUES LATER
			AssetLoader.sweepBodies(); //c;eam up all the dead bodies
			//Global.r.debugCam.render(Global.world,Global.r.cam.combined);
			Global.r.moveCamera(player); //camera follows player
			Global.r.cam.update(); //refresh camera
			Global.updateInput();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		bg.dispose();
		UI.disposeAll();	//disposes all ui textures
		try {
			if(Global.currScore>Global.highscore){	//if the player broke the local highscore record, replace highscore
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
