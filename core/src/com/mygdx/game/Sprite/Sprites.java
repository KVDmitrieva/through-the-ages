package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Tools.Constants;

import static com.mygdx.game.Tools.Constants.SIZE;

class Sprites {

    private Texture texture; //image
    private int frameNr; //frames in line
    public int currentFrame; //current frame
    private long frameTicker; //time to update last frame
    private int framePeriod; //time for changing frames

    private int spriteWidth; //width of frame in texture
    private int spriteHeight; //height of frame in texture

    public float spriteW, spriteH; //real size in game

    public float x, y; //coordinates in game

    private int xTexture, yTexture; //coordinates in texture

    public int mod; //current line with animation
    private int count = 0, lines;


    Sprites(Texture texture, float x, float y, int frameCount, int lines) {
        this.texture = texture;

        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = texture.getWidth() / frameCount;
        spriteHeight = texture.getHeight() / lines;
        this.x = x;
        this.y = y;
        spriteW = (float) (3 * (SIZE)) / 4;
        spriteH = (float) 6 * (SIZE) / 4;
        framePeriod = Constants.framePeriod;
        frameTicker = 1;
        mod = 0;
        this.lines = lines;

    }


    private void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
                count++;
                if (count == 6 && lines == 4) {
                    mod = 3;
                    count = 0;
                } else if (count == 1 && mod == 3) mod = 0;
            }
        }
        xTexture = currentFrame * spriteWidth;
        yTexture = spriteHeight * (mod);
    }

    public void draw(SpriteBatch batch) {

        batch.draw(texture, x, y, spriteW, spriteH, xTexture, yTexture, spriteWidth, spriteHeight, false, false);
        update(System.currentTimeMillis());
    }
}