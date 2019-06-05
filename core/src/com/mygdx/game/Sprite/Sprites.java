package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Sprites {
    private Texture texture;
    private int frameNr;
    public int currentFrame;
    private long frameTicker;
    private int framePeriod;

    private int spriteWidth;
    private int spriteHeight;

    public float spriteW, spriteH;

    public float x,y;

    private int xTexture, yTexture;


    public int mod;


    Sprites(Texture texture, float x, float y, int fps, int frameCount, int lines, int size){
        this.texture= texture;

        currentFrame=0;
        frameNr= frameCount;
        spriteWidth= texture.getWidth()/ frameCount;
        spriteHeight= texture.getHeight()/lines;
        this.x= x;
        this.y= y;
        spriteW = (float) (3*(size))/4;
        spriteH = (float) 6*(size)/4;
        framePeriod=1000/fps;
        frameTicker= 1;
        mod = 0;

    }


    private void update(long gameTime){
        if(gameTime> frameTicker+ framePeriod){
            frameTicker = gameTime;
            currentFrame++;
            if(currentFrame>= frameNr){
                currentFrame=0;
            }
        }
        xTexture = currentFrame* spriteWidth;
        yTexture =spriteHeight*(mod);

    }

    public void draw(SpriteBatch batch){

        batch.draw(texture, x, y, spriteW, spriteH, xTexture,yTexture,spriteWidth, spriteHeight, false, false);

        update(System.currentTimeMillis());
    }
}