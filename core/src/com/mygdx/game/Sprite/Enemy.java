package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Tools.Constants;

import static com.mygdx.game.Tools.Constants.LEVEL;
import static com.mygdx.game.Tools.Constants.SIZE;
import static com.mygdx.game.Tools.Constants.scaleWidthOfBoss;
import static com.mygdx.game.Tools.Constants.scaleWidthOfEnemy;

public class Enemy extends Sprites {
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
    private int def, crit;
    public int id;
    private int xTexture, yTexture;

    public Enemy(Texture bitmap, float x, float y, int frameCount, int lines, int health, int def, int attack, int crit, int id) {
        super(bitmap, x, y, frameCount, lines);
        this.bitmap = bitmap;
        this.lines = lines;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight() / lines;
        framePeriod = Constants.framePeriod;
        frameTicker = 1;
        if (id == 7) {
            spriteW = (scaleWidthOfBoss * SIZE);
            spriteH = (3 * SIZE);
        } else {
            spriteW = (scaleWidthOfEnemy * SIZE);
            spriteH = (SIZE);
        }
        this.health = health + LEVEL * 5;
        this.def = def;
        this.attack = attack + LEVEL * 3;
        this.crit = crit;
        this.id = id;
    }

    private int count = 0;


    private void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
                count++;
                if (count == 5 && lines == 4) {
                    mod = 3;
                    count = 0;
                } else if (count == 1 && mod == 3) {
                    mod = 0;
                    count = 0;
                }
            }
        }
        xTexture = currentFrame * spriteWidth;
        yTexture = spriteHeight * (mod);
    }

    public void drawe(SpriteBatch batch) {
        batch.draw(bitmap, x, y, spriteW, spriteH, xTexture, yTexture, spriteWidth, spriteHeight, false, false);
        update(System.currentTimeMillis());

    }


}