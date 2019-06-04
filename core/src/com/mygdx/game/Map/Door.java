package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door {
    private int idOfRoom;
    private Texture image;
    public int size;
    public int x,y;

    public Door(int idOfRoom, Texture image,int x, int y, int size ){
        this.x = x;
        this.y = y;
        this.idOfRoom = idOfRoom;
        this.image = image;
        this.size = size;
    }

    public void drawObject(SpriteBatch batch){

        batch.draw(image, x, y,size,size, 0,0, image.getWidth(),image.getHeight(), false,false);

    }


}