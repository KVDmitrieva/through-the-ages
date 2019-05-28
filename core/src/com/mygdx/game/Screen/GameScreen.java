package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MainClass;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Tools.JoyStick;


public class GameScreen implements Screen {

    private MainClass mainClass;
    private Texture floor, d1 , d2;
    private int width, height, size;
    Map map;
    OrthographicCamera camera;
    JoyStick joyStick;
    Stage stage;

    public GameScreen(MainClass mainClass){
        this.mainClass = mainClass;

        joyStick = new JoyStick(30, 30);


        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();
        camera = new OrthographicCamera(width, height);

        stage = new Stage(mainClass.screenPort, mainClass.batch);
        stage.addActor(joyStick);
        Gdx.input.setInputProcessor(stage);
        size = 50;

        floor = new Texture("floor1.png");
        d1 = new Texture("dino1.png");
        d2 = new Texture("dino2.png");

        map = new Map(width,height, floor, size, d1, d2);
        map.generate(5);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
     //   mainClass.batch.setProjectionMatrix(camera.combined);
        //mainClass.batch.begin();
        map.drawMap(mainClass.batch, mainClass.shape);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //mainClass.batch.end();
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

    }


   private void moveCamera(float x, float y){
        camera.translate(x,y);
   }

}
