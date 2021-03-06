package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;

public class Dino2 extends Enemy {

    private static int health = 100;
    private static int def = 2;
    private static int attack = 10;
    private static int crit = 10;
    private static int id = 2;

    public Dino2(Texture bitmap, float x, float y, int frameCount, int lines) {
        super(bitmap, x, y, frameCount, lines, health, def, attack, crit, id);
    }
}
