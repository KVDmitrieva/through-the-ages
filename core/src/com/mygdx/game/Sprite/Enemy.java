package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.Screen.GameScreen.LEVEL;

public class Enemy extends Sprites{
    private Texture bitmap;

    private int frameNr;
    public int currentFrame;
    private long frameTicker;
    private int framePeriod;
    private int spriteWidth;
    private int spriteHeight;

    public float spriteW, spriteH;



    private int lines;
    public int health, attack;
    private int def,crit;
    public int id;
    private int xTexture, yTexture;

    public Enemy(Texture bitmap, float x, float y, int fps, int frameCount,int lines,int health, int def, int attack, int crit, int id, int size){
        super(bitmap, x, y,  fps, frameCount, lines, size);
        this.bitmap= bitmap;
        this.lines = lines;
        currentFrame=0;
        frameNr= frameCount;
        spriteWidth= bitmap.getWidth()/ frameCount;
        spriteHeight= bitmap.getHeight()/lines;
        framePeriod=1000/ fps;
        frameTicker= 1;
        if(id == 7){
            spriteW = (3.2f*size);
            spriteH = (3*size);
        }else{
        spriteW = (1.2f*size);
        spriteH = (size);
        }
        this.health = health + LEVEL*5;
        this.def = def;
        this.attack = attack + LEVEL*2;
        this.crit = crit;
        this.id = id;
    }
    private int count = 0;


    private void update(long gameTime){
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
        update(System.currentTimeMillis());

    }


}