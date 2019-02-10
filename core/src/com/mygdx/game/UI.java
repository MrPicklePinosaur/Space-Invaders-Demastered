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
    static SpriteBatch batch;
    static Texture title;
    static Texture playButton,playButtonHover,playButtonClicked;
    static Texture Help,HelpHover,HelpClicked;
    static Texture HelpScreen,HelpScreenHover,HelpScreenClicked;
    static Texture lives,xp,PauseMenu;
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
    static BitmapFont sector,highscore,score;
    static boolean isPaused = false;
    static boolean opening = false;
    static boolean isClassPicked = false;
    static int menuX,menuY,playX,playY;
    static boolean musicPlaying=true;
    static boolean HelpScreenOpen = false;
    public UI(){
        batch = new SpriteBatch();
        title = new Texture("title.png");
        playButton = new Texture("PlayButton.png");
        playButtonHover = new Texture("PlayButtonHover.png");
        playButtonClicked = new Texture("PlayButtonClicked.png");
        Help = new Texture("Help.png");
        HelpHover = new Texture("HelpHover.png");
        HelpClicked = new Texture("HelpClicked.png");
        HelpScreen = new Texture("HelpScreen.png");
        HelpScreenHover = new Texture("HelpScreenHover.png");
        HelpScreenClicked = new Texture("HelpScreenClicked.png");
        lives = new Texture("lifebar.png");
        classMenu = new Texture("classes.png");
        gunnerHover = new Texture("gunnerHover.png");
        gunnerClicked = new Texture("gunnerClicked.png");
        shotgunnistHover = new Texture("shotgunnistHover.png");
        shotgunnistClicked = new Texture("shotgunnistClicked.png");
        sniperHover = new Texture("sniperHover.png");
        sniperClicked = new Texture("sniperClicked.png");
        rammerHover = new Texture("rammerHover.png");
        rammerClicked = new Texture("rammerClicked.png");
        shapeRenderer = new ShapeRenderer();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gomarice_no_continue.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.borderWidth = 3;
        sector = generator.generateFont(parameter); // font size 12 pixels
        parameter.size = 50;
        highscore = generator.generateFont(parameter);
        parameter.size = 30;
        score = generator.generateFont(parameter);
        generator.dispose(); // disposing generator - no longer needed
        /*
        POSSIBLE USEFUL PARAMETERS FOR FreeTypeFontParameter, WITH THEIR DEFAULTS:

        The size in pixels
        public int size = 16;

        Foreground color (required for non-black borders)
        public Color color = Color.WHITE;

        Border width in pixels, 0 to disable
        public float borderWidth = 0;

        Border color; only used if borderWidth > 0
        public Color borderColor = Color.BLACK;

        true for straight (mitered), false for rounded borders
        public boolean borderStraight = false;

        Offset of text shadow on X axis in pixels, 0 to disable
        public int shadowOffsetX = 0;

        Offset of text shadow on Y axis in pixels, 0 to disable
        public int shadowOffsetY = 0;

        Shadow color; only used if shadowOffset > 0
        public Color shadowColor = new Color(0, 0, 0, 0.75f);
        */
        xp = new Texture("xp.png");
        PauseMenu = new Texture("PauseMenu.png");

        BackButtonHover = new Texture("BackButtonHover.png");
        BackButtonClicked = new Texture("BackButtonClicked.png");

        Exit = new Texture("Exit.png");
        ExitHover = new Texture("ExitHover.png");
        ExitClicked = new Texture("ExitClicked.png");

        MusicHover = new Texture("MusicHover.png");
        MusicClicked = new Texture("MusicClicked.png");
        MusicMuted = new Texture("MusicMuted.png");
        MusicMutedHover = new Texture("MusicMutedHover.png");
        MusicMutedClicked = new Texture("MusicMutedClicked.png");

        death = new Texture("death.png");
        again = new Texture("again.png");
        againHover = new Texture("againHover.png");
        againClicked = new Texture("againClicked.png");

        menuX = Global.SCREEN_WIDTH/2-PauseMenu.getWidth()/2;
        menuY = Global.SCREEN_HEIGHT/2-PauseMenu.getHeight()/2;
        playX = Global.SCREEN_WIDTH/2-title.getWidth()/2;
        playY = Global.SCREEN_HEIGHT/2-title.getHeight()/2;
    }
    public static void draw(Player player){
        //Gdx.gl.glClearColor(0, 0, 0, 1); //refresh screen
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float hpWidth = player.getHp();
        batch.begin();
        batch.draw(lives,10,800-lives.getHeight()-10);
        batch.draw(xp,10,800-lives.getHeight()*2-30);
        sector.draw(batch,"%: "+Math.round(player.getXp()/player.getLvl()*100)/100d,10+xp.getWidth()+10,800-lives.getHeight()*2-5,125,1,false);
        sector.draw(batch,"Level: "+player.getLvl(),10+xp.getWidth()+10,800-lives.getHeight()*2-45,125,1,false);
        sector.draw(batch,"Sector: ("+Math.round(Map.getSector(player).x*100)/100d+", "+Math.round(Map.getSector(player).y*100)/100d+")",840,780,360,1,false);
        highscore.draw(batch,"HIGHSCORE: "+Global.highscore,Global.SCREEN_WIDTH/2-600,50,1200,1,false);
        score.draw(batch,"SCORE: "+Global.currScore,Global.SCREEN_WIDTH/2-600,100,1200,1,false);
        //sector.draw(batch,"Sector: (4069.00, 4069.00)",840,780,360,1,false);
        //updateHealth(); --> updateHealth call moved to main
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255,0,0,255);
        //System.out.println(true);
        shapeRenderer.rect(35+10-1, 800-14-17,hpWidth, 8);
        shapeRenderer.end();
    }
    public static void pause(){
        //Global.delta = 0f;
        UI.isPaused = true;
    }
    public static void pauseMenu(){
        batch.begin();
        batch.draw(PauseMenu,menuX,menuY);
        if(!musicPlaying){
            batch.draw(MusicMuted,menuX+52,menuY+110);
        }
        //Back button before top left: 56x53   Back button wxh: 150x59
        //System.out.println(Global.mx+" "+Global.my);
        //if(Global.mx>(menuX+56) && Global.mx<(menuX+207) && Global.my<(menuY+(Global.SCREEN_HEIGHT-53)) && Global.my>(menuY+(Global.SCREEN_HEIGHT-53-59))){
        if(Global.mx>=-75 && Global.mx<=75 && Global.my<=95 && Global.my>=41){   //-75,97
            batch.draw(BackButtonHover,menuX+52,menuY+184);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(BackButtonClicked,menuX+52,menuY+184);
                UI.isPaused = false;
            }
            //System.out.println(true);
        }
        if(Global.mx>=-75 && Global.mx<=75 && Global.my<=22 && Global.my>=-34){//-75,22
            if(musicPlaying){
                batch.draw(MusicHover,menuX+52,menuY+110);
                if(Gdx.input.justTouched()){
                    batch.draw(MusicClicked,menuX+52,menuY+110);
                    musicPlaying = false;
                }
            }else{
                batch.draw(MusicMutedHover,menuX+52,menuY+110);
                if(Gdx.input.justTouched()){
                    batch.draw(MusicMutedClicked,menuX+52,menuY+110);
                    musicPlaying = true;
                }
            }
        }
        if(Global.mx>=-75 && Global.mx<=75 && Global.my<=-48 && Global.my>=-113){   //-75,97
            batch.draw(ExitHover,menuX+52,menuY+36);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ExitClicked,menuX+52,menuY+36);
                Gdx.app.exit();
            }
            //System.out.println(true);
        }
        batch.end();
    }

    public static void opening(){
        //opening scene
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,255);
        shapeRenderer.rect(0,0,Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);
        shapeRenderer.end();
        batch.begin();
        batch.draw(title,playX,playY*3/2);
        batch.draw(playButton,getRelCenter(playButton).x,playY);
        batch.draw(Help,getRelCenter(Help).x,playY*3/5);
        batch.draw(Exit,getRelCenter(Exit).x,playY/4);
        System.out.println(Global.mx+" "+Global.my);
        //(-117, 24) to (115,-56)
        if(Global.mx>=-117 && Global.mx<=118 && Global.my<=24 && Global.my>=-57){
            batch.draw(playButtonHover,getRelCenter(playButton).x,playY);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(playButtonClicked,getRelCenter(playButton).x,playY);
                UI.isPaused = false;
                opening = true;
            }
            //System.out.println(true);
        }
        if(Global.mx>=-90 && Global.mx<=90 && Global.my<=-125 && Global.my>=-200){
            batch.draw(HelpHover,getRelCenter(Help).x,playY*3/5);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(HelpClicked,getRelCenter(Help).x,playY*3/5);
                HelpScreenOpen = true;
            }
        }
        if(Global.mx>=-78 && Global.mx<=78 && Global.my<=-250 && Global.my>=-315){
            batch.draw(ExitHover,getRelCenter(Exit).x,playY/4);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ExitClicked,getRelCenter(Exit).x,playY/4);
                Gdx.app.exit();
            }
        }
        if(HelpScreenOpen){
            batch.draw(HelpScreen,getRelCenter(HelpScreen).x,getRelCenter(HelpScreen).y);
            if(Global.mx>=513 && Global.mx<=533 && Global.my<=339 && Global.my>=316){
                batch.draw(HelpScreenHover,1200-37-50,800-35-50);
                if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    batch.draw(HelpScreenClicked,1200-37-50,800-35-50);
                    HelpScreenOpen = false;
                }
            }
        }
        batch.end();
        //isPaused = false;
        //opening = true;
    }

    public static void Death(Player player){
        //Death screen
        UI.isPaused = true;
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,255);
        shapeRenderer.rect(0,0,Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);
        shapeRenderer.end();
        batch.begin();
        batch.draw(death,getRelCenter(death).x,getRelCenter(death).y);
        batch.draw(again,getRelCenter(again).x,playY/2);
        batch.draw(Exit,getRelCenter(Exit).x,playY/4);
        //System.out.println(Global.mx+" "+Global.my);
        //(-117, -145) to (115,223)
        if(Global.mx>=-117 && Global.mx<=115 && Global.my<=-145 && Global.my>=-223){   //-75,97
            batch.draw(againHover,getRelCenter(again).x,playY/2);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(againClicked,getRelCenter(again).x,playY/2);
                player.addXp(player.getXp()*-1);    //flushes xp
                Global.currScore = 0;
                Global.isDead = true;
                Map.randomPlayerSpawn(player);
                //AssetLoader.switchClasses(player,AssetLoader.class_base);
                player.reset();
                UI.isPaused = false;
            }
            //System.out.println(true);
        }
        if(Global.mx>=-78 && Global.mx<=78 && Global.my<=-250 && Global.my>=-315){   //-75,97
            batch.draw(ExitHover,Global.SCREEN_WIDTH/2-Exit.getWidth()/2,playY/4);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ExitClicked,getRelCenter(Exit).x,playY/4);
                Gdx.app.exit();
            }
        }
        batch.end();
    }

    public static void pickClass(Player player){
        batch.begin();
        batch.draw(classMenu,getRelCenter(classMenu).x,getRelCenter(classMenu).y);
        //System.out.println(Global.mx+" "+Global.my);
        if(Global.mx>=-344 && Global.mx<=-227 && Global.my<=-120 && Global.my>=-145){   //-344,-120
            batch.draw(gunnerHover,getRelCenter(classMenu).x+56,800-getRelCenter(classMenu).y-320-gunnerHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(gunnerClicked,getRelCenter(classMenu).x+56,800-getRelCenter(classMenu).y-320-gunnerHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_gunner);
                isClassPicked = true;
                isPaused = false;
            }
        }
        if(Global.mx>=-155 && Global.mx<=33 && Global.my<=-120 && Global.my>=-145){   //-75,97
            batch.draw(shotgunnistHover,getRelCenter(classMenu).x+245,800-getRelCenter(classMenu).y-320-shotgunnistHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(shotgunnistClicked,getRelCenter(classMenu).x+245,800-getRelCenter(classMenu).y-320-shotgunnistHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_shotgunist);
                isClassPicked = true;
                isPaused = false;
            }
        }
        if(Global.mx>=91 && Global.mx<=188 && Global.my<=-120 && Global.my>=-145){   //-75,97
            batch.draw(sniperHover,getRelCenter(classMenu).x+491,800-getRelCenter(classMenu).y-320-sniperHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(sniperClicked,getRelCenter(classMenu).x+491,800-getRelCenter(classMenu).y-320-sniperHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_sniper);
                isClassPicked = true;
                isPaused = false;
            }
        }
        if(Global.mx>=231 && Global.mx<=353 && Global.my<=-120 && Global.my>=-145){   //-75,97
            batch.draw(rammerHover,getRelCenter(classMenu).x+631,800-getRelCenter(classMenu).y-320-rammerHover.getHeight());
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(rammerClicked,getRelCenter(classMenu).x+631,800-getRelCenter(classMenu).y-320-rammerHover.getHeight());
                AssetLoader.switchClasses(player,AssetLoader.class_rammer);
                isClassPicked = true;
                isPaused = false;
            }
        }
        batch.end();
    }

    public static Vector2 getRelCenter(Texture t){
        return new Vector2(Global.SCREEN_WIDTH/2-t.getWidth()/2,Global.SCREEN_HEIGHT/2-t.getHeight()/2);
    }

    //pause state getter
    public static boolean isPaused(){return UI.isPaused;}

    /*public static void opening(){
        batch.begin();
        batch.draw(title,Global.SCREEN_WIDTH/2-title.getWidth()/2,Global.SCREEN_HEIGHT/2-title.getHeight()/2);
        batch.end();
    }*/

    /*public void dispose(){
        uiBatch.dispose();
    }*/
}
