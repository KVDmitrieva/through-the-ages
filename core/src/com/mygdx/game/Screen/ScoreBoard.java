package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MainClass;

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


    public ScoreBoard (MainClass mainClass){
        this.mainClass = mainClass;

        background = new Texture("main1.png");
        title = new Texture("score.png");

        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();

        getBGPosition();
        getParamsForLogo();

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
    }

    @Override
    public void resize(int width, int height) {
        // this.width = Gdx.app.getGraphics().getWidth();
        // this.height = Gdx.app.getGraphics().getHeight();
        getBGPosition();
        getParamsForLogo();


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



}
