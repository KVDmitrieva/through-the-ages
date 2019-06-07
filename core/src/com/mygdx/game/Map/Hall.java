package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

class Hall {
    int x, y, width, height, //coordinates, size of hall
            id;// id - where the hall is located relativity to room (on top, bottom, left or right side of the room)

    private int size; //basic size which helps to scale the map
    private Texture bitmap, wall; //texture of floor and walls

    boolean roomCreated = false; //checks if the second room of the hall was created
    boolean deleteHall = false; //if the second room wasn't successfully created the room and this hall wll be removed

    //coordinates for all walls
    private int xl0, yl0, xr0, yr0, xl1, yl1, xr1, yr1,
            xl2, yl2, xr2, yr2, xl3, yl3, xr3, yr3;


    Hall(int x, int y, int width, int height, int size, Texture image, Texture wall){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.size = size;
        bitmap = image;
        this.wall = wall;
    }

    void createWalls(Hall r){
        //set coordinates for walls
        xl0 = r.x;
        xr0 = r.x+r.width*size;
        yl0 = r.y-size/4;
        yr0 = r.y;

        xl1 = r.x+r.width*size;
        xr1 = r.x+r.width*size+size/4;
        yl1 = r.y;
        yr1 = r.y+r.height*size;

        xl2 = r.x;
        xr2 = r.x+r.width*size;
        yl2 = r.y+r.height*size;
        yr2 = r.y+r.height*size+size/4;

        xl3 = r.x-size/4;
        xr3 = r.x;
        yl3 = r.y;
        yr3 = r.y+r.height*size;
    }

    void addWall(Array<Wall> walls){
        //create walls
        //checks if the hall is vertical or horizontal
        if(id%2==1){
            walls.add(new Wall(xl0, yl0, xr0, yr0,wall));
            walls.add(new Wall(xl2, yl2, xr2, yr2,wall));
        }
        else{
            walls.add(new Wall(xl3, yl3, xr3, yr3,wall));
            walls.add(new Wall(xl1, yl1, xr1, yr1,wall));
        }
    }

    void drawHall(SpriteBatch batch){
        batch.draw(bitmap, x,y,width*size, height*size, 0,0,width*bitmap.getWidth()/24, height*bitmap.getHeight()/24, false, false);

    }
}