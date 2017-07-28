package ru.annabaturina.game.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by AB
 */

public class ResourceLoader {

    public static final String KEY_HIGH_SCORE = "highScore";
    private static TextureAtlas atlas;
    public static Sprite logo, flyAndSpiders, background, grass, fly1, fly2, fly3, spider, webUp,
            webDown, playButtonUp, playButtonDown, ready, retry, gameOver, scoreBoard, starOn, starOff,
            highScore, sparklingstar1, sparklingstar2, sparklingstar3, meteorite;
    public static Animation flyAnimation, stars1Animation, stars2Animation;
    public static Sound flap, fall, dead, coin;
    public static Music fly;
    public static BitmapFont font, font2, font3, shadow;


    private static Preferences preferences;


    public static void load() {

        //текстуры
        atlas = new TextureAtlas(Gdx.files.internal("texture/texture17.pack"), true);

        //изображения
        logo = new Sprite(atlas.findRegion("logo"));
        logo.flip(false, true); //перевернуть


        playButtonUp = new Sprite(atlas.findRegion("buttonOff"));
        playButtonDown = new Sprite(atlas.findRegion("buttonOn"));
        ready = new Sprite(atlas.findRegion("tapToFly"));
        retry = new Sprite(atlas.findRegion("retry"));
        gameOver = new Sprite(atlas.findRegion("gameOver"));
        scoreBoard = new Sprite(atlas.findRegion("wood"));
        starOn = new Sprite(atlas.findRegion("starOn"));
        starOff = new Sprite(atlas.findRegion("starOff"));
        highScore = new Sprite(atlas.findRegion("highScore"));
        background = new Sprite(atlas.findRegion("background"));
        grass = new Sprite(atlas.findRegion("grass"));
        fly1 = new Sprite(atlas.findRegion("fly1"));
        fly2 = new Sprite(atlas.findRegion("fly2"));
        fly3 = new Sprite(atlas.findRegion("fly3"));
        spider = new Sprite(atlas.findRegion("spider"));
        webUp = new Sprite(atlas.findRegion("webUp"));
        webDown = new Sprite(atlas.findRegion("webDown"));
        meteorite = new Sprite(atlas.findRegion("meteor"));
        sparklingstar1 = new Sprite(atlas.findRegion("sparklingstar1"));
        sparklingstar2 = new Sprite(atlas.findRegion("sparklingstar2"));
        sparklingstar3 = new Sprite(atlas.findRegion("sparklingstar3"));

        //анимация тарелочки
        TextureRegion[]fly = {fly1, fly2};
        flyAnimation = new Animation(0.3f, fly);
        flyAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //анимация звездочек
        TextureRegion[]stars1 = {sparklingstar3, sparklingstar2};
        stars1Animation = new Animation(1.5f, stars1);
        stars1Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[]stars2 = {sparklingstar2, sparklingstar3};
        stars2Animation = new Animation(3f, stars2);
        stars2Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //звуки
        dead = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("sounds/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
        fall = Gdx.audio.newSound(Gdx.files.internal("sounds/fall.wav"));

        //фоновая музыка
        ResourceLoader.fly = Gdx.audio.newMusic(Gdx.files.internal("sounds/fly.wav"));

        //шрифты
        font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        font2 = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        font2.getData().setScale(.12f, -.12f);
        font3 = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        font3.getData().setScale(.15f, -.15f);


        preferences = Gdx.app.getPreferences("FlappyUfo");

        if (!preferences.contains(KEY_HIGH_SCORE)){
            preferences.putInteger(KEY_HIGH_SCORE, 0);
        }

    }


    public  static void setHightScore(int val){
        preferences.putInteger(KEY_HIGH_SCORE,val);
        preferences.flush();
    }


    public static int getHighScore() {
        return preferences.getInteger(KEY_HIGH_SCORE);
    }


    public static  void dispose(){
        atlas.dispose();

        dead.dispose();
        flap.dispose();
        coin.dispose();
        fly.dispose();

        font.dispose();
        font2.dispose();
        font3.dispose();


    }

}
