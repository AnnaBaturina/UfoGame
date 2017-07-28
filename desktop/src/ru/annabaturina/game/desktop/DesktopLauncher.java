package ru.annabaturina.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.annabaturina.game.UfoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new UfoGame(), config);
		config.title = "UFO Night Fly";
		config.width = 360;
		config.height = 640;
	}
}
