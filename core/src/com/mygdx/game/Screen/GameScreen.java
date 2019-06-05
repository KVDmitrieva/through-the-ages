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


public class GameScreen implements Screen{

    public static int LEVEL;
    public static int SCORE ;
    public static int ENEMIES;


    private MainClass mainClass;
    private Texture floor, d1, d2, wall, character, attack, health,
            def, doors, white, red, gameover;
    private int width, height, size, velocity;
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
    private boolean healths = false, attacks = false, defs = false;
    private int fpsH = 0, fpsA = 0, fpsD = 0;
    private MyButton exit;

     GameScreen(final MainClass mainClass) {
        this.mainClass = mainClass;
        SCORE = 0;
        LEVEL = 1;
        ENEMIES = 0;


        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();
        bitmapFont = new BitmapFont();

        camera = new OrthographicCamera(width, height);
        camera.position.x = (float) width / 2;
        camera.position.y = (float) height / 2;


        size = width / 5;
        velocity = width / 100;
        joyStick = new JoyStick(30, 30);
        layout = new GlyphLayout(bitmapFont, "hello");
        Gesture gesture = new Gesture(joyStick);

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

        map = new Map(width, height, floor, size, d1, d2, wall);
        map.generate(8);
        createDoor();

        player = new Characters(character, (float)(width / 2),(float) (height / 2), 7, 6, 7, size);

        exit = new MyButton("exitbut.atlas", "exitbut.json");
        getParamsForButtons();
        exit.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y,
                                      int pointer, int button) {
                MyDatabase db = new MyDatabase();
                if(SCORE!=0)db.pushData();
                mainClass.setScreen(new MainMenu(mainClass));
                return true;
            }

        });
        stage = new Stage(mainClass.screenPort, mainClass.batch);
        stage.addActor(joyStick);
        stage.addActor(exit);
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
        if (intersectDoor()) {generate(); LEVEL++; SCORE+=50;}
        moveCamera();
        camera.update();
        mainClass.batch.setProjectionMatrix(camera.combined);
        mainClass.batch.begin();
        if(player.health>0){
        map.drawMap(mainClass.batch);
        door.drawObject(mainClass.batch);
        onDrawEnemy();
        drawStat();
        drawHealth();
        player.draw(mainClass.batch);}else
        endOfGame();
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
                if (((player.x + player.spriteW <= e.x + e.spriteW&&player.x + player.spriteW >= e.x)||
                        (player.x  <= e.x + e.spriteW&&player.x>= e.x))
                        && (((player.y + 3*player.spriteH / 4 <= e.y + e.spriteH)
                        && player.y + 3*player.spriteH / 4 >= e.y)||(player.y  <= e.y + e.spriteH)
                        && player.y >= e.y)) {
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
                    SCORE = SCORE+ e.id * 10;
                    i.remove();
                    ENEMIES++;
                }
            }
        }
    }

    private void destroyEnemy(Enemy e) {

        xStat = (int) e.x+(float)size/2;
        yStat = (int) e.y+(float)size/2;

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
            //if (py1 > dy1 && py1 < dy2 || py2 > dy1 && py2 < dy2) {
                return (py1 > dy1 && py1 < dy2 || py2 > dy1 && py2 < dy2) ;
           // }
        } return false;


    }

    private void drawStat(){
        if(healths){
            mainClass.batch.draw(health, xStat, yStat, (float)size/2, (float)size/2, 0,0,health.getWidth(),health.getHeight(), false,false);
            fpsH++;
            if(fpsH==10){
                healths = false;
                fpsH = 0;
            }
        }
        if(attacks){
            mainClass.batch.draw(attack, xStat, yStat, (float)size/2, (float)size/2, 0,0,attack.getWidth(),attack.getHeight(), false,false);
            fpsA++;
            if(fpsA==10){
                attacks = false;
                fpsA = 0;
            }
        }
        if(defs){
            mainClass.batch.draw(def, xStat, yStat, (float)size/2, (float)size/2, 0,0,def.getWidth(),def.getHeight(), false,false);
            fpsD++;
            if(fpsD==10){
                defs = false;
                fpsD = 0;
            }
        }
    }

    private void generate(){
        camera.position.x = (float) width / 2;
        camera.position.y = (float) height / 2;
        player.x = camera.position.x;
        player.y = camera.position.y;

        map.generate(8);
        createDoor();
    }

    private void drawHealth(){
        float x = camera.position.x- (float)width/2;
        float y = camera.position.y + (float) height/2.5f;
        float xRect = (((float)player.health/10)*(float)width/600);
        mainClass.batch.draw(white, x,y,(float)width/6, (float)height/25, 1,1,white.getWidth(), white.getHeight(), false,false);
        mainClass.batch.draw(red, x,y,xRect, (float)height/25, 1,1,red.getWidth(), red.getHeight(), false,false);


    }

    private void endOfGame(){
        camera.position.x = (float) width / 2;
        camera.position.y = (float) height / 2;
            mainClass.batch.draw(gameover, camera.position.x-(float)2*width/5,
                    (camera.position.y), (float)4*width/5,(float)height/3,0,0,
                    gameover.getWidth(), gameover.getHeight(), false, false);

            String finalScore = "Your score is "+ SCORE;
            bitmapFont.getData().setScale((float)size/50);
            layout.setText(bitmapFont, finalScore);
            bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


            bitmapFont.draw(mainClass.batch,layout, camera.position.x-layout.width/2, camera.position.y-layout.height);

    }

    private void getParamsForButtons(){

        float width = (float)this.width/10;

        exit.setHeight(width);
        exit.setWidth(width);

        float x = camera.position.x + (float) this.width/2 - width;
        float y = camera.position.y + (float)this.height/2 - width;
        exit.setPosition(x, y);

    }



}
