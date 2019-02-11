/*
 __  __     __
/\ \/\ \   /\ \
\ \ \_\ \  \ \ \
 \ \_____\  \ \_\
  \/_____/   \/_/
 */

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.security.Key;

public class UI {
    static SpriteBatch batch;   //declaring ui's spritebatch.

    //Declaration Section
    //To see what each texture/font/boolean does, see where it is INTIALIZED
    //(some things are declared AND intialized here, in which case their
    //purpose will be stated there)

    //declares ALL textures used by UI - pls dont dock marks my one brain cell worked very hard on this
    static Texture title;
    static Texture playButton,playButtonHover,playButtonClicked;
    static Texture Help,HelpHover,HelpClicked;
    static Texture HelpScreen,HelpScreenHover,HelpScreenClicked;
    static Texture lives,xp,PauseMenu;
    static Texture StatsMenu;
    static Texture MaxHPHover,MaxHPClicked;
    static Texture DamageHover,DamageClicked;
    static Texture ReloadSpeedHover,ReloadSpeedClicked;
    static Texture ShipSpeedHover,ShipSpeedClicked;
    static Texture TurnSpeedHover,TurnSpeedClicked;
    static Texture ContactDamageHover,ContactDamageClicked;
    static Texture classMenu;
    static Texture gunnerHover,gunnerClicked;
    static Texture shotgunnistHover,shotgunnistClicked;
    static Texture sniperHover,sniperClicked;
    static Texture rammerHover,rammerClicked;
    static Texture BackButtonHover,BackButtonClicked;
    static Texture Exit,ExitHover,ExitClicked;
    static Texture MusicHover,MusicClicked;
    static Texture MusicMuted,MusicMutedHover,MusicMutedClicked;
    static Texture death,again,againHover,againClicked;
    static ShapeRenderer shapeRenderer;
    //declares all fonts used by ui
    static BitmapFont sector,highscore,score;

    //booleans (used as flags)
    static boolean isPaused = false;    //static boolean used to check if the game is paused
    static boolean opening = false;     //static flag for displaying opening page
    static boolean isClassPicked = false;   //boolean that checks if you already have your class chosen at level 5

