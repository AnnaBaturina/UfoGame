package ru.annabaturina.game.objects;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Web extends Moving {

    public static final int GAP = 60;

    private Random r;
    private  float groundY;
    private Rectangle webDownR;
    private Circle spiderC;
//    private float rotationS;

    private boolean isScored = false;

    public Web(float x, float y, int width, int height, float movSpeed, float groundY) {
        super(x, y, width, height, movSpeed);
        r = new Random();
        spiderC = new Circle();
        webDownR = new Rectangle();
        this.groundY = groundY;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        webDownR.set(position.x+3, position.y + height + GAP + 11, width-9, groundY -(position.y + height+GAP));
        spiderC.set(position.x - (4-width)/2 +2, position.y + height + 7 , 9);
//        rotationS += 30 * delta;
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height=r.nextInt(50) + 10;
        isScored = false;
    }

    public boolean collides(Saucer saucer){
        if (position.x < saucer.getX() + saucer.getWidth()){
            return (Intersector.overlaps(saucer.getCircle(), webDownR))
                    || Intersector.overlaps(saucer.getCircle(), spiderC);
        }
        return false;
    }



    public boolean isScored(){
        return isScored;
    }

    public void  setScored(boolean b){
        isScored=b;
    }


    public void onRestart(float x, float movSpeed ) {
        velocity.x = movSpeed;
        reset(x);

    }

//    public float getRotationS() {
//        return rotationS;
//    }
}
