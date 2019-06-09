package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Iterator;

import static com.mygdx.game.Tools.Constants.HEIGHT;
import static com.mygdx.game.Tools.Constants.LEVEL;
import static com.mygdx.game.Tools.Constants.SIZE;
import static com.mygdx.game.Tools.Constants.WIDTH;
import static com.mygdx.game.Tools.Constants.bossRoomHeight;
import static com.mygdx.game.Tools.Constants.bossRoomWidth;

public class Map {
    private int width, height, size;
    private int countHalls; //max number of halls
    public ArrayList<Room> map_room = new ArrayList<Room>(); //list of all rooms
    public Array<Wall> map_walls = new Array<Wall>(); //list of all walls
    private Texture floor, d1, d2, wall; //textures of floor, monsters and wall


    public Map(Texture floor, Texture d1, Texture d2, Texture wall) {
        this.width = WIDTH;
        this.height = HEIGHT;
        this.floor = floor;
        this.d1 = d1;
        this.d2 = d2;
        this.wall = wall;
        this.size = SIZE;
    }

    private int random() {
        return (int) (Math.random() * 100) % 5;
    }

    //function creates new room  that connects with hall
    private void create(Hall hall) {
        hall.roomCreated = true;
        int w = hall.width + random() + 1; //width of room
        if (w < 3) w += 1;
        int h = hall.height + random() + 1; //height of room
        if (h < 3) h += 1;
        int x = 0, y = 0; //coordinates of room
        //check where the hall connects to the room and then set coordinates
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
        Room room = new Room(x, y, w, h, floor, wall);
        boolean intersection = true; //check if new room intersects with others
        for (Room r : map_room) {
            if (r.intersect(room)) intersection = false;
        }
        if (intersection) { //if new room doesn't intersect others
            room.createWalls(room);//create walls for room
            //check where the hall connects to the room
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
            room.intersectionHall(hall, idHall); //updates room's coordinates
            map_room.add(room); //add new room to list
            room.addEnemies(d1, d2); //add enemies to the room
            addHall(room); //add new halls to the room
        } else hall.deleteHall = true; //if new room intersects others hall will be deleted
    }

    //creates first room where player will start
    private Room first() {
        int w = 3 + random();
        int h = 3 + random();
        return new Room((width / 2 - (w / 2) * size), (height / 2) - (h / 2) * size, w, h, floor, wall);
    }

    //function creates new hall for room
    private Hall createHall(Room r, int id) {
        Hall hall;
        int w = 2, h = 2;
        int x = 0, y = 0;
        int more = 2;
        switch (id) {
            case 0:
                h = more;
                x = r.x + size;
                y = r.y - h * size;
                break;
            case 1:
                w = more;
                x = r.x + r.width * size;
                y = r.y + size;
                break;
            case 2:
                h = more;
                x = r.x + r.width * size - 2 * size;
                y = r.y + r.height * size;
                break;
            case 3:
                w = more;
                x = r.x - w * size;
                y = r.y + r.height * size - 2 * size;
                break;


        }
        hall = new Hall(x, y, w, h, floor, wall);
        return hall;
    }

    public void generate(int roomCount) {
        countHalls = roomCount - 1;
        map_room.clear();
        map_walls.clear();
        //special room with boss
        if (LEVEL % 5 == 0) {
            bossRoom();
        } else {
            //start generation
            Room rfirst = first();
            rfirst.createWalls(rfirst);
            map_room.add(rfirst);
            addHall(rfirst);
            createWalls();
        }
    }

    private void createWalls() {
        //add walls for all rooms and halls
        for (Room r : map_room) {
            r.addWall(map_walls);
            for (Hall h : r.room_hall) {
                h.addWall(map_walls);
            }
        }
    }

    private void addHall(Room r) {
        if (!r.hallCreated) {
            r.hallCreated = true;
            if (map_room.size() == 1) r.numberOfHalls = 4;
            else //for the first room add 4 halls
                r.numberOfHalls = r.numberOfHalls(); //number of halls in other rooms
            if (r.numberOfHalls == 6) r.numberOfHalls++; //one more hall for 6th room
            countHalls -= r.numberOfHalls; //check the ability to create new halls
            if (countHalls < 0) r.numberOfHalls += countHalls;
            boolean freeWall = false; //check free walls (without halls) in the room
            for (int i = 0; i < 4; i++) {
                if (r.idOfHalls[i]) freeWall = true;
            }
            int index;
            if (r.numberOfHalls > 0 && freeWall) {
                for (int j = 0; j < r.numberOfHalls; j++) {
                    index = Math.abs(((int) (Math.random() * Math.random() * 10000)) % 4);
                    if (!r.idOfHalls[index]) //check wall - free or not
                        for (int i = 0; i < 4; i++) {
                            if (r.idOfHalls[i]) index = i; //set id for hall
                        }
                    r.idOfHalls[index] = false;

                    Hall hall = createHall(r, index); //create hall to this room

                    r.intersectionHall(hall, index); //update room's hall
                    hall.id = index;
                    hall.createWalls(hall); //create walls for hall
                    r.room_hall.add(hall);

                }

                Iterator<Hall> i = r.room_hall.iterator();
                while (i.hasNext()) {
                    Hall h = i.next();
                    if (!h.roomCreated)
                        create(h);
                    if (h.deleteHall) {
                        r.idOfHalls[h.id] = true;
                        i.remove();
                    }
                }


            }
        }
    }

    public void drawMap(SpriteBatch batch) {

        for (Wall w : map_walls) {
            w.drawWall(batch);
        }

        for (Room r : map_room) {
            r.drawRoom(batch);
            for (Hall hall : r.room_hall) {
                hall.drawHall(batch);
            }
        }


    }

    private void bossRoom() {
        //special room with boss
        Room room = new Room(width / 2 - size, height / 2 - size, bossRoomWidth, bossRoomHeight, floor, wall);
        room.addBoss(new Texture("boss.png"), (float) width / 2 - size, (float) height / 2 - size);
        room.createWalls(room);
        room.addWall(map_walls);
        map_room.add(room);
    }


}