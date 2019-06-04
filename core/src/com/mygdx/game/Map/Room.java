package com.mygdx.game.Map;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.Enemy;

import java.util.ArrayList;


public class Room {
    public int x, y, width, height;
    boolean[] idOfHalls = new boolean[4];
    ArrayList<Hall> room_hall = new ArrayList<Hall>();
    int numberOfHalls;
    boolean hallCreated = false;
    Texture bitmap, wall;
    public int size;
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    //walls cor
    private int xl0, yl0, xr0, yr0, xl1, yl1, xr1, yr1,
            xl2, yl2, xr2, yr2, xl3, yl3, xr3, yr3;
    private int hxl0 = 0, hyl0 = 0, hxr0 = 0, hyr0 = 0, hxl1 = 0, hyl1 = 0, hxr1 = 0, hyr1 = 0,
            hxl2 = 0, hyl2 = 0, hxr2 = 0, hyr2 = 0, hxl3 = 0, hyl3 = 0, hxr3 = 0, hyr3 = 0;


    Room(int x, int y, int width, int height, int size, Texture image, Texture wall) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        for (int i = 0; i < 4; i++) {
            idOfHalls[i] = true;
        }
        bitmap = image;
        this.wall = wall;
        this.size = size;
    }

    int numberOfHalls() {
        return (int) (Math.random() * 100) % 3;
    }

    boolean intersect(Room r) {
        return ((((r.x + r.width * size >= x && r.x + r.width * size <= x + width * size) || (r.x <= x + width * size && r.x >= x)) &&
                ((r.y <= y + height * size && r.y >= y) || (r.y + r.height * size <= y + height * size && r.y + r.height * size >= y))) ||
                (x + width * size >= r.x && x + width * size <= r.x + r.width * size || x <= r.x + r.width * size && x >= r.x) &&
                        (y <= r.y + r.height * size && y >= r.y || y + height * size <= r.y + r.height * size && y + height * size >= r.y));
    }

    void createWalls(Room r) {
        xl0 = r.x - size / 4;
        xr0 = r.x + r.width * size + size / 4;
        yl0 = r.y - size / 4;
        yr0 = r.y;

        xl1 = r.x + r.width * size;
        xr1 = r.x + r.width * size + size / 4;
        yl1 = r.y - size / 4;
        yr1 = r.y + r.height * size + size / 4;

        xl2 = r.x - size / 4;
        xr2 = r.x + r.width * size + size / 4;
        yl2 = r.y + r.height * size;
        yr2 = r.y + r.height * size + size / 4;

        xl3 = r.x - size / 4;
        xr3 = r.x;
        yl3 = r.y - size / 4;
        yr3 = r.y + r.height * size;
    }

    void intersectionHall(Hall h, int id) {
        switch (id) {
            case 0:
                hxl0 = h.x;
                hxr0 = h.x + h.width * size;
                hyl0 = yl0;
                hyr0 = yr0;
                break;
            case 1:
                hxl1 = xl1;
                hxr1 = xr1;
                hyl1 = h.y;
                hyr1 = h.y + h.height * size;
                break;
            case 2:
                hxl2 = h.x;
                hxr2 = h.x + h.width * size;
                hyl2 = yl2;
                hyr2 = yr2;
                break;
            case 3:
                hxl3 = xl3;
                hxr3 = xr3;
                hyl3 = h.y;
                hyr3 = h.y + h.height * size;
                break;
        }

    }

    void addWall(Array<Wall> walls) {
        if (idOfHalls[0]) walls.add(new Wall(xl0, yl0, xr0, yr0,wall));
        else {
            walls.add(new Wall(xl0, yl0, hxl0, hyr0,wall));
            walls.add(new Wall(hxr0, hyl0, xr0, yr0,wall));
        }
        if (idOfHalls[1]) walls.add(new Wall(xl1, yl1, xr1, yr1,wall));
        else {
            walls.add(new Wall(xl1, yl1, hxr1, hyl1,wall));
            walls.add(new Wall(hxl1, hyr1, xr1, yr1,wall));
        }
        if (idOfHalls[2]) walls.add(new Wall(xl2, yl2, xr2, yr2,wall));
        else {
            walls.add(new Wall(xl2, yl2, hxl2, hyr2,wall));
            walls.add(new Wall(hxr2, hyl2, xr2, yr2,wall));

        }
        if (idOfHalls[3]) walls.add(new Wall(xl3, yl3, xr3, yr3,wall));
        else {
            walls.add(new Wall(xl3, yl3, hxr3, hyl3,wall));
            walls.add(new Wall(hxl3, hyr3, xr3, yr3,wall));
        }
    }

        void addEnemies(Texture d1, Texture d2){
            int number = width*height/5;
            for(int i = 0; i<number; i++){
                float xS = x+size/2+((float)(Math.random()*1000)%width)*size;

                float yS = y+size/2+((float)(Math.random()*1000)%height)*size;
                if(xS>x&&xS+1.2f*size<x+width*size&&yS+size<y+height*size&&yS>y){
                    boolean noneIntersect = true;
                    if(enemies.size()>0){
                        for(Enemy enemy:enemies){
                            if(enemy.x>=xS&&enemy.x<=xS+enemy.spriteW||
                                    enemy.x+enemy.spriteW>=xS&&enemy.x+enemy.spriteW<=xS+enemy.spriteW){
                                if(enemy.y>=yS&&enemy.y<=yS+enemy.spriteH||
                                        enemy.y+enemy.spriteH>=yS&&enemy.y+enemy.spriteH<=yS+enemy.spriteW){
                                    noneIntersect = false;
                                }
                            }
                        }
                    }

                    if(noneIntersect){
                        if (Math.random()*100<50) {
                            enemies.add( new Enemy(d1, xS,yS,7, 5, 4, 100, 2, 10, 10, 1,size));
                        }
                        else
                            enemies.add(new Enemy(d2,xS,yS,7, 5, 3, 150, 2, 15, 10, 2,size));

                    }}}

        }

        void moveEnemiesX(int velocity){
            for(Enemy e: enemies)
                e.x+=velocity;
        }
        void moveEnemiesY(int velocity){
            for(Enemy e: enemies)
                e.y+=velocity;
        }

    void drawRoom(SpriteBatch batch) {
        batch.draw(bitmap, x,y,width*size, height*size, 0,0,width*bitmap.getWidth()/24, height*bitmap.getHeight()/24, false, false);

    }

}
