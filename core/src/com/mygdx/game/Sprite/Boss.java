package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;

public class Boss extends Enemy {

    private static int health = 250;
    private static int def = 2;
    private static int attack = 20;
    private static int crit = 10;
    private static int id = 7;

    public Boss(Texture bitmap, float x, float y, int frameCount, int lines) {
        super(bitmap, x, y, frameCount, lines, health, def, attack, crit, id);
    }
}
