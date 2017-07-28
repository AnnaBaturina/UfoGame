package ru.annabaturina.game.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import ru.annabaturina.game.loader.ResourceLoader;
import ru.annabaturina.game.objects.MoveHandler;
import ru.annabaturina.game.objects.Saucer;


public class GameWorld {

    private Saucer saucer;
    private MoveHandler moveHandler;
    private Rectangle ground;

    private float runTime = 0;

    private int midPointY;
    private int midPintX;

    private int score = 0;

    private GameRender renderer;

    private GameState currentState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public GameWorld(int midPointY, int midPointX) {

        currentState = GameState.MENU;

        this.midPintX = midPointX;
        this.midPointY = midPointY;

        saucer = new Saucer(33, midPointY - 5, 17, 12);
        moveHandler = new MoveHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 137, 11);

    }


    public void update(float delta) {
        runTime += delta;

        switch (currentState){
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;

        }

    }


    private void updateReady(float delta){
        saucer.updateReay(runTime);
        moveHandler.updateReady(delta);
    }


    public void updateRunning(float delta) {

        if (delta > 0.15f) {
            delta = 0.15f;
        }

        saucer.update(delta);
        moveHandler.update(delta);

        if (moveHandler.collides(saucer) && saucer.isAlive()) {
            moveHandler.stop();
            saucer.die();
            saucer.cling();
            ResourceLoader.fall.play();
            renderer.prepareTransition(255, 255, 255, 0.3f);
            currentState = GameState.GAMEOVER;

            if (score > ResourceLoader.getHighScore()) {
                ResourceLoader.setHightScore(score);
                currentState = GameState.HIGHSCORE;
            }

        }
        if (Intersector.overlaps(saucer.getCircle(), ground)) {
            if (saucer.isAlive()) {
                ResourceLoader.dead.play();
                saucer.die();
                renderer.prepareTransition(255, 255, 255, 0.3f);
            }
            moveHandler.stop();
            saucer.cling();
            currentState = GameState.GAMEOVER;

            if (score > ResourceLoader.getHighScore()) {
                ResourceLoader.setHightScore(score);
                currentState = GameState.HIGHSCORE;
            }

        }

    }

    public MoveHandler getMoveHandler() {
        return moveHandler;
    }

    public Saucer getSaucer() {
        return saucer;
    }


    public void setRenderer(GameRender renderer) {
        this.renderer = renderer;
    }


    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }


    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void start() {
        currentState = GameState.RUNNING;
    }


    public void restart() {
        score = 0;
        saucer.onRestart(midPointY - 5);
        moveHandler.onRestart();
        ready();
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }


    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }


    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public int getMidPointY() {
        return midPointY;
    }

    public int getMidPintX() {
        return midPintX;
    }



}
