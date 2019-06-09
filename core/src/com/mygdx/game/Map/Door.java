package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door {

    private Texture image; //image of the door
    public int size; //size of the door
    public int x, y; //coordinates of the door

    public Door(Texture image, int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.size = size;
    }

    public void drawDoor(SpriteBatch batch) {
        batch.draw(image, x, y, size, size, 0, 0, image.getWidth(), image.getHeight(), false, false);

    }


}