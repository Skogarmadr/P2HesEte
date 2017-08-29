package com.stuffle.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stuffle.Parameter.GameParameter;
import com.stuffle.game.StufflesGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = GameParameter.GAME_TITLE;
		config.width = GameParameter.SCREEN_WIDTH;
		config.height = GameParameter.SCREEN_HEIGHT;
		config.forceExit = true;
		new LwjglApplication(new StufflesGame(), config);
		Monitor currMonitor = Gdx.graphics.getMonitor();
		
        DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
         //if(!Gdx.graphics.setFullscreenMode(displayMode)) {      }
	}
}