    static int menuX,menuY,playX,playY; //integers for placing buttons relative to menu and opening page
    static boolean musicPlaying=true;   //you can guess what this is used for
    static boolean HelpScreenOpen = false;  //boolean that checks if the help screen in the opening page is open
    public UI(){
        batch = new SpriteBatch();  //UI has its own spritebatch to make sure the ui stays on screen at all times without worrying about camera/in-world translation
        //Intializes ALL texures used by UI.
        title = new Texture("title.png");   //title for opening page

        /*
        For textures that follow this format when being initalized:
        x = new Texture("x.png");
        xHover = new Texture("xHover.png");
        xClicked = new Texture("xClicked");

        xHover is the texture that replaces x when the player's mouse is hovering over it
        xClicked is the texture that flashes right when the player clicks on the button
        */

        playButton = new Texture("PlayButton.png"); //play button on opening page
        playButtonHover = new Texture("PlayButtonHover.png");
        playButtonClicked = new Texture("PlayButtonClicked.png");
        Help = new Texture("Help.png"); //help button on opening page
        HelpHover = new Texture("HelpHover.png");
        HelpClicked = new Texture("HelpClicked.png");
        HelpScreen = new Texture("HelpScreen.png"); //the splash page that opens up when help button is clicked
        HelpScreenHover = new Texture("HelpScreenHover.png");
        HelpScreenClicked = new Texture("HelpScreenClicked.png");
        lives = new Texture("lifebar.png"); //the life bar in the game ui
        StatsMenu = new Texture("StatsMenu.png");   //the popup that appears when you level up
        MaxHPHover = new Texture("MaxHPHover.png"); //max hp level up button
        MaxHPClicked = new Texture("MaxHPClicked.png");
        DamageHover = new Texture("DamageHover.png");   //damage level up button
        DamageClicked = new Texture("DamageClicked.png");
        ReloadSpeedHover = new Texture("ReloadSpeedHover.png"); //reload speed level up button
        ReloadSpeedClicked = new Texture("ReloadSpeedClicked.png");
        ShipSpeedHover = new Texture("ShipSpeedHover.png"); //ship speed level up button
        ShipSpeedClicked = new Texture("ShipSpeedClicked.png");
        TurnSpeedHover = new Texture("TurnSpeedHover.png"); //turn speed level up button
        TurnSpeedClicked = new Texture("TurnSpeedClicked.png");
        ContactDamageHover = new Texture("ContactDamageHover.png"); //contact damage level up button
        ContactDamageClicked = new Texture("ContactDamageClicked.png");
        classMenu = new Texture("classes.png"); //popup that appears when you reach level 5 to choose your class
        gunnerHover = new Texture("gunnerHover.png");   //choose gunner class button
        gunnerClicked = new Texture("gunnerClicked.png");
        shotgunnistHover = new Texture("shotgunnistHover.png"); //choose shotgunnist class button
        shotgunnistClicked = new Texture("shotgunnistClicked.png");
        sniperHover = new Texture("sniperHover.png");   //choose sniper class button
        sniperClicked = new Texture("sniperClicked.png");
        rammerHover = new Texture("rammerHover.png");   //chose rammer class button
        rammerClicked = new Texture("rammerClicked.png");
        shapeRenderer = new ShapeRenderer();    //used to create the black background for opening page
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gomarice_no_continue.ttf"));//font used for game ui
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.borderWidth = 3;
        sector = generator.generateFont(parameter); // font size 12 pixels  -- used to show the sector the player is in,
                                                    // as well as the current xp in the level the player is in as well
                                                    // as the player's level
        parameter.size = 50;
        highscore = generator.generateFont(parameter);  //font size 50 pixels -- used to show the local highscore
        parameter.size = 30;
        score = generator.generateFont(parameter);  //used to show the score of the current run
        generator.dispose(); // disposing font generator - no longer needed
        xp = new Texture("xp.png"); //visual signifier for current xp
        PauseMenu = new Texture("PauseMenu.png");   //menu that opens when esc is pressed

        BackButtonHover = new Texture("BackButtonHover.png");   //back button in escape menu
        BackButtonClicked = new Texture("BackButtonClicked.png");

        Exit = new Texture("Exit.png"); //exit button used in various menus
        ExitHover = new Texture("ExitHover.png");
        ExitClicked = new Texture("ExitClicked.png");

        MusicHover = new Texture("MusicHover.png"); //music pausing button in escape menu
        MusicClicked = new Texture("MusicClicked.png");
        MusicMuted = new Texture("MusicMuted.png"); //music playing button in escape menu
        MusicMutedHover = new Texture("MusicMutedHover.png");
        MusicMutedClicked = new Texture("MusicMutedClicked.png");

        death = new Texture("death.png");   //death splash screen
        again = new Texture("again.png");   //again button in death scren
        againHover = new Texture("againHover.png");
        againClicked = new Texture("againClicked.png");

        menuX = Global.SCREEN_WIDTH/2-PauseMenu.getWidth()/2;   //relative center of pause menu button
        menuY = Global.SCREEN_HEIGHT/2-PauseMenu.getHeight()/2;
        playX = Global.SCREEN_WIDTH/2-title.getWidth()/2;       //relative center of play menu button
        playY = Global.SCREEN_HEIGHT/2-title.getHeight()/2;
    }
    public static void draw(Player player){ //draws ui that appears when playing the game
        float hpWidth = player.getHp();
        batch.begin();
        batch.draw(lives,10,800-lives.getHeight()-10);
        batch.draw(xp,10,800-lives.getHeight()*2-30);
        sector.draw(batch,"%: "+Math.round(player.getXp()/player.getLvl()*100)/100d,10+xp.getWidth()+10,800-lives.getHeight()*2-5,125,1,false);
        sector.draw(batch,"Level: "+player.getLvl(),10+xp.getWidth()+10,800-lives.getHeight()*2-45,125,1,false);
        sector.draw(batch,"Sector: ("+Math.round(Map.getSector(player).x*100)/100d+", "+Math.round(Map.getSector(player).y*100)/100d+")",840,780,360,1,false);
        highscore.draw(batch,"HIGHSCORE: "+Global.highscore,Global.SCREEN_WIDTH/2-600,50,1200,1,false);
        score.draw(batch,"SCORE: "+Global.currScore,Global.SCREEN_WIDTH/2-600,100,1200,1,false);
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255,0,0,255);
        shapeRenderer.rect(35+10-1, 800-14-17,hpWidth, 8);
        shapeRenderer.end();
    }
    public static void pauseMenu(){ //draws pause menu when game is paused
        batch.begin();
        batch.draw(PauseMenu,menuX,menuY);
        if(!musicPlaying){
            batch.draw(MusicMuted,menuX+52,menuY+110);
        }

        /*
        In this method and the following ones, there is a format that is followed:

        if(players mouse ON THE CAMERA (not the screen dimensions!) is within the the confines of the button):
            draw the button's mouse-over (xHover) version
            if(the left mouse button is pressed):
                draw the button's clicked (xClicked) version
                do whatever clicking that button does

        From now on, only the function of the button will be commented on, to avoid redundancy.
        */

        if(Global.mx>=-75 && Global.mx<=75 && Global.my<=95 && Global.my>=41){
            batch.draw(BackButtonHover,menuX+52,menuY+184);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(BackButtonClicked,menuX+52,menuY+184);
                UI.isPaused = false;    //if the player wants to exit the pause menu, resume the game
            }
        }
        if(Global.mx>=-75 && Global.mx<=75 && Global.my<=22 && Global.my>=-34){
            if(musicPlaying){
                batch.draw(MusicHover,menuX+52,menuY+110);
                if(Gdx.input.justTouched()){
                    batch.draw(MusicClicked,menuX+52,menuY+110);
                    MusicPlayer.toggleMute();   //if the player wants to mute the music, do so
                    musicPlaying = false;
                }
            }else{
                batch.draw(MusicMutedHover,menuX+52,menuY+110);
                if(Gdx.input.justTouched()){
                    batch.draw(MusicMutedClicked,menuX+52,menuY+110);
                    MusicPlayer.toggleMute();   //if the player wants to unmute the music, do so
                    musicPlaying = true;
                }
            }
        }
        if(Global.mx>=-75 && Global.mx<=75 && Global.my<=-48 && Global.my>=-113){
            batch.draw(ExitHover,menuX+52,menuY+36);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ExitClicked,menuX+52,menuY+36);
                Gdx.app.exit(); //if the player wants to quit the game from the pause menu, let them
            }
        }
        batch.end();
    }

    public static void opening(){
        //Drawing the opening page

        //draws background
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,255);
        shapeRenderer.rect(0,0,Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);
        shapeRenderer.end();

        batch.begin();
        batch.draw(title,playX,playY*3/2);  //draws the opening page title
        batch.draw(playButton,getRelCenter(playButton).x,playY);    //draws play button
        batch.draw(Help,getRelCenter(Help).x,playY*3/5);    //draws help button
        batch.draw(Exit,getRelCenter(Exit).x,playY/4);  //draws exit button
        if(Global.mx>=-117 && Global.mx<=118 && Global.my<=24 && Global.my>=-57){
            batch.draw(playButtonHover,getRelCenter(playButton).x,playY);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(playButtonClicked,getRelCenter(playButton).x,playY);
                UI.isPaused = false;
                opening = true; //if the player wants to play the game, let them
            }
        }
        if(Global.mx>=-90 && Global.mx<=90 && Global.my<=-125 && Global.my>=-200){
            batch.draw(HelpHover,getRelCenter(Help).x,playY*3/5);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(HelpClicked,getRelCenter(Help).x,playY*3/5);
                HelpScreenOpen = true;  //if the player wants to view the help page, let them
            }
        }
        if(Global.mx>=-78 && Global.mx<=78 && Global.my<=-250 && Global.my>=-315){
            batch.draw(ExitHover,getRelCenter(Exit).x,playY/4);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ExitClicked,getRelCenter(Exit).x,playY/4);
                Gdx.app.exit(); //if the player wants to close the game from the opening page menu, let them
            }
        }
        if(HelpScreenOpen){ //if the presses the help button
            batch.draw(HelpScreen,getRelCenter(HelpScreen).x,getRelCenter(HelpScreen).y);
            if(Global.mx>=513 && Global.mx<=533 && Global.my<=339 && Global.my>=316){
                batch.draw(HelpScreenHover,1200-37-50,800-35-50);
                if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    batch.draw(HelpScreenClicked,1200-37-50,800-35-50);
                    HelpScreenOpen = false; //if the player wants to close the help page, let them
                }
            }
        }
        batch.end();
    }

    public static void Death(Player player){
        //Death screen
        UI.isPaused = true;

        //draw background
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,255);
        shapeRenderer.rect(0,0,Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);
        shapeRenderer.end();

        batch.begin();
        batch.draw(death,getRelCenter(death).x,getRelCenter(death).y);  //draw 'you are dead' header
        batch.draw(again,getRelCenter(again).x,playY/2);    //draw the replay button
        batch.draw(Exit,getRelCenter(Exit).x,playY/4);  //draw the exit button
        if(Global.mx>=-117 && Global.mx<=115 && Global.my<=-145 && Global.my>=-223){
            batch.draw(againHover,getRelCenter(again).x,playY/2);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(againClicked,getRelCenter(again).x,playY/2);

                //if the player wants to replay, reset the player's stats and let them
                player.addXp(player.getXp()*-1);    //flushes xp
                Global.currScore = 0;   //resets score
                Global.isDead = true;
                Map.randomPlayerSpawn(player);  //choose rand player location
                player.reset(); //reset player stats
                UI.isPaused = false;
            }
        }
        if(Global.mx>=-78 && Global.mx<=78 && Global.my<=-250 && Global.my>=-315){
            batch.draw(ExitHover,Global.SCREEN_WIDTH/2-Exit.getWidth()/2,playY/4);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ExitClicked,getRelCenter(Exit).x,playY/4);
                Gdx.app.exit(); //if player wants to exit game from death screen, let them
            }
        }
        batch.end();
    }

    public static void pickClass(Player player){
        //ui that lets player pick their class at level 5
        batch.begin();
        batch.draw(classMenu,getRelCenter(classMenu).x,getRelCenter(classMenu).y);
        if(Global.mx>=-344 && Global.mx<=-227 && Global.my<=-120 && Global.my>=-145){
            batch.draw(gunnerHover,getRelCenter(classMenu).x+56,800-getRelCenter(classMenu).y-320-gunnerHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(gunnerClicked,getRelCenter(classMenu).x+56,800-getRelCenter(classMenu).y-320-gunnerHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_gunner);
                isClassPicked = true;   //if player picks the double gun class, give them that classes attributes
            }
        }
        if(Global.mx>=-155 && Global.mx<=33 && Global.my<=-120 && Global.my>=-145){
            batch.draw(shotgunnistHover,getRelCenter(classMenu).x+245,800-getRelCenter(classMenu).y-320-shotgunnistHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(shotgunnistClicked,getRelCenter(classMenu).x+245,800-getRelCenter(classMenu).y-320-shotgunnistHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_shotgunist);
                isClassPicked = true;   //if player picks the shotgun class, give them that classes attributes
            }
        }
        if(Global.mx>=91 && Global.mx<=188 && Global.my<=-120 && Global.my>=-145){
            batch.draw(sniperHover,getRelCenter(classMenu).x+491,800-getRelCenter(classMenu).y-320-sniperHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(sniperClicked,getRelCenter(classMenu).x+491,800-getRelCenter(classMenu).y-320-sniperHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_sniper);
                isClassPicked = true;   //if the player picks the sniper class, give them that classes attributes
            }
        }
        if(Global.mx>=231 && Global.mx<=353 && Global.my<=-120 && Global.my>=-145){
            batch.draw(rammerHover,getRelCenter(classMenu).x+631,800-getRelCenter(classMenu).y-320-rammerHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(rammerClicked,getRelCenter(classMenu).x+631,800-getRelCenter(classMenu).y-320-rammerHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_rammer);
                isClassPicked = true;   //if player picks the rammer (run directly into enemies) class,
                                        // give them that classes attributes
            }
        }
        batch.end();
    }

    public static void statMenu(Player player){
        batch.begin();
        /*
        Stats that can change:

        Max hp
        Damage
        Reload Speed
        Ship Speed
        Turn Speed
        Contact Damage
        */
        batch.draw(StatsMenu,getRelCenter(StatsMenu).x,getRelCenter(StatsMenu).y);
        if(Global.mx>=-277 && Global.mx<=-190 && Global.my<=47 && Global.my>=26){
            batch.draw(MaxHPHover,getRelCenter(StatsMenu).x+123,800-getRelCenter(StatsMenu).y-153-MaxHPHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(MaxHPClicked,getRelCenter(StatsMenu).x+123,800-getRelCenter(StatsMenu).y-153-MaxHPHover.getHeight());
                player.choosePoint(1,0,0,0,0,0);
                Global.mustLevelUp = false;
                isPaused = false;   //raise the players maximum health stat
            }
        }
        if(Global.mx>=-90 && Global.mx<=5 && Global.my<=47 && Global.my>=26){
            batch.draw(DamageHover,getRelCenter(StatsMenu).x+310,800-getRelCenter(StatsMenu).y-153-DamageHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(DamageClicked,getRelCenter(StatsMenu).x+310,800-getRelCenter(StatsMenu).y-153-DamageHover.getHeight());
                player.choosePoint(0,1,0,0,0,0);
                Global.mustLevelUp = false;
                isPaused = false;   //raise the players damage per shot stat
            }
        }
        if(Global.mx>=106 && Global.mx<=277 && Global.my<=47 && Global.my>=26){
            batch.draw(ReloadSpeedHover,getRelCenter(StatsMenu).x+506,800-getRelCenter(StatsMenu).y-153-ReloadSpeedHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ReloadSpeedClicked,getRelCenter(StatsMenu).x+506,800-getRelCenter(StatsMenu).y-153-ReloadSpeedHover.getHeight());
                player.choosePoint(0,0,1,0,0,0);
                Global.mustLevelUp = false;
                isPaused = false;   //increase the player's shot speed
            }
        }
        if(Global.mx>=-296 && Global.mx<=-162 && Global.my<=-94 && Global.my>=-116){
            batch.draw(ShipSpeedHover,getRelCenter(StatsMenu).x+104,800-getRelCenter(StatsMenu).y-294-ShipSpeedHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ShipSpeedClicked,getRelCenter(StatsMenu).x+104,800-getRelCenter(StatsMenu).y-294-ShipSpeedHover.getHeight());
                player.choosePoint(0,0,0,1,0,0);
                Global.mustLevelUp = false;
                isPaused = false;   //increase the player's ship speed
            }
        }
        if(Global.mx>=-107 && Global.mx<=34 && Global.my<=-94 && Global.my>=-116){
            batch.draw(TurnSpeedHover,getRelCenter(StatsMenu).x+293,800-getRelCenter(StatsMenu).y-294-TurnSpeedHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(TurnSpeedClicked,getRelCenter(StatsMenu).x+293,800-getRelCenter(StatsMenu).y-294-TurnSpeedHover.getHeight());
                player.choosePoint(0,0,0,0,1,0);
                Global.mustLevelUp = false;
                isPaused = false;   //increase the player's turn speed
            }
        }
        if(Global.mx>=88 && Global.mx<=294 && Global.my<=-94 && Global.my>=-116){
            batch.draw(ContactDamageHover,getRelCenter(StatsMenu).x+488,800-getRelCenter(StatsMenu).y-294-ContactDamageHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ContactDamageClicked,getRelCenter(StatsMenu).x+488,800-getRelCenter(StatsMenu).y-294-ContactDamageHover.getHeight());
                player.choosePoint(0,0,0,0,0,1);
                Global.mustLevelUp = false;
                isPaused = false;   //increase the amount of damage the player does when ramming into enemies
            }
        }

        batch.end();
    }

    //method for getting center of the screen for a texture relative to the window
    public static Vector2 getRelCenter(Texture t){
        return new Vector2(Global.SCREEN_WIDTH/2-t.getWidth()/2,Global.SCREEN_HEIGHT/2-t.getHeight()/2);
    }

    //pause state getter
    public static boolean isPaused(){return UI.isPaused;}

    //disposes ALL textures
    public static void disposeAll(){
        batch.dispose();
        title.dispose();
        playButton.dispose();
        playButtonHover.dispose();
        playButtonClicked.dispose();
        Help.dispose();
        HelpHover.dispose();
        HelpClicked.dispose();
        HelpScreen.dispose();
        HelpScreenHover.dispose();
        HelpScreenClicked.dispose();
        lives.dispose();
        xp.dispose();
        PauseMenu.dispose();
        BackButtonHover.dispose();
        BackButtonClicked.dispose();
        Exit.dispose();
        ExitHover.dispose();
        ExitClicked.dispose();
        MusicHover.dispose();
        MusicClicked.dispose();
        MusicMuted.dispose();
        MusicMutedHover.dispose();
        MusicMutedClicked.dispose();
        shapeRenderer.dispose();
        sector.dispose();
        highscore.dispose();
        score.dispose();
        death.dispose();
        again.dispose();
        againHover.dispose();
        againClicked.dispose();
        classMenu.dispose();
        gunnerHover.dispose();
        gunnerClicked.dispose();
        shotgunnistHover.dispose();
        shotgunnistClicked.dispose();
        sniperHover.dispose();
        sniperClicked.dispose();
        rammerHover.dispose();
        rammerClicked.dispose();
        StatsMenu.dispose();
        MaxHPHover.dispose();
        MaxHPClicked.dispose();
        DamageHover.dispose();
        DamageClicked.dispose();
        ReloadSpeedHover.dispose();
        ReloadSpeedClicked.dispose();
        ShipSpeedHover.dispose();
        ShipSpeedClicked.dispose();
        TurnSpeedHover.dispose();
        TurnSpeedClicked.dispose();
        ContactDamageHover.dispose();
        ContactDamageClicked.dispose();
    }
}
