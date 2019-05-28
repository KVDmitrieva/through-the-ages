package com.mygdx.game.Map;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

class Room {
    int x, y, width, height;
    boolean[] idOfHalls = new boolean[4];
    Array<Hall> room_hall = new Array<Hall>();
    int numberOfHalls;
    boolean hallCreated = false;
    Texture bitmap;
    int size;
   // private Rect sourceRect;
    // List<Enemy> enemies = new ArrayList<>();
    //walls cor
    private int xl0, yl0, xr0, yr0, xl1, yl1, xr1, yr1,
            xl2, yl2, xr2, yr2, xl3, yl3, xr3, yr3;
    private int hxl0 = 0, hyl0 = 0, hxr0 = 0, hyr0 = 0, hxl1 = 0, hyl1 = 0, hxr1 = 0, hyr1 = 0,
            hxl2 = 0, hyl2 = 0, hxr2 = 0, hyr2 = 0, hxl3 = 0, hyl3 = 0, hxr3 = 0, hyr3 = 0;


    Room(int x, int y, int width, int height, int size, Texture image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        for (int i = 0; i < 4; i++) {
            idOfHalls[i] = true;
        }
        bitmap = image;
        this.size = size;
      //  sourceRect = new Rect(0, 0, size * width, size * height);
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
        if (idOfHalls[0]) walls.add(new Wall(xl0, yl0, xr0, yr0));
        else {
            walls.add(new Wall(xl0, yl0, hxl0, hyr0));
            walls.add(new Wall(hxr0, hyl0, xr0, yr0));
        }
        if (idOfHalls[1]) walls.add(new Wall(xl1, yl1, xr1, yr1));
        else {
            walls.add(new Wall(xl1, yl1, hxr1, hyl1));
            walls.add(new Wall(hxl1, hyr1, xr1, yr1));
        }
        if (idOfHalls[2]) walls.add(new Wall(xl2, yl2, xr2, yr2));
        else {
            walls.add(new Wall(xl2, yl2, hxl2, hyr2));
            walls.add(new Wall(hxr2, hyl2, xr2, yr2));

        }
        if (idOfHalls[3]) walls.add(new Wall(xl3, yl3, xr3, yr3));
        else {
            walls.add(new Wall(xl3, yl3, hxr3, hyl3));
            walls.add(new Wall(hxl3, hyr3, xr3, yr3));
        }
    }

    /*    void addEnemies(Bitmap d1, Bitmap d2){
            int number = width*height/5;
            for(int i = 0; i<number; i++){
                float xS = x+size/2+((float)(Math.random()*1000)%width)*size;

                float yS = y+size/2+((float)(Math.random()*1000)%height)*size;
                if(xS>x&&xS+size<x+width*size&&yS+size<y+height*size&&yS>y){
                    boolean noneIntersect = true;
                    if(enemies.size()>0){
                        for(Enemy enemy:enemies){
                            if(enemy.x>=xS&&enemy.x<=xS+enemy.spriteWidth||enemy.x+enemy.spriteWidth>=xS&&enemy.x+enemy.spriteWidth<=xS+enemy.spriteWidth){
                                if(enemy.y>=yS&&enemy.y<=yS+enemy.spriteHeight||enemy.y+enemy.spriteHeight>=yS&&enemy.y+enemy.spriteHeight<=yS+enemy.spriteWidth){
                                    noneIntersect = false;
                                }
                            }
                        }
                    }

                    if(noneIntersect){
                        if (Math.random()*100<50) {
                            enemies.add( new Enemy(d1, xS,yS,7, 5, 4, 100, 2, 10, 10, 1));
                        }
                        else
                            enemies.add(new Enemy(d2,xS,yS,7, 5, 3, 150, 2, 15, 10, 2));

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
    */
    void drawRoom(SpriteBatch batch, ShapeRenderer shape) {
       // batch.draw(bitmap, (float)x, (float)y,0,0, width * size, height * size);
        batch.draw(bitmap, x,y,width*size, height*size, 0,0,width*bitmap.getWidth()/24, height*bitmap.getHeight()/24, false, false);

       // shape.setColor(0.2f, 0.2f, 0.2f, 1);
       // shape.rect((float)x, (float)y,  width * size,  height * size);
    }

}
