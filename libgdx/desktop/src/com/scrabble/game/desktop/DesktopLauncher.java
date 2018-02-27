package com.scrabble.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.scrabble.game.scrabbleMain;

import screens.ScrabbleLauncher;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.title = "Stone Age Scrabble!";
		config.width = 1280;
		config.height = 720;
		config.addIcon("icon.png", FileType.Internal);
		new LwjglApplication(new ScrabbleLauncher(), config);
	}
}
