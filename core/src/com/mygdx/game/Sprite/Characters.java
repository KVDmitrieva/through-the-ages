package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.game.Tools.Constants.maxPlayerHealth;

public class Characters extends Sprites {

    public int health = maxPlayerHealth, attack = 5, def = 5;


    public Characters(Texture bitmap, float x, float y, int frameCount, int lines) {
        super(bitmap, x, y, frameCount, lines);

    }

    public void stop(int v) {
        mod = v;
    }


}