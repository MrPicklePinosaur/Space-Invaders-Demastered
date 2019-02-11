package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.util.Random;
import com.mygdx.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Random rand = new Random();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		//Pick a random MOTD
		String[] motd = {"Hardcoded Hell","Created in 48 hours","Demastered - Demastered","The Phantom Menace","pew pew",
				"notavirus.exe 97% downloaded","Now with 100% more game","Created with Lego NXT","dYNaMiC PRoGraMiNG"};
		config.title = "Space Invaders Demastered - "+motd[rand.nextInt(motd.length)];
		new LwjglApplication(new Main(), config);
	}
}
