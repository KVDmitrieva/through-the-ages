package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.sql.Database;
import com.mygdx.game.MainClass;
import com.mygdx.game.Tools.MyButton;
import com.mygdx.game.Tools.MyDatabase;

import java.util.ArrayList;

public class ScoreBoard implements Screen {

    private MainClass mainClass;
    private Texture background, title;
    private int width, height;


    //params for background image
    private float xBG, yBG;
    private int sizeBG;

    //params for logo
    private float xLogo, yLogo;
    private int widthLogo, heightLogo;

    private MyButton back;
    private Stage stage;

    ScrollPane scrollPane;
    List<String> list;
    Skin skin;
    TextureAtlas atlas;MyDatabase db;

    ScoreBoard (final MainClass mainClass){
        this.mainClass = mainClass;

        background = new Texture("main1.png");
        title = new Texture("score.png");

        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();

        getBGPosition();
        getParamsForLogo();

        back = new MyButton("skinex.atlas","skinex.json");
        back.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y,
                                      int pointer, int button) {
                mainClass.setScreen(new MainMenu(mainClass));
                return true;
            }

        });
        getParamsForButtons();


        atlas = new TextureAtlas("font.atlas");
        skin = new Skin(Gdx.files.internal("font.json"), atlas);

        list = new List<String>(skin);

        String[] strings = new String[20];
         db = new MyDatabase();
        ArrayList<String> str = db.selectAll();
        int k=0;
        String[] st = new String[str.size()];
        for (String s:str){
            st[k] = s;
            k++;
        }

        list.setItems(st);
        scrollPane = new ScrollPane(list);
        scrollPane.scrollTo(0,0,width, height);

        scrollPane.setWidth((float)width/2);
        //scrollPane.setHeight(height);
        //scrollPane.setHeight((float)height/2);
        //scrollPane.setBounds(0,0,(float)width/2,(float)height/2);
        scrollPane.scaleBy(1);
        scrollPane.setTransform(true);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition(width/100,
                (float)height/2);
        scrollPane.setScrollPercentY(100);



        stage = new Stage(mainClass.screenPort);
        stage.addActor(back);
        stage.addActor(scrollPane);
        Gdx.input.setInputProcessor(stage);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainClass.batch.begin();
        mainClass.batch.draw(background, xBG, yBG, sizeBG, sizeBG);
        mainClass.batch.draw(title, xLogo, yLogo, widthLogo, heightLogo);
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
        background.dispose();

    }

    private void getBGPosition(){
        if (width>height) {
            sizeBG = width;
            xBG = 0;
            yBG = 0-((1-(float)height/(float)width)*sizeBG)/2;
        } else {
            sizeBG = height;
            yBG = 0;
            xBG =  0-((1-(float)width/(float)height)*sizeBG)/2;
        }
    }

    private void getParamsForLogo(){
        if(width>height){
            widthLogo = 4*(width/5);
            heightLogo = height/4;


        } else{
            widthLogo = 19*(width/20);
            heightLogo = height/5;


        }
        yLogo = 7*(float)height/9;
        xLogo = (float)width/2-(float)widthLogo/2;


    }

    private void getParamsForButtons(){

        float height = (float)this.width/10;
        float width = (float)this.width/2;

        back.setHeight(height);
        back.setWidth(width);

        back.setPosition((float)this.width/2-width/2, height);
    }
}
