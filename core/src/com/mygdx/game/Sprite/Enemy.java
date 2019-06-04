package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy extends Sprites{
    private Texture bitmap;

    private int frameNr;
    public int currentFrame;
    private long frameTicker;
    private int framePeriod;

     int spriteWidth;
    int spriteHeight;

    public float spriteW, spriteH;



    private int lines;
    public int health, attack;
    int def,crit, size;
    public int id;
    int xTexture, yTexture;

    public Enemy(Texture bitmap, float x, float y, int fps, int frameCount,int lines,int health, int def, int attack, int crit, int id, int size){
        super(bitmap, x, y,  fps, frameCount, lines, size);
        this.bitmap= bitmap;
        //this.x= x;
        // this.y= y;
        this.lines = lines;
        currentFrame=0;
        frameNr= frameCount;
        spriteWidth= bitmap.getWidth()/ frameCount;
        spriteHeight= bitmap.getHeight()/lines;
        framePeriod=1000/ fps;
        frameTicker= 0l;

        spriteW = (1.2f*size);
        spriteH = (size);
        this.size = size;
        this.health = health;
        this.def = def;
        this.attack = attack;
        this.crit = crit;
        this.id = id;
    }
    private int count = 0;
    private void updates(long gameTime){
        if(gameTime> frameTicker+ framePeriod){
            frameTicker = gameTime;
            currentFrame++;
            if(currentFrame>= frameNr){
                currentFrame=0; count++;
                if(count==6&&lines==4){
                    mod =3; count = 0;
                } else if(count ==1 && mod ==3) mod = 0;
            }
        }
        xTexture = currentFrame* spriteWidth;
        yTexture =spriteHeight*(mod);
    }

    public void drawe(SpriteBatch batch){
        batch.draw(bitmap, x, y, spriteW, spriteH, xTexture,yTexture,spriteWidth, spriteHeight, false, false);
        updates(System.currentTimeMillis());

    }


}