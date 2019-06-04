package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Sprites {
    private Texture texture;
  //  private Rect sourceRect;
    private int frameNr;
    public int currentFrame;
    private long frameTicker;
    private int framePeriod;

    public int spriteWidth;
    public int spriteHeight;

    public float spriteW, spriteH;

    public float x,y;

    int xTexture, yTexture;


    public int mod; int size;


    Sprites(Texture texture, float x, float y, int fps, int frameCount, int lines, int size){
        this.texture= texture;

        currentFrame=0;
        frameNr= frameCount;
        spriteWidth= texture.getWidth()/ frameCount;
        spriteHeight= texture.getHeight()/lines;
        this.x= x;//-(float)spriteWidth/2;
        this.y= y;//-(float)spriteHeight/2;
       // sourceRect=new Rect(0,0, spriteWidth, spriteHeight);
        spriteW = (float) (3*(size))/4;
        spriteH = (float) 6*(size)/4;
        framePeriod=1000/ fps;
        frameTicker= 01;
        mod = 0;
        this.size = size;

    }


    void update(long gameTime){
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