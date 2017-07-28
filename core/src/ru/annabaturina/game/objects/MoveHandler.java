package ru.annabaturina.game.objects;


import ru.annabaturina.game.game.GameWorld;
import ru.annabaturina.game.loader.ResourceLoader;

public class MoveHandler {

    public static final int MOV_SPEED_METEOR = -75;
    private Grass frontGrass, backGrass;
    private Web web1, web2, web3;
    private Meteor meteor;

    public static final int MOV_SPEED = -45;
    public static final int WEB_GAP = 50;


    private GameWorld gameWorld;

    public MoveHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontGrass = new Grass(0, yPos -5 , 143, 17, MOV_SPEED - 5);
        backGrass = new Grass(frontGrass.getTailX(), yPos -5, 143, 17, MOV_SPEED - 5);

        web1 = new Web(210, 0, 22, 60, MOV_SPEED, yPos+1);
        web2 = new Web(web1.getTailX() + WEB_GAP, 0, 22, 70, MOV_SPEED, yPos+1);
        web3 = new Web(web2.getTailX() + WEB_GAP, 0, 22, 60, MOV_SPEED, yPos+1);


        meteor = new Meteor(1000, -100, 20, 20, MOV_SPEED_METEOR, yPos);


    }


    public void update(float delta) {

        frontGrass.update(delta);
        backGrass.update(delta);
        web1.update(delta);
        web2.update(delta);
        web3.update(delta);
        meteor.update(delta);

        if (web1.isScrolledLeft()) {
            web1.reset(web3.getTailX() + WEB_GAP);
        } else if (web2.isScrolledLeft()) {
            web2.reset(web1.getTailX() + WEB_GAP);
        } else if (web3.isScrolledLeft()) {
            web3.reset(web2.getTailX() + WEB_GAP);

        }
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());
        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }


        if (meteor.isScrolledLeft()) {
            meteor.reset(meteor.getTailX() + 272);
        }

    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Web getWeb1() {
        return web1;
    }

    public Meteor getMeteor() {
        return meteor;
    }

    public Web getWeb2() {
        return web2;
    }

    public Web getWeb3() {
        return web3;
    }


    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        web1.stop();
        web2.stop();
        web3.stop();
        meteor.stop();

    }


    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }


    public boolean collides(Saucer saucer) {

        if (!web1.isScored() && web1.getX() + (web1.getWidth() / 2) < saucer.getWidth() + saucer.getWidth()) {
            addScore(1);
            web1.setScored(true);
            ResourceLoader.coin.play();
        } else if (!web2.isScored() && web2.getX() + (web2.getWidth() / 2) < saucer.getWidth() + saucer.getWidth()) {
            addScore(1);
            web2.setScored(true);
            ResourceLoader.coin.play();
        } else if (!web3.isScored() && web3.getX() + (web3.getWidth() / 2) < saucer.getWidth() + saucer.getWidth()) {
            addScore(1);
            web3.setScored(true);
            ResourceLoader.coin.play();
        }

        return (web1.collides(saucer) || web2.collides(saucer) || web3.collides(saucer) || meteor.collides(saucer));

    }

    public void onRestart() {
        frontGrass.onRestart(0, MOV_SPEED - 5);
      backGrass.onRestart(frontGrass.getTailX(), MOV_SPEED -5);
        web1.onRestart(210, MOV_SPEED);
        web2.onRestart(web1.getTailX() + WEB_GAP, MOV_SPEED);
        web3.onRestart(web2.getTailX() + WEB_GAP, MOV_SPEED);
        meteor.onRestart(1000, MOV_SPEED_METEOR);

    }

    public void updateReady(float delta) {

        frontGrass.update(delta);
        backGrass.update(delta);

        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());
        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }

    }
}
