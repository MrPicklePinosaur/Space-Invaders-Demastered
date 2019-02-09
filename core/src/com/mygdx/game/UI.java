package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UI {
    static SpriteBatch batch;
    static Texture lives;
    static ShapeRenderer shapeRenderer;
    static float x=60f;
    static BitmapFont sector,highscore;
    public UI(){
        batch = new SpriteBatch();
        lives = new Texture("lifebar.png");
        shapeRenderer = new ShapeRenderer();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gomarice_no_continue.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.borderWidth = 3;
        sector = generator.generateFont(parameter); // font size 12 pixels
        parameter.size = 50;
        highscore = generator.generateFont(parameter);
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
    }
    public static void draw(Player player){
        //Gdx.gl.glClearColor(0, 0, 0, 1); //refresh screen
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(lives,0+10,800-lives.getHeight()-10);
        sector.draw(batch,"Sector: ("+Math.round(Map.getSector(player.getX(),player.getY()).x*100)/100d+", "+Math.round(Map.getSector(player.getX(),player.getY()).y*100)/100d+")",840,780,360,1,false);
        highscore.draw(batch,"HIGHSCORE: "+Global.highscore,Global.SCREEN_WIDTH/2-600,50,1200,1,false);
        //sector.draw(batch,"Sector: (4069.00, 4069.00)",840,780,360,1,false);
        //updateHealth(); --> updateHealth call moved to main
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255,0,0,255);
        //System.out.println(true);
        shapeRenderer.rect(35+10-1, 800-14-17,x, 8);
        shapeRenderer.end();
    }
    public static void updateHealth(Player player){
        //34+60width,13+8height for changeable part
        x = player.getHp();
        //System.out.println(true);
    }
    /*public void dispose(){
        uiBatch.dispose();
    }*/
}
