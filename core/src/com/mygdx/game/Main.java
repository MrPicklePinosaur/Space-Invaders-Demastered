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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
		Global.r = r;
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
		player = AssetLoader.create_player(AssetLoader.class_base); //create player object
		System.out.println(player.getLvl());
		player.choosePoint(0,0,0,0,0,0);
		//player.addXp(0);
		oldSector = Map.getSector(player);
		currSector = new Vector2(-1,-1);
		mapSprite = new Sprite(new Texture("space.png"),Map.DIVISION_SIZE*24/Global.PPM,Map.DIVISION_SIZE*24/Global.PPM);
		ui = new UI();
		UI.isPaused = true;
		//UI.opening();
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

		//Is the game paused?
		//System.out.println(player.getLvl());

		//DRAWING SPRITES TO SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1); //refre\h screen
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

		if(UI.isPaused()){
			if(!UI.opening){
				UI.opening();
			}
			else{
				if(player.getHp()>0){
					if(UI.isClassPicked || player.getLvl()!=5  || Global.mustLevelUp) {
						if(Global.mustLevelUp){
							UI.statMenu(player);
						}else {
							UI.pauseMenu();
							r.moveCamera(player);
							r.cam.update(); //refresh camera
						}
					}else{
						UI.pickClass(player);
						UI.statMenu(player);
					}
				}else{
					UI.Death(player);
				}
			}
			Global.updateInput();
		}else {

			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				UI.pause();
			}

			//UPDATE STUFF
			//Update Player
			player.handleInput();
			map.getBounds(player);
			player.regen();
			if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
				player.modHp(-1000);	//suicide button
			}

			//Update Enemies
			//enemy spawning
			currSector = Map.getSector(player);
			if (((int) currSector.x != (int) oldSector.x || (int) currSector.y != (int) oldSector.y || Enemy.enemies.size() == 0) && currSector.x != 0 && currSector.y != 0) {
				map.generateEnemy(player);

				oldSector = currSector;
				System.out.println("New sector");
			}
			Enemy.updateAll(player);
			if(player.getHp()<=0 || (player.getLvl()==5 && UI.isClassPicked==false)){
				UI.isPaused = true;
			}

			//update projectiles
			Projectile.updateAll();

			//Update world and viewport
			Global.world.step(Global.delta, 6, 2); //NOTE: GET RID OF HARDCODED VALUES LATER
			AssetLoader.sweepBodies();
			//r.debugCam.render(Global.world,r.cam.combined);
			r.moveCamera(player);
			r.cam.update(); //refresh camera
			//r.screenShake(2f);
			Global.updateInput();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		bg.dispose();
		UI.batch.dispose();
		UI.title.dispose();
		UI.playButton.dispose();
		UI.playButtonHover.dispose();
		UI.playButtonClicked.dispose();
		UI.Help.dispose();
		UI.HelpHover.dispose();
		UI.HelpClicked.dispose();
		UI.HelpScreen.dispose();
		UI.HelpScreenHover.dispose();
		UI.HelpScreenClicked.dispose();
		UI.lives.dispose();
		UI.xp.dispose();
		UI.PauseMenu.dispose();
		UI.BackButtonHover.dispose();
		UI.BackButtonClicked.dispose();
		UI.Exit.dispose();
		UI.ExitHover.dispose();
		UI.ExitClicked.dispose();
		UI.MusicHover.dispose();
		UI.MusicClicked.dispose();
		UI.MusicMuted.dispose();
		UI.MusicMutedHover.dispose();
		UI.MusicMutedClicked.dispose();
		UI.shapeRenderer.dispose();
		UI.sector.dispose();
		UI.highscore.dispose();
		UI.score.dispose();
		UI.death.dispose();
		UI.again.dispose();
		UI.againHover.dispose();
		UI.againClicked.dispose();
		UI.classMenu.dispose();
		UI.gunnerHover.dispose();
		UI.gunnerClicked.dispose();
		UI.shotgunnistHover.dispose();
		UI.shotgunnistClicked.dispose();
		UI.sniperHover.dispose();
		UI.sniperClicked.dispose();
		UI.rammerHover.dispose();
		UI.rammerClicked.dispose();
		UI.StatsMenu.dispose();
		UI.MaxHPHover.dispose();
		UI.MaxHPClicked.dispose();
		UI.DamageHover.dispose();
		UI.DamageClicked.dispose();
		UI.ReloadSpeedHover.dispose();
		UI.ReloadSpeedClicked.dispose();
		UI.ShipSpeedHover.dispose();
		UI.ShipSpeedClicked.dispose();
		UI.TurnSpeedHover.dispose();
		UI.TurnSpeedClicked.dispose();
		UI.ContactDamageHover.dispose();
		UI.ContactDamageClicked.dispose();
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
