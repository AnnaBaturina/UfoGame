package ru.annabaturina.game;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


import ru.annabaturina.game.UfoGame;

public class AndroidLauncher extends AndroidApplication {


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new UfoGame(), config);
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;



	}
}
