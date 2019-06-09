package com.mygdx.game.Map;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.Boss;
import com.mygdx.game.Sprite.Dino1;
import com.mygdx.game.Sprite.Dino2;
import com.mygdx.game.Sprite.Enemy;

import java.util.ArrayList;

import static com.mygdx.game.Tools.Constants.SIZE;
import static com.mygdx.game.Tools.Constants.scaleSizeOfFloor;
import static com.mygdx.game.Tools.Constants.scaleWidthOfEnemy;


public class Room {

    public int x, y, width, height; //coordinates and size of room
    boolean[] idOfHalls = new boolean[4]; //checks where the hall can be created
    ArrayList<Hall> room_hall = new ArrayList<Hall>(); //list of room's halls
    int numberOfHalls; //number of room's halls
    boolean hallCreated = false; //checks if the halls were created
    private Texture bitmap, wall; //texture of floor and wall
    public int size; //basic size to scale map
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //list of room's enemy

    //walls coordinates without hall
    private int xl0, yl0, xr0, yr0, xl1, yl1, xr1, yr1,
            xl2, yl2, xr2, yr2, xl3, yl3, xr3, yr3;

    //walls coordinates with hall
    private int hxl0 = 0, hyl0 = 0, hxr0 = 0, hyr0 = 0, hxl1 = 0, hyl1 = 0, hxr1 = 0, hyr1 = 0,
            hxl2 = 0, hyl2 = 0, hxr2 = 0, hyr2 = 0, hxl3 = 0, hyl3 = 0, hxr3 = 0, hyr3 = 0;


    Room(int x, int y, int width, int height, Texture image, Texture wall) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        for (int i = 0; i < 4; i++) {
            idOfHalls[i] = true;
        }
        bitmap = image;
        this.wall = wall;
        this.size = SIZE;
    }

    int numberOfHalls() {
        return (int) (Math.random() * 100) % 3; //number of halls for this room
    }

    boolean intersect(Room r) {
        //check if new room intersects with others
        return ((((r.x + r.width * size >= x && r.x + r.width * size <= x + width * size) || (r.x <= x + width * size && r.x >= x)) &&
                ((r.y <= y + height * size && r.y >= y) || (r.y + r.height * size <= y + height * size && r.y + r.height * size >= y))) ||
                (x + width * size >= r.x && x + width * size <= r.x + r.width * size || x <= r.x + r.width * size && x >= r.x) &&
                        (y <= r.y + r.height * size && y >= r.y || y + height * size <= r.y + r.height * size && y + height * size >= r.y));
    }

    void createWalls(Room r) {
        //set params for walls
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
        //if there is a hall then sets new walls' coordinates
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
        //create new walls
        if (idOfHalls[0]) walls.add(new Wall(xl0, yl0, xr0, yr0, wall));
        else {
            walls.add(new Wall(xl0, yl0, hxl0, hyr0, wall));
            walls.add(new Wall(hxr0, hyl0, xr0, yr0, wall));
        }
        if (idOfHalls[1]) walls.add(new Wall(xl1, yl1, xr1, yr1, wall));
        else {
            walls.add(new Wall(xl1, yl1, hxr1, hyl1, wall));
            walls.add(new Wall(hxl1, hyr1, xr1, yr1, wall));
        }
        if (idOfHalls[2]) walls.add(new Wall(xl2, yl2, xr2, yr2, wall));
        else {
            walls.add(new Wall(xl2, yl2, hxl2, hyr2, wall));
            walls.add(new Wall(hxr2, hyl2, xr2, yr2, wall));

        }
        if (idOfHalls[3]) walls.add(new Wall(xl3, yl3, xr3, yr3, wall));
        else {
            walls.add(new Wall(xl3, yl3, hxr3, hyl3, wall));
            walls.add(new Wall(hxl3, hyr3, xr3, yr3, wall));
        }
    }

    void addEnemies(Texture d1, Texture d2) {
        int number = width * height / 5; //number of enemies in room
        for (int i = 0; i < number; i++) {
            float xS = x + (float) size / 2 + ((float) (Math.random() * 1000) % width) * size;
            float yS = y + (float) size / 2 + ((float) (Math.random() * 1000) % height) * size;

            if (xS > x && xS + scaleWidthOfEnemy * size < x + width * size && yS + size < y + height * size && yS > y) {
                boolean noneIntersect = true; //check intersection with borders of the room
                if (enemies.size() > 0) {
                    for (Enemy enemy : enemies) { //check intersection with other enemies
                        if (enemy.x >= xS && enemy.x <= xS + enemy.spriteW ||
                                enemy.x + enemy.spriteW >= xS && enemy.x + enemy.spriteW <= xS + enemy.spriteW) {
                            if (enemy.y >= yS && enemy.y <= yS + enemy.spriteH ||
                                    enemy.y + enemy.spriteH >= yS && enemy.y + enemy.spriteH <= yS + enemy.spriteW) {
                                noneIntersect = false;
                            }
                        }
                    }
                }

                if (noneIntersect) {
                    if (Math.random() * 100 < 50) {
                        enemies.add(new Dino1(d1, xS, yS, 5, 4));
                    } else
                        enemies.add(new Dino2(d2, xS, yS, 5, 3));

                }
            }
        }

    }


    void drawRoom(SpriteBatch batch) {
        batch.draw(bitmap, x, y, width * size, height * size, 0, 0, width * bitmap.getWidth() / scaleSizeOfFloor, height * bitmap.getHeight() / scaleSizeOfFloor, false, false);

    }

    void addBoss(Texture boss, float x, float y) {
        enemies.add(new Boss(boss, x + 7 * size, y + size, 5, 4));
    }

}
