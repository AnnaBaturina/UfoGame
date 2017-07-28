package ru.annabaturina.game.ui;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.util.ArrayList;

import ru.annabaturina.game.game.GameWorld;
import ru.annabaturina.game.loader.ResourceLoader;
import ru.annabaturina.game.objects.Saucer;

public class InputHandler implements InputProcessor {

    private Saucer mySaucer;

    private ArrayList<PlayButton> menuButtons;
    private PlayButton playButton;

    private GameWorld myWorld;
    private float scaleFactorX;
    private float scaleFactorY;


    public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
        this.myWorld = myWorld;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        mySaucer = myWorld.getSaucer();

        int midPointX = myWorld.getMidPintX();
        int midPointY = myWorld.getMidPointY();


        menuButtons = new ArrayList<PlayButton>();
        playButton = new PlayButton(midPointX - 14.5f,
                midPointY + 10, 29, 29, ResourceLoader.playButtonUp, ResourceLoader.playButtonDown);
        menuButtons.add(playButton);

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);


        if (myWorld.isMenu()){
            playButton.isTouchDown(screenX, screenY);
        } else if (myWorld.isReady()){
            myWorld.start();
            mySaucer.onClick();
        } else if (myWorld.isRunning()){
            mySaucer.onClick();
        }


        if(myWorld.isGameOver() || myWorld.isHighScore()){
            myWorld.restart();
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);


        if (myWorld.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                myWorld.ready();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private  int scaleX(int screenX){
        return (int) (screenX / scaleFactorX);
    }

    private  int scaleY(int screenY){
        return (int) (screenY / scaleFactorY);
    }

    public ArrayList<PlayButton> getMenuButtons() {
        return menuButtons;
    }
}
