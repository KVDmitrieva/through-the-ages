package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainClass;
import com.mygdx.game.Map.Door;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Map.Room;
import com.mygdx.game.Map.Wall;
import com.mygdx.game.Sprite.Characters;
import com.mygdx.game.Sprite.Enemy;
import com.mygdx.game.Tools.JoyStick;

import java.util.Iterator;


public class GameScreen implements Screen {

    public static int LEVEL = 0;
    public static int SCORE = 0;
    public static int ENEMIES = 0;


    private MainClass mainClass;
    private Texture floor, d1, d2, wall, character, attack, health, def, doors;
    private int width, height, size, velocity;
    Map map;
    OrthographicCamera camera;
    JoyStick joyStick;
    Stage stage;
    Characters player;
    Enemy attacker;
    Door door;

    private float xStat, yStat;
    private boolean healths = false, attacks = false, defs = false;
    private int fpsH = 0, fpsA = 0, fpsD = 0;

    public GameScreen(MainClass mainClass) {
        this.mainClass = mainClass;


        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();
        camera = new OrthographicCamera(width, height);
        camera.position.x = (float) width / 2;
        camera.position.y = (float) height / 2;


        size = width / 5;
        velocity = width / 100;
        joyStick = new JoyStick(30, 30);


        floor = new Texture("floor1.png");
        wall = new Texture("wall.png");
        d1 = new Texture("dino1.png");
        d2 = new Texture("dino2.png");
        character = new Texture("character.png");
        doors = new Texture("level.png");
        attack = new Texture("atack.png");
        def = new Texture("def.png");
        health = new Texture("health.png");

        map = new Map(width, height, floor, size, d1, d2, wall);
        map.generate(8);
        createDoor();

        player = new Characters(character, (width / 2), (height / 2), 7, 6, 7, size);

        stage = new Stage(mainClass.screenPort, mainClass.batch);
        stage.addActor(joyStick);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        moveCamera();
        camera.update();
        mainClass.batch.setProjectionMatrix(camera.combined);
        mainClass.batch.begin();
        map.drawMap(mainClass.batch);
        door.drawObject(mainClass.batch);
        onDrawEnemy();
        drawStat();
        player.draw(mainClass.batch);
        mainClass.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        floor.dispose();
        stage.dispose();
        d1.dispose();
        d2.dispose();
        wall.dispose();
        character.dispose();
        doors.dispose();
        def.dispose();
        health.dispose();
        attack.dispose();
    }


    private void moveCamera() {
        float knobX = joyStick.getKnobPercentX();
        float knobY = joyStick.getKnobPercentY();
        boolean closeToWall = closeToWall(knobX, knobY);
        boolean closeToEnemy = closeToEnemy();

        if (!closeToWall) {
            if (closeToEnemy) attackEnemy(attacker);
            else {
                if (knobX != 0 && knobY != 0) {
                    if (Math.abs(knobX) > Math.abs(knobY)) {
                        camera.translate(knobX * velocity, 0);
                        if (knobX > 0) player.mod = 1;
                        else player.mod = 2;

                    } else {
                        camera.translate(0, knobY * velocity);
                        if (knobY > 0) player.mod = 4;
                        else player.mod = 3;
                    }
                    player.x = camera.position.x;
                    player.y = camera.position.y;
                } else player.mod = 0;
            }
        } else player.mod = 0;
    }

    private void attackEnemy(Enemy e) {
        if (player.x > e.x) {
            player.stop(6);
            e.mod = 2;

        } else {
            player.stop(5);
            e.mod = 1;
        }
        if (e.currentFrame == 2) {
            e.health = e.health - player.attack;
        }
        if (player.currentFrame == 3)
            player.health = player.health + (int) (player.def * 0.1f) - (e.attack);

    }

