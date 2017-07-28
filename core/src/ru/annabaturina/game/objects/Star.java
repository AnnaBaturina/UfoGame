package ru.annabaturina.game.objects;

import com.badlogic.gdx.math.Vector2;


public class Star {

    private Vector2 position;

    public Star() {
        position = new Vector2((float) Math.random() * 136, (float) Math.random() * 300);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

}
