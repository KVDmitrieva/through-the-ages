package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainClass;
import com.mygdx.game.Tools.MyButton;



public class MainMenu implements Screen {
    private MainClass mainClass;
    private Texture background, title;
    private int width, height;
    private MyButton score, game;
    private Stage stage;


    //params for background image
        private float xBG, yBG;
        private int sizeBG;

    //params for logo
        private float xLogo, yLogo;
        private int widthLogo, heightLogo;





   public MainMenu (final MainClass mainClass){
       this.mainClass = mainClass;


       background = new Texture("main1.png");
       title = new Texture("logo.png");

       width = Gdx.app.getGraphics().getWidth();
       height = Gdx.app.getGraphics().getHeight();

       stage = new Stage(mainClass.screenPort);

       game = new MyButton("play");
       game.addListener(new InputListener() {
           public boolean touchDown (InputEvent event, float x, float y,
                                     int pointer, int button) {
                    mainClass.setScreen(new GameScreen(mainClass)); //move to game
               return true;
           }

       });


       score = new MyButton("score");
       score.addListener(new InputListener() {
           public boolean touchDown (InputEvent event, float x, float y,
                                     int pointer, int button) {
               mainClass.setScreen(new ScoreBoard(mainClass)); //move to statistic
               return true;
           }

       });


       getBGPosition();
       getParamsForLogo();
       getParamsForButtons();

       stage.addActor(game);
       stage.addActor(score);
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
       title.dispose();
       stage.dispose();

    }

    private void getBGPosition(){
       //set position and size for background
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
        //set position and size for title
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
        //set position and size for button
            float height = (float)this.width/10;
            float width = (float)this.width/2;

            game.setHeight(height);
            game.setWidth(width);

            score.setHeight(height);
            score.setWidth(width);

            game.setPosition((float)this.width/2-width/2, yLogo-height);
            score.setPosition((float)this.width/2-width/2, yLogo-2.5f*height);
    }




}
