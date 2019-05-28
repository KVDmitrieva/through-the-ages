package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.MainClass;

public class MainMenu implements Screen {
    private MainClass mainClass;
    private Texture background, title, butPlay, butScore;
    private int width, height;
    private ImageButton game, score;
    private Stage stage;



    //params for background image
        private float xBG, yBG;
        private int sizeBG;

    //params for logo
        private float xLogo, yLogo;
        private int widthLogo, heightLogo;




   public MainMenu (MainClass mainClass){
        this.mainClass = mainClass;

       background = new Texture("main1.png");
       title = new Texture("logo.png");

       width = Gdx.app.getGraphics().getWidth();
       height = Gdx.app.getGraphics().getHeight();

       butScore = new Texture("stat.png");
       score = new ImageButton(new SpriteDrawable(new Sprite(butScore)));

       butPlay = new Texture("play.png");
       game = new ImageButton(new SpriteDrawable(new Sprite(butPlay)));

       stage = new Stage(mainClass.screenPort);
       Gdx.input.setInputProcessor(stage);


       getBGPosition();
       getParamsForLogo();
       getParamsForButtons();

       stage.addActor(game);
       stage.addActor(score);



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
       title.dispose();
       butPlay.dispose();
       butScore.dispose();
       stage.dispose();

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
        yLogo = 4*(float)height/9;
        xLogo = (float)width/2-(float)widthLogo/2;


    }

    private void getParamsForButtons(){
     //  if(height>width){

            float height = 3*(float)this.height/5;
            float width = (float)this.width/2;

           game.setHeight(height);
           game.setWidth(width);

           score.setHeight(height);
           score.setWidth(width);

           game.setPosition((float)this.width/2-width/2, yBG-(float)this.height/20-height);
           score.setPosition((float)this.width/2-width/2, yBG-2*((float)this.height/20-height));
       //}
    }




}
