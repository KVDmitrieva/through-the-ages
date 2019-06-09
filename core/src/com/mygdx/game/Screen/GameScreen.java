package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainClass;
import com.mygdx.game.Map.Door;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Map.Room;
import com.mygdx.game.Map.Wall;
import com.mygdx.game.Sprite.Characters;
import com.mygdx.game.Sprite.Enemy;
import com.mygdx.game.Tools.Gesture;
import com.mygdx.game.Tools.JoyStick;
import com.mygdx.game.Tools.MyButton;
import com.mygdx.game.Tools.MyDatabase;

import java.util.Iterator;

import static com.mygdx.game.Tools.Constants.HEIGHT;
import static com.mygdx.game.Tools.Constants.SIZE;
import static com.mygdx.game.Tools.Constants.WIDTH;
import static com.mygdx.game.Tools.Constants.LEVEL;
import static com.mygdx.game.Tools.Constants.SCORE;
import static com.mygdx.game.Tools.Constants.ENEMIES;
import static com.mygdx.game.Tools.Constants.bonusForNewLevel;
import static com.mygdx.game.Tools.Constants.fontScale;
import static com.mygdx.game.Tools.Constants.healthForBoss;
import static com.mygdx.game.Tools.Constants.healthForEnemy;
import static com.mygdx.game.Tools.Constants.healthHeight;
import static com.mygdx.game.Tools.Constants.healthWidth;
import static com.mygdx.game.Tools.Constants.maxForAttack;
import static com.mygdx.game.Tools.Constants.maxForDef;
import static com.mygdx.game.Tools.Constants.maxForHealth;
import static com.mygdx.game.Tools.Constants.maxNumberOfRooms;
import static com.mygdx.game.Tools.Constants.maxPlayerHealth;
import static com.mygdx.game.Tools.Constants.minForAttack;
import static com.mygdx.game.Tools.Constants.minForDef;
import static com.mygdx.game.Tools.Constants.minForHealth;
import static com.mygdx.game.Tools.Constants.noCoef;
import static com.mygdx.game.Tools.Constants.partOfDef;
import static com.mygdx.game.Tools.Constants.partOfHealthX;
import static com.mygdx.game.Tools.Constants.partOfHealthX2;
import static com.mygdx.game.Tools.Constants.partOfHealthY;
import static com.mygdx.game.Tools.Constants.periodOfDrawingStats;
import static com.mygdx.game.Tools.Constants.scoreForEnemy;
import static com.mygdx.game.Tools.Constants.sizeExitButton;
import static com.mygdx.game.Tools.Constants.yesCoef;


public class GameScreen implements Screen {


    private MainClass mainClass;
    private Texture floor, d1, d2, wall, character, attack, health,
            def, doors, white, red, gameover;
    private int velocity;
    private GlyphLayout layout;
    private Map map;
    private OrthographicCamera camera;
    private JoyStick joyStick;
    private Stage stage;
    private Characters player;
    private Enemy attacker;
    private Door door;
    private BitmapFont bitmapFont;
    private float xStat, yStat;
    private boolean healths = false, attacks = false, defs = false, noCheck = false;
    private int fpsH = 0, fpsA = 0, fpsD = 0;
    private MyButton exit, yes, no, quest;
    private boolean doorCreated = false;


    GameScreen(final MainClass mainClass) {
        this.mainClass = mainClass;

        SCORE = 0;
        LEVEL = 1;
        ENEMIES = 0;

        //game camera
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.x = (float) WIDTH / 2;
        camera.position.y = (float) HEIGHT / 2;


        velocity = WIDTH / 100;  //players speed of moving

        joyStick = new JoyStick(); //control player's move

        //font for text with result of game
        bitmapFont = new BitmapFont();
        layout = new GlyphLayout(bitmapFont, "hello");

        floor = new Texture("floor1.png");
        wall = new Texture("wall.png");
        d1 = new Texture("dino1.png");
        d2 = new Texture("dino2.png");
        character = new Texture("character.png");
        doors = new Texture("level.png");
        attack = new Texture("atack.png");
        def = new Texture("def.png");
        health = new Texture("health.png");
        red = new Texture("red.png");
        white = new Texture("white.png");
        gameover = new Texture("gameover.png");

        map = new Map(floor, d1, d2, wall);
        map.generate(maxNumberOfRooms); //auto-generating of map

        createDoor();

        player = new Characters(character, (float) (WIDTH / 2), (float) (HEIGHT / 2), 6, 7);

        //exit button - go to main menu
        exit = new MyButton("exit");
        getParamsForButtons();
        exit.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                MyDatabase db = new MyDatabase();
                if (SCORE != 0) db.pushData();
                mainClass.setScreen(new MainMenu(mainClass));
                return true;
            }

        });
        //button for dialog
        yes = new MyButton("yes");
        yes.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                no.setVisible(false);
                yes.setVisible(false);
                quest.setVisible(false);
                LEVEL++;
                SCORE += bonusForNewLevel;
                generate();

                return true;
            }

        });

        //button for dialog
        no = new MyButton("no");
        no.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                no.setVisible(false);
                yes.setVisible(false);
                quest.setVisible(false);
                noCheck = true;
                return true;
            }

        });

        no.setVisible(false);
        yes.setVisible(false);

        //dialog of getting to another level
        quest = new MyButton("question");
        paramAlert();
        quest.setVisible(false);


        stage = new Stage(mainClass.screenPort, mainClass.batch);
        stage.addActor(joyStick);
        stage.addActor(exit);
        stage.addActor(quest);
        stage.addActor(no);
        stage.addActor(yes);

        //Listener of tapping on screen (to move joystick)
        Gesture gesture = new Gesture(joyStick);
        Gdx.input.setInputProcessor(new GestureDetector(gesture));

        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(new GestureDetector(gesture));
        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);


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

        if (player.health > 0) {
            map.drawMap(mainClass.batch);
            if (doorCreated) door.drawDoor(mainClass.batch);
            onDrawEnemy();
            drawStat();
            drawHealth();
            player.draw(mainClass.batch);

        /*
        if the door was created and player intersects it,
       the game will show the alert with suggestion to go to another level
       if player doesn't want to go, noCheck will become true and alert won't show again
       until player move away and come again
         */
            if (doorCreated) {
                if (intersectDoor()) {
                    if (!noCheck) {
                        drawAlert();
                    }
                } else noCheck = false;
            }

        } else endOfGame();


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
        red.dispose();
        white.dispose();
    }

    private void moveCamera() {
        //direction of joystick
        float knobX = joyStick.getKnobPercentX();
        float knobY = joyStick.getKnobPercentY();

        boolean closeToWall = closeToWall(knobX, knobY); //check intersection with walls

        boolean closeToEnemy = closeToEnemy(); //check intersection with enemies

        if (!closeToWall) {
            if (closeToEnemy) attackEnemy(attacker);
            else {


                if (knobX != 0 && knobY != 0) {
                    if (Math.abs(knobX) > Math.abs(knobY)) { //choose direction of moving (left, right, up, down)
                        camera.translate(knobX * velocity, 0);
                        if (knobX > 0)
                            player.mod = 1;  //line with animation of moving (right)
                        else
                            player.mod = 2; //line with animation of moving (left)

                    } else {
                        camera.translate(0, knobY * velocity);
                        if (knobY > 0)
                            player.mod = 4; //line with animation of moving (up)
                        else
                            player.mod = 3; //line with animation of moving (down)
                    }


                    player.x = camera.position.x;
                    player.y = camera.position.y;
                } else player.mod = 0;
            }
        } else player.mod = 0;
    }

    private void attackEnemy(Enemy e) {
        if (player.x > e.x) {
            player.stop(6); //character animation of attack (left direction)
            e.mod = 2; //enemy animation of attack (right direction)

        } else {
            player.stop(5); //character animation of attack (right direction)
            e.mod = 1; //enemy animation of attack (left direction)
        }
        if (e.currentFrame == 2) {  //frame of enemy attack
            e.health = e.health - player.attack;
        }
        if (player.currentFrame == 3) //frame of character attack
            player.health = player.health + (int) (player.def * partOfDef) - (e.attack);

    }

    private boolean closeToEnemy() {
        //check the intersection with enemy
        for (Room r : map.map_room) {
            Iterator<Enemy> i = r.enemies.iterator();
            while (i.hasNext()) {
                Enemy e = i.next();
                if (((player.x + player.spriteW <= e.x + e.spriteW && player.x + player.spriteW >= e.x) ||
                        (player.x <= e.x + e.spriteW && player.x >= e.x))
                        && ((((player.y + 3 * player.spriteH / 4 <= e.y + e.spriteH)
                        && player.y + 3 * player.spriteH / 4 >= e.y) || (player.y <= e.y + e.spriteH)
                        && player.y >= e.y) || player.y <= e.y && e.y <= player.y + player.spriteH)) {
                    attacker = e; // player intersects with this enemy
                    return true;
                }
            }
        }
        return false;
    }

    private boolean closeToWall(float knobX, float knobY) {
        //check intersection with walls
        boolean closeTo = false, oneMore = false;
        int dir; //direction of player moving
        if (Math.abs(knobX) > Math.abs(knobY)) {
            if (knobX > 0) dir = 4;
            else dir = 0;
        } else if (knobY > 0) dir = 2;
        else dir = 6;


        float x1 = player.x, x2 = player.x + player.spriteW,
                y1 = player.y, y2 = player.y + player.spriteH;
        for (Wall w : map.map_walls) {
            if (dir == 0) {
                if (x1 - 3 >= w.x1 && x1 - 3 <= w.x2 && (y1 <= w.y2 && y1 >= w.y1 || y2 >= w.y1 && y2 <= w.y2))
                    oneMore = true;
            } else if (dir == 6) {
                if (y1 - 3 >= w.y1 && y1 - 3 <= w.y2 && (x1 <= w.x2 && x1 >= w.x1 || x2 <= w.x2 && x2 >= w.x1))
                    oneMore = true;

            } else if (dir == 4) {
                if (x2 + 3 >= w.x1 && x2 + 3 <= w.x2 && (y1 <= w.y2 && y1 >= w.y1 || y2 >= w.y1 && y2 <= w.y2))
                    oneMore = true;

            } else {
                if (y2 + 3 >= w.y1 && y2 + 3 <= w.y2 && (x1 <= w.x2 && x1 >= w.x1 || x2 <= w.x2 && x2 >= w.x1))
                    oneMore = true;

            }
            if (oneMore) {
                closeTo = true;
                player.mod = 0; //line with character animation
            }
        }

        return closeTo;
    }

    private void createDoor() {
        //create door

        int idOfRoom = (int) (Math.random() * 100) % map.map_room.size();  //id of room where the door will be created
        Room r = map.map_room.get(idOfRoom); //get this room
        int x = r.x + ((int) (Math.random() * 100) % r.width) * r.size;  //coordinates of the door
        int y = r.y + ((int) (Math.random() * 100) % r.height) * r.size;
        door = new Door(doors, x, y, r.size);

        //check intersection with enemies
        Iterator<Enemy> i = r.enemies.iterator();
        while (i.hasNext()) {
            Enemy enemy = i.next();
            if (enemy.x >= x && enemy.x <= x + r.size || enemy.x + enemy.spriteW >= x && enemy.x + enemy.spriteW <= x + r.size) {
                if (enemy.y >= y && enemy.y <= y + r.size || enemy.y + enemy.spriteH >= y && enemy.y + enemy.spriteH <= y + r.size) {
                    i.remove(); //if any enemy intersects with the door it will be removed
                }
            }
        }
        doorCreated = true;  //usually door is creating with map but in boss room it is creating after boss's death
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
                    SCORE = SCORE + e.id * scoreForEnemy;
                    i.remove();
                    ENEMIES++;
                }
            }
        }
    }

    private void destroyEnemy(Enemy e) {
        if (e.id == 7) { //boss id
            player.health += healthForBoss;
            if (player.health > maxPlayerHealth) player.health = maxPlayerHealth;
            healths = true;
            xStat = (int) e.x + (float) SIZE / 2;
            yStat = (int) e.y + (float) SIZE / 2;
            createDoor();
        } else { //other monsters
            //coordinates of picture with reward
            xStat = (int) e.x + (float) SIZE / 2;
            yStat = (int) e.y + (float) SIZE / 2;

            int rand = (int) (Math.random() * 100);

            //reward - health for player
            if (rand >= minForHealth && rand <= maxForHealth) {
                player.health += healthForEnemy;
                healths = true;
            } else if (rand >= minForDef && rand <= maxForDef) {
                //reward - more defense
                player.def += 1;
                defs = true;
            } else if (rand >= minForAttack && rand <= maxForAttack) {
                //reward - more attack
                player.attack += 5;
                attacks = true;
            }
        }

    }

    private boolean intersectDoor() {
        //check intersection with door
        int px1 = (int) player.x;
        int px2 = (int) player.x + (int) player.spriteW;
        int py1 = (int) player.y;
        int py2 = (int) player.y + (int) player.spriteH;

        int dx1 = door.x;
        int dx2 = door.x + door.size;
        int dy1 = door.y;
        int dy2 = door.y + door.size;

        if (px1 > dx1 && px1 < dx2 || px2 > dx1 && px2 < dx2) {
            return (py1 > dy1 && py1 < dy2 || py2 > dy1 && py2 < dy2 || dy1 < py2 && dy1 > py1 || dy2 < py2 && dy2 > py1);

        }
        return false;


    }

    private void drawStat() {
        //if player gets some reward then special icon will be drawn
        if (healths) {
            mainClass.batch.draw(health, xStat, yStat, (float) SIZE / 2, (float) SIZE / 2, 0, 0, health.getWidth(), health.getHeight(), false, false);
            fpsH++;
            if (fpsH == periodOfDrawingStats) {
                healths = false;
                fpsH = 0;
            }
        }
        if (attacks) {
            mainClass.batch.draw(attack, xStat, yStat, (float) SIZE / 2, (float) SIZE / 2, 0, 0, attack.getWidth(), attack.getHeight(), false, false);
            fpsA++;
            if (fpsA == periodOfDrawingStats) {
                attacks = false;
                fpsA = 0;
            }
        }
        if (defs) {
            mainClass.batch.draw(def, xStat, yStat, (float) SIZE / 2, (float) SIZE / 2, 0, 0, def.getWidth(), def.getHeight(), false, false);
            fpsD++;
            if (fpsD == periodOfDrawingStats) {
                defs = false;
                fpsD = 0;
            }
        }
    }

    private void generate() {
        //when player goes to another level new map generates
        camera.position.x = (float) WIDTH / 2;
        camera.position.y = (float) HEIGHT / 2;
        player.x = camera.position.x;
        player.y = camera.position.y;

        map.generate(maxNumberOfRooms);
        if (LEVEL % 5 != 0) {
            createDoor();
        } else doorCreated = false;
    }

    private void drawHealth() {
        //health of player
        float x = camera.position.x - partOfHealthX;
        float y = camera.position.y + partOfHealthY;
        float xRect = (((float) player.health / 10) * partOfHealthX2);
        mainClass.batch.draw(white, x, y, healthWidth, healthHeight, 1, 1, white.getWidth(), white.getHeight(), false, false);
        mainClass.batch.draw(red, x, y, xRect, healthHeight, 1, 1, red.getWidth(), red.getHeight(), false, false);


    }

    private void endOfGame() {
        camera.position.x = (float) WIDTH / 2;
        camera.position.y = (float) HEIGHT / 2;
        //big label "game over"
        mainClass.batch.draw(gameover, camera.position.x - (float) 2 * WIDTH / 5,
                (camera.position.y), (float) 4 * WIDTH / 5, (float) HEIGHT / 3, 0, 0,
                gameover.getWidth(), gameover.getHeight(), false, false);

        //text with the game score
        String finalScore = "Your score is " + SCORE;
        bitmapFont.getData().setScale(fontScale);
        layout.setText(bitmapFont, finalScore);
        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        bitmapFont.draw(mainClass.batch, layout, camera.position.x - layout.width / 2, camera.position.y - layout.height);

    }

    private void getParamsForButtons() {
        //position and size of exit button
        float size = sizeExitButton;

        exit.setHeight(size);
        exit.setWidth(size);

        float x = camera.position.x + (float) WIDTH / 2 - size;
        float y = camera.position.y + (float) HEIGHT / 2 - size;
        exit.setPosition(x, y);

    }

    private void paramAlert() {
        //position and size of alert's components
        //params for alert
        float xQ, yQ, wQ, hQ;

        //params button yes
        float xY, yY, wY, hY;

        //params button no
        float xN, yN, wN, hN;

        wQ = (float) 3 * WIDTH / 4;
        hQ = wQ / 4;

        xQ = camera.position.x - wQ / 2;
        yQ = camera.position.y - hQ / 2;

        wY = wQ / 4;
        hY = wY / 4;

        wN = wY;
        hN = hY;

        xY = xQ + wQ / 2 - yesCoef * wY;
        yY = yQ + wY / 5;

        xN = xQ + wQ / 2 + noCoef * wN;
        yN = yY;

        yes.setSize(wY, hY);
        yes.setPosition(xY, yY);

        no.setSize(wN, hN);
        no.setPosition(xN, yN);

        quest.setPosition(xQ, yQ);
        quest.setSize(wQ, hQ);

    }

    private void drawAlert() {
        no.setVisible(true);
        yes.setVisible(true);
        quest.setVisible(true);
    }


}
