package ru.annabaturina.game.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;


public class Meteor extends Moving {


    private Random r;
    private  float groundY;
    private Circle meteorC;

    public Meteor(float x, float y, int width, int height, float movSpeed, float groundY) {
        super(x, y, width, height, movSpeed);
        r = new Random();
        this.groundY = groundY;
        meteorC = new Circle();

    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        position.y=r.nextInt(50);
    }

    public boolean collides(Saucer saucer){
        if (position.x < saucer.getX() + saucer.getWidth()){
            return (Intersector.overlaps(saucer.getCircle(), meteorC));
        }
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        meteorC.set(position.x+4, position.y+9, 5);
    }

    public void onRestart(int x, int movSpeed) {
            velocity.x = movSpeed;
            reset(x);

    }
}