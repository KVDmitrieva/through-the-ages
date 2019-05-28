package com.mygdx.game.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
public class Map {
    private int width, height;
   // private Paint p =new Paint();
    private int countHalls;
    Array<Room> map_room = new Array<Room>();
    Array<Wall> map_walls = new Array<Wall>();
    private int size;


    private Texture floor, d1,d2;


    public Map(int width, int height, Texture floor, int size, Texture d1,Texture d2){
        this.width = width;
        this.height =height;
        this.floor = floor;
        this.size = size;
        this.d1 = d1;
        this.d2 = d2;
    }

    private void create(Hall hall) {
        hall.roomCreated = true;
        int w = hall.width + (int) (Math.random() * 100) % 5+1;
        if (w < 3) w += 1;
        int h = hall.height + (int) (Math.random() * 100) % 5+1;
        if (h < 3) h += 1;
        int x = 0, y = 0;
        switch (hall.id) {
            case 0:
                x = hall.x - size;
                y = hall.y - h * size;
                break;
            case 1:
                x = hall.x + hall.width * size;
                y = hall.y - size;
                break;
            case 2:
                x = hall.x - size;
                y = hall.y + hall.height * size;
                break;
            case 3:
                x = hall.x - w * size;
                y = hall.y - size;
                break;


        }
        Room room = new Room(x, y, w, h, size,floor);
        boolean intersection = true;
        for (Room r : map_room) {
            if (r.intersect(room)) intersection = false;
        }
        if (intersection) {
            room.createWalls(room);
            int idHall = 0;
            switch (hall.id) {
                case 0:
                    room.idOfHalls[2] = false;
                    idHall = 2;
                    break;
                case 1:
                    room.idOfHalls[3] = false;
                    idHall = 3;
                    break;
                case 2:
                    room.idOfHalls[0] = false;
                    idHall = 0;
                    break;
                case 3:
                    room.idOfHalls[1] = false;
                    idHall = 1;
                    break;
            }
            room.intersectionHall(hall, idHall);
            map_room.add(room);
           // room.addEnemies(d1,d2);
            addHall(room);
        } else hall.moreWalls = true;
    }

    private Room first(){
        int w = 3 + (int)(Math.random()*100)%5;
        int h = 3 + (int)(Math.random()*100)%5;
        return  new Room((width/2-(w/2)*size), (height/2)-(h/2)*size,w,h,size,floor);
    }

    private Hall createHall(Room r, int id) {
        Hall hall;
        int w=2, h=2;
        int x = 0, y = 0;
        int more = 2;
        switch(id){
            case 0:
                h=more;
                x=r.x+size;
                y=r.y-h*size;
                break;
            case 1:
                w=more;
                x=r.x+r.width*size;
                y=r.y+size;
                break;
            case 2:
                h=more;
                x=r.x+r.width*size-2*size;
                y=r.y+r.height*size;
                break;
            case 3:
                w=more;
                x=r.x-w*size;
                y=r.y+r.height*size-2*size;
                break;


        }
        hall = new Hall(x,y,w,h,size,floor);
        return hall;
    }

    public void generate(int roomCount){
        countHalls = roomCount-1;
        map_room.clear();
        map_walls.clear();
        Room rfirst = first();
       // rfirst.addEnemies(d1,d2);
        rfirst.createWalls(rfirst);
        map_room.add(rfirst);
        addHall(rfirst);
        createWalls();
    }

    private void createWalls(){
        for(Room r: map_room){
            r.addWall(map_walls);
            for(Hall h:r.room_hall){
                h.addWall(map_walls);
            }
        }
    }

    private void addHall(Room r){
        if(!r.hallCreated) {
            r.hallCreated = true;
            if(map_room.size==1) r.numberOfHalls = 4; else
                r.numberOfHalls = r.numberOfHalls();
            if(r.numberOfHalls==6) r.numberOfHalls++;
            countHalls -= r.numberOfHalls;
            if (countHalls < 0) r.numberOfHalls += countHalls;
            boolean freeWall = false;
            for(int i=0; i<4; i++){
                if(r.idOfHalls[i]) freeWall=true;
            }
            int index;
            if (r.numberOfHalls > 0&&freeWall) {
                for (int j = 0; j < r.numberOfHalls; j++) {
                    index = Math.abs(((int)(Math.random() *Math.random()* 10000)) % 4 );
                    if (!r.idOfHalls[index])
                        for(int i=0; i<4; i++){
                            if(r.idOfHalls[i])index = i;
                        }
                    r.idOfHalls[index] = false;

                    Hall hall = createHall(r, index);

                    r.intersectionHall(hall, index);
                    hall.id = index;
                    hall.createWalls(hall);
                    r.room_hall.add(hall);

                }
                int a = 0;
                for (Hall h : r.room_hall) {
                    if(!h.roomCreated)
                        create(h);
                    if(h.moreWalls){
                        r.idOfHalls[h.id]=true;
                        r.room_hall.removeIndex(a);

                    } a++;
                }
            }
        }
    }

    public void drawMap(SpriteBatch batch, ShapeRenderer shape){
        batch.begin();
        for(Room r:map_room){
            r.drawRoom(batch, shape);
            for(Hall hall:r.room_hall){
                hall.drawHall(batch, shape);
            }
        }
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        for(Wall w:map_walls){
            w.drawWall(shape);
        }
        shape.end();
    }



}