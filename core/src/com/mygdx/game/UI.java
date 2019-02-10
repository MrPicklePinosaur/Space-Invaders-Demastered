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

import java.security.Key;

public class UI {
    static SpriteBatch batch;
    static Texture title;
    static Texture playButton,playButtonHover,playButtonClicked;
    static Texture lives,PauseMenu;
    static Texture BackButtonHover,BackButtonClicked;
    static Texture Exit,ExitHover,ExitClicked;
    static Texture MusicHover,MusicClicked;
    static Texture MusicMuted,MusicMutedHover,MusicMutedClicked;
    static ShapeRenderer shapeRenderer;
    static float x=60f;
    static BitmapFont sector,highscore,score;
    static boolean isPaused = false;
    static boolean opening = false;
    static int menuX,menuY,playX,playY;
    static boolean musicPlaying=true;
    public UI(){
        batch = new SpriteBatch();
        title = new Texture("title.png");
        playButton = new Texture("PlayButton.png");
        playButtonHover = new Texture("PlayButtonHover.png");
        playButtonClicked = new Texture("PlayButtonClicked.png");
        lives = new Texture("lifebar.png");
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

        menuX = Global.SCREEN_WIDTH/2-PauseMenu.getWidth()/2;
        menuY = Global.SCREEN_HEIGHT/2-PauseMenu.getHeight()/2;
        playX = Global.SCREEN_WIDTH/2-title.getWidth()/2;
        playY = Global.SCREEN_HEIGHT/2-title.getHeight()/2;
    }
    public static void draw(Player player){
        //Gdx.gl.glClearColor(0, 0, 0, 1); //refresh screen
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(lives,0+10,800-lives.getHeight()-10);
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
        if(x>0) {
            shapeRenderer.rect(35 + 10 - 1, 800 - 14 - 17, x, 8);
        }
        shapeRenderer.end();
    }
    public static void updateHealth(Player player){
        //34+60width,13+8height for changeable part
        x = player.getHp();
        //System.out.println(true);
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

    public static void pauseMenu(int n){
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,255);
        shapeRenderer.rect(0,0,Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);
        shapeRenderer.end();
        batch.begin();
        batch.draw(title,playX,playY);
        batch.draw(playButton,Global.SCREEN_WIDTH/2-playButton.getWidth()/2,playY/2);
        batch.draw(Exit,Global.SCREEN_WIDTH/2-Exit.getWidth()/2,playY/4);
        //System.out.println(Global.mx+" "+Global.my);
        //(-117, -145) to (115,223)
        if(Global.mx>=-117 && Global.mx<=115 && Global.my<=-145 && Global.my>=-223){   //-75,97
            batch.draw(playButtonHover,Global.SCREEN_WIDTH/2-playButton.getWidth()/2,playY/2);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(playButtonClicked,Global.SCREEN_WIDTH/2-playButton.getWidth()/2,playY/2);
                UI.isPaused = false;
                opening = true;
            }
            //System.out.println(true);
        }
        if(Global.mx>=-78 && Global.mx<=78 && Global.my<=-250 && Global.my>=-315){   //-75,97
            batch.draw(ExitHover,Global.SCREEN_WIDTH/2-Exit.getWidth()/2,playY/4);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                batch.draw(ExitClicked,Global.SCREEN_WIDTH/2-Exit.getWidth()/2,playY/4);
                Gdx.app.exit();
            }
        }
        batch.end();
        //isPaused = false;
        //opening = true;
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
