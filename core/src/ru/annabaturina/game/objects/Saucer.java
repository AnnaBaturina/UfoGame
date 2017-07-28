package ru.annabaturina.game.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import ru.annabaturina.game.loader.ResourceLoader;

public class Saucer {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private float height;
    private float originalY;


    private Circle circle;
    public boolean isAlive;

    public Saucer(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.originalY = y;

        circle = new Circle();
        isAlive = true;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 300);


    }


    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean notFlap() {
        return velocity.y > 70 || !isAlive;

    }


    public float getRotation() {
        return rotation;
    }

    public int getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }


    public void onClick() {
        if (isAlive) {
            velocity.y = -140;
            ResourceLoader.flap.play();
        }
    }


    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }


        if (position.y < 0){
            position.y = 0;
            velocity.y=0;
        }

        position.add(velocity.cpy().scl(delta));
        circle.set(position.x + 9, position.y + 6, 6.0f);

        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -25) {
                rotation = -25;
            }
        }

        if (isFalling()) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }
        }


        //самодеятельность
        if (position.y > 220) {
            position.y = 220;
        }

    }


    public Circle getCircle() {
        return circle;
    }

    public void die(){
        isAlive = false;
        velocity.y = 0;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void cling(){
acceleration.y = 0;
    }


    public void onRestart(int y) {
        rotation =0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 300;
        isAlive = true;
    }

    public void updateReay(float runTime) {

        position.y = 2 * (float) Math.sin(7*runTime) + originalY;
    }
}
