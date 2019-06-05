package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wall {
    public int x1,  y1, x2,  y2;
    private Texture wall;
    Wall(int x1, int y1, int x2, int y2, Texture wall){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.wall = wall;
    }
    void drawWall(SpriteBatch batch){
        batch.draw(wall,x1, y1, x2-x1, y2-y1,1, 1, wall.getWidth()/24,wall.getHeight()/24, false, false);
    }

}