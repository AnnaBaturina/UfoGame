package ru.annabaturina.game.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ru.annabaturina.game.loader.ResourceLoader;
import ru.annabaturina.game.objects.Grass;
import ru.annabaturina.game.objects.MoveHandler;
import ru.annabaturina.game.objects.Saucer;
import ru.annabaturina.game.objects.Meteor;
import ru.annabaturina.game.objects.Star;
import ru.annabaturina.game.objects.Web;
import ru.annabaturina.game.tools.Value;
import ru.annabaturina.game.tools.ValueAccessor;
import ru.annabaturina.game.ui.InputHandler;
import ru.annabaturina.game.ui.PlayButton;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;

public class GameRender {


    public static final int VERT_GAP = 60;
    public static final int STARS_COUNT1 = 80;
    public static final int STARS_COUNT2 = 20;

    public static final String RETRY = "RETRY?";
    public static final String READY= "TAP TO FLY";
    public static final String START= "START";
    public static final String GAME_OVER = "GAME OVER";
    public static final String HIGHSCORE = "HIGHSCORE";
    public static final String PROGRESS = "PROGRESS";

    private int midPointY;
    private int midPointX;

    private GameWorld mWorld;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batch;

    private Saucer mySaucer;
    private MoveHandler moveHandler;
    private Grass frontGrass, backGrass;
    private Web web1, web2, web3;
    private Meteor meteor;
    private Star[] myStars;
    private Star[] myStars2;
    private Star[] myStars3;

    private Sprite background, grass, saucerMid, spider, webUp, webDown, ready, flyLogo, gameOver, ufoGameLogo,
    highScore, scoreboard, starOn, starOff, retry, meteorite, sparkingstar1, sparkingstar2, sparkingstar3;
    private Animation flyAnimation, stars1Animation, stars2Animation;
    private Music music;


    private TweenManager manager;
    private Value alpha = new Value();
    private Color transitionColor;

    private ArrayList<PlayButton> menuButtons;


    public static BitmapFont fntDrummon;



    public GameRender(GameWorld world, int gameHeight, int midPointY, int midPointX) {
        mWorld = world;

        this.midPointY = midPointY;
        this.midPointX = midPointX;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();


        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, gameHeight);


        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);


        myStars = new Star[STARS_COUNT1];
        for (int i = 0; i < STARS_COUNT1; i++) {
            myStars[i] = new Star();
        }

        myStars2 = new Star[STARS_COUNT1];
        for (int i = 0; i < STARS_COUNT1; i++) {
            myStars2[i] = new Star();
        }

        myStars3 = new Star[STARS_COUNT1];
        for (int i = 0; i < STARS_COUNT1; i++) {
            myStars3[i] = new Star();
        }



        initGameObjects();
        initAssets();

        transitionColor = new Color();
        prepareTransition(255, 255, 255, 0.3f);

    }

    public void render(float delta, float runTime) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //фон
        shapeRenderer.setColor(20 / 255.0f, 50 / 255.0f, 122 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        //вода
        shapeRenderer.setColor(5 / 255.0f, 21 / 255.0f, 59/ 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 53);


        //статичные звезды
        shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 255/ 255.0f, 0.5f);
        for (int i = 0; i < STARS_COUNT1; i++) {
            shapeRenderer.circle(myStars[i].getX(), myStars[i].getY(), 0.3f);
        }

        //трава
        shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 58, 136, 20);




        shapeRenderer.end();

        batch.begin();
//        batch.disableBlending();

        drawStars2(runTime);
        drawStars3(runTime);

        batch.draw(background, 0, midPointY + 23, 136, 43);

