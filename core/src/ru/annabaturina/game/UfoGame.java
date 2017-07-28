package ru.annabaturina.game;

import com.badlogic.gdx.Game;

import ru.annabaturina.game.loader.ResourceLoader;
import ru.annabaturina.game.screens.SplashScreen;


public class UfoGame extends Game {


    @Override
    public void create() {
        ResourceLoader.load();
        setScreen(new SplashScreen(this));

    }

    public void dispose() {
        super.dispose();
        ResourceLoader.dispose();
    }

}