    private boolean closeToEnemy() {
        for (Room r : map.map_room) {
            Iterator<Enemy> i = r.enemies.iterator();
            while (i.hasNext()) {
                Enemy e = i.next();
                if (player.x + player.spriteW / 4 <= e.x + e.spriteW
                        && player.x + 3 * player.spriteW / 4 >= e.x
                        && player.y + player.spriteH / 2 <= e.y + e.spriteH
                        && player.y + player.spriteH / 2 >= e.y) {
                    attacker = e;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean closeToWall(float knobX, float knobY) {
        boolean closeTo = false, oneMore = false;
        int dir;
        if (Math.abs(knobX) > Math.abs(knobY)) {
            if (knobX > 0) dir = 4;
            else dir = 0;
        } else if (knobY > 0) dir = 2;
        else dir = 6;


        float x1 = player.x, x2 = player.x + player.spriteW,
                y1 = player.y, y2 = player.y + player.spriteH;
        for (Wall w : map.map_walls) {
            if (dir == 0) {
                if (x1 - 5 >= w.x1 && x1 - 5 <= w.x2 && (y1 <= w.y2 && y1 >= w.y1 || y2 >= w.y1 && y2 <= w.y2))
                    oneMore = true;
            } else if (dir == 6) {
                if (y1 - 5 >= w.y1 && y1 - 5 <= w.y2 && (x1 <= w.x2 && x1 >= w.x1 || x2 <= w.x2 && x2 >= w.x1))
                    oneMore = true;

            } else if (dir == 4) {
                if (x2 + 5 >= w.x1 && x2 + 5 <= w.x2 && (y1 <= w.y2 && y1 >= w.y1 || y2 >= w.y1 && y2 <= w.y2))
                    oneMore = true;

            } else if (dir == 2) {
                if (y2 + 5 >= w.y1 && y2 + 5 <= w.y2 && (x1 <= w.x2 && x1 >= w.x1 || x2 <= w.x2 && x2 >= w.x1))
                    oneMore = true;

            }
            if (oneMore) {
                closeTo = true;
                player.mod = 0;
            }
        }

        return closeTo;
    }

    private void createDoor() {
        int idOfRoom = (int) (Math.random() * 100) % map.map_room.size();
        Room r = map.map_room.get(idOfRoom);
        int x = r.x + ((int) (Math.random() * 100) % r.width) * r.size;
        int y = r.y + ((int) (Math.random() * 100) % r.height) * r.size;
        door = new Door(idOfRoom, doors, x, y, r.size);
        Iterator<Enemy> i = r.enemies.iterator();
        while (i.hasNext()) {
            Enemy enemy = i.next();
            if (enemy.x >= x && enemy.x <= x + r.size || enemy.x + enemy.spriteW >= x && enemy.x + enemy.spriteW <= x + r.size) {
                if (enemy.y >= y && enemy.y <= y + r.size || enemy.y + enemy.spriteH >= y && enemy.y + enemy.spriteH <= y + r.size) {
                    i.remove();
                }
            }
        }
    }

    private void onDrawEnemy() {
        for (Room r : map.map_room) {
            Iterator<Enemy> i = r.enemies.iterator();
            while (i.hasNext()) {
                Enemy e = i.next();
                if (e.health > 0) {
                    e.drawe(mainClass.batch);
                } else {
                    destroyEnemy(e);
                    i.remove();
                    ENEMIES++;
                }
            }
        }
    }

    void destroyEnemy(Enemy e) {
        SCORE += e.id * 100;
        xStat = (int) e.x+size/2;
        yStat = (int) e.y+size/2;

        int rand = (int) (Math.random() * 100);
        if (rand >= 10 && rand <= 30) {
            player.health += 100;
            healths = true;
        } else if (rand >= 40 && rand <= 60) {
            player.def += 1;
            defs = true;
        } else if (rand >= 70 && rand <= 90) {
            player.attack += 5;
            attacks = true;
        }


    }

    private boolean intersectDoor() {
        int px1 = (int) player.x;
        int px2 = (int) player.x + (int) player.spriteW;
        int py1 = (int) player.y;
        int py2 = (int) player.y + (int) player.spriteH;

        int dx1 = door.x;
        int dx2 = door.x + door.size;
        int dy1 = door.y;
        int dy2 = door.y + door.size;

        if (px1 > dx1 && px1 < dx2 || px2 > dx1 && px2 < dx2) {
            if (py1 > dy1 && py1 < dy2 || py2 > dy1 && py2 < dy2) {
                return true;
            }
        } return false;


    }

    private void drawStat(){
        if(healths){
            mainClass.batch.draw(health, xStat, yStat, (float)size/2, (float)size/2, 0,0,health.getWidth(),health.getHeight(), false,false);
            fpsH++;
            if(fpsH==5){
                healths = false;
                fpsH = 0;
            }
        }
        if(attacks){
            mainClass.batch.draw(attack, xStat, yStat, (float)size/2, (float)size/2, 0,0,attack.getWidth(),attack.getHeight(), false,false);
            fpsA++;
            if(fpsA==5){
                attacks = false;
                fpsA = 0;
            }
        }
        if(defs){
            mainClass.batch.draw(def, xStat, yStat, (float)size/2, (float)size/2, 0,0,def.getWidth(),def.getHeight(), false,false);
            fpsD++;
            if(fpsD==5){
                defs = false;
                fpsD = 0;
            }
        }
    }
}