//        batch.enableBlending();


        drawGrass();
        drawWebs();
        drawMeteor();
        drawSpiders();


        if (mWorld.isRunning()) {
            drawFly(runTime);
            drawScore();
        }
        if (mWorld.isReady()) {
            drawFly(runTime);
            drawReady();
        }
        if (mWorld.isMenu()) {
            drawSaucerCentered(runTime);
            drawMenuUI();
        } else if (mWorld.isGameOver()) {
            drawScoreBoard();
            drawFly(runTime);
            drawGameOver();
            drawRetry();
        } else if (mWorld.isHighScore()) {
            drawScoreBoard();
            drawFly(runTime);
            drawHighScore();
            drawRetry();
        }

        batch.end();
        drawTransition(delta);

        if(mySaucer.isAlive()){
            music.setVolume(0.1f);
            music.play();
            music.isLooping();
        } else {
            music.stop();
        }


    }


    private void initAssets() {
        background = ResourceLoader.background;
        grass = ResourceLoader.grass;
        flyAnimation = ResourceLoader.flyAnimation;
        stars1Animation = ResourceLoader.stars1Animation;
        stars2Animation = ResourceLoader.stars2Animation;
        saucerMid = ResourceLoader.fly3;
        spider = ResourceLoader.spider;
        webUp = ResourceLoader.webUp;
        webDown = ResourceLoader.webDown;
        ready = ResourceLoader.ready;
        flyLogo = ResourceLoader.logo;
        gameOver = ResourceLoader.gameOver;
        highScore = ResourceLoader.highScore;
        scoreboard = ResourceLoader.scoreBoard;
        retry = ResourceLoader.retry;
        starOn = ResourceLoader.starOn;
        starOff = ResourceLoader.starOff;
        music = ResourceLoader.fly;
        ufoGameLogo =ResourceLoader.flyAndSpiders;
        //
        meteorite = ResourceLoader.meteorite;
        sparkingstar1 = ResourceLoader.sparklingstar1;
        sparkingstar2 = ResourceLoader.sparklingstar2;
        sparkingstar3 = ResourceLoader.sparklingstar3;


    }


    private void initGameObjects() {
        mySaucer = mWorld.getSaucer();
        moveHandler = mWorld.getMoveHandler();
        frontGrass = moveHandler.getFrontGrass();
        backGrass = moveHandler.getBackGrass();
        web1 = moveHandler.getWeb1();
        web2 = moveHandler.getWeb2();
        web3 = moveHandler.getWeb3();

        meteor = moveHandler.getMeteor();
    }


    private void drawGrass() {
        batch.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batch.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }


    private void drawWebs() {
        batch.draw(webUp, web1.getX(), web1.getY(), web1.getWidth(), web1.getHeight());
        batch.draw(webDown, web1.getX(), web1.getY() + web1.getHeight() + VERT_GAP,
                web1.getWidth(), midPointY + 66 - (web1.getHeight() + VERT_GAP));

        batch.draw(webUp, web2.getX(), web2.getY(), web2.getWidth(), web2.getHeight());
        batch.draw(webDown, web2.getX(), web2.getY() + web2.getHeight() + VERT_GAP,
                web2.getWidth(), midPointY + 66 - (web2.getHeight() + VERT_GAP));

        batch.draw(webUp, web3.getX(), web3.getY(), web3.getWidth(), web3.getHeight());
        batch.draw(webDown, web3.getX(), web3.getY() + web3.getHeight() + VERT_GAP,
                web3.getWidth(), midPointY + 66 - (web3.getHeight() + VERT_GAP));


    }


    public void drawSpiders() {

        batch.draw(spider, web1.getX(), web1.getY() + web1.getHeight() - 5, 24, 24);
        batch.draw(spider, web2.getX(), web2.getY() + web2.getHeight() - 5, 24, 24);
        batch.draw(spider, web3.getX(), web3.getY() + web3.getHeight() - 5, 24, 24);

    }


    private void drawFly(float runTime) {

        if (mySaucer.notFlap()) {
            batch.draw(saucerMid, mySaucer.getX(), mySaucer.getY(),
                    mySaucer.getWidth() / 2.0f, mySaucer.getHeight() / 2.0f,
                    mySaucer.getWidth(), mySaucer.getHeight(), 1, 1, mySaucer.getRotation());
        } else {
            //каст текстуры
            batch.draw((TextureRegion) flyAnimation.getKeyFrame(runTime),
                    mySaucer.getX(), mySaucer.getY(), mySaucer.getWidth() / 2.0f,
                    mySaucer.getHeight() / 2.0f, mySaucer.getWidth(), mySaucer.getHeight(),
                    1, 1, mySaucer.getRotation());
        }

    }


    private void drawMeteor() {
        for (int i = 0; i < 50; i++) {
            batch.draw(meteorite, meteor.getX(), meteor.getY(), meteor.getWidth(), meteor.getHeight());
        }
    }



    private void drawStars2(float runTime) {
        for (int i = 0; i < STARS_COUNT2; i++) {
            batch.draw((TextureRegion) stars2Animation.getKeyFrame(runTime), myStars2[i].getX(), myStars2[i].getY(), 5f, 0.5f);
        }
    }


    private void drawStars3(float runTime) {
        for (int i = 0; i < STARS_COUNT2; i++) {
            batch.draw((TextureRegion) stars1Animation.getKeyFrame(runTime), myStars3[i].getX(), myStars3[i].getY(), 5f, 0.5f);
        }
    }


    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setVal(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }


    private void drawTransition(float delta) {
        if (alpha.getVal() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g, transitionColor.b, alpha.getVal());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    private void drawMenuUI() {

        ResourceLoader.font.draw(batch, START, midPointX-25, midPointY -55);
        for (PlayButton button : menuButtons) {
            button.draw(batch);
        }

    }

    private void drawScoreBoard() {
        batch.draw(scoreboard, 20, midPointY - 35, 101, 45);

        batch.draw(starOff, 25, midPointY - 15, 10, 10);
        batch.draw(starOff, 37, midPointY - 15, 10, 10);
        batch.draw(starOff, 49, midPointY - 15, 10, 10);
        batch.draw(starOff, 61, midPointY - 15, 10, 10);
        batch.draw(starOff, 73, midPointY - 15, 10, 10);

        if (mWorld.getScore() >= 5) {
            batch.draw(starOn, 25, midPointY - 15, 10, 10);
        }

        if (mWorld.getScore() >= 20) {
            batch.draw(starOn, 37, midPointY - 15, 10, 10);
        }

        if (mWorld.getScore() >= 50) {
            batch.draw(starOn, 49, midPointY - 15, 10, 10);
        }

        if (mWorld.getScore() >= 100) {
            batch.draw(starOn, 61, midPointY - 15, 10, 10);
        }

        if (mWorld.getScore() >= 250) {
            batch.draw(starOn, 73, midPointY - 15, 10, 10);
        }

        ResourceLoader.font3.draw(batch, PROGRESS,
                29, midPointY - 25);


        ResourceLoader.font2.draw(batch, "SCORE\n" + mWorld.getScore(),
                90, midPointY - 30);

        ResourceLoader.font2.draw(batch, "BEST\n" + ResourceLoader.getHighScore(),
                90, midPointY - 10);



    }


    private void drawRetry() {
//        batch.draw(retry, 36, midPointY + 10, 66, 14);
        int length = (RETRY.length());
        ResourceLoader.font.draw(batch, RETRY, midPointX-length*4.5f, midPointY + 15);
    }

    private void drawReady() {
        int length = (READY.length());
        ResourceLoader.font3.draw(batch, READY, midPointX-length*3f, midPointY - 50);
    }

    private void drawGameOver() {
        batch.draw(gameOver, 24, midPointY - 55, 92, 14);
    }


    private void drawScore() {
        int length = ("" + mWorld.getScore()).length();
        ResourceLoader.font.draw(batch, "" + mWorld.getScore(), 68 - (3 * length), midPointY - 80);

    }

    private void drawHighScore() {
        batch.draw(highScore, 22, midPointY - 50, 96, 14);
    }


    private void drawSaucerCentered(float runTime) {
        batch.draw((TextureRegion) flyAnimation.getKeyFrame(runTime), 59, mySaucer.getY() - 15,
                mySaucer.getWidth() / 2.0f, mySaucer.getHeight() / 2.0f,
                mySaucer.getWidth(), mySaucer.getHeight(), 1, 1, mySaucer.getRotation());
    }

}
