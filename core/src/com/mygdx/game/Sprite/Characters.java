package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;

 public class Characters extends Sprites{
    public int health = 1000,  attack = 5, def = 5;


   public Characters(Texture bitmap, float x, float y, int fps, int frameCount, int lines,  int size) {
        super(bitmap, x, y,  fps, frameCount, lines, size);

    }

    public void stop(int v){

        mod = v;
    }


}