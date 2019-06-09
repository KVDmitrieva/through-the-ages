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

import static com.mygdx.game.Tools.Constants.backgroundX;
import static com.mygdx.game.Tools.Constants.buttonHeight;
import static com.mygdx.game.Tools.Constants.buttonWidth;
import static com.mygdx.game.Tools.Constants.buttonX;
import static com.mygdx.game.Tools.Constants.logoHeight;
import static com.mygdx.game.Tools.Constants.logoWidth;
import static com.mygdx.game.Tools.Constants.logoX;
import static com.mygdx.game.Tools.Constants.menuLogoY;
import static com.mygdx.game.Tools.Constants.sizeOfBackground;


public class MainMenu implements Screen {
    private MainClass mainClass;
    private Texture background, title;
    private MyButton score, game;
    private Stage stage;


    //params for background image
    private float xBG = backgroundX;
    private int sizeBG = sizeOfBackground;

    //params for logo
    private float xLogo = logoX, yLogo = menuLogoY;
    private int widthLogo = logoWidth, heightLogo = logoHeight;


    public MainMenu(final MainClass mainClass) {
        this.mainClass = mainClass;


        background = new Texture("main1.png");
        title = new Texture("logo.png");

        stage = new Stage(mainClass.screenPort);

        game = new MyButton("play");
        game.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                mainClass.setScreen(new GameScreen(mainClass)); //move to game
                return true;
            }

        });


        score = new MyButton("score");
        score.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                mainClass.setScreen(new ScoreBoard(mainClass)); //move to statistic
                return true;
            }

        });


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
        mainClass.batch.draw(background, xBG, 0, sizeBG, sizeBG);
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


    private void getParamsForButtons() {
        //set position and size for button
        float height = buttonHeight;
        float width = buttonWidth;

        game.setHeight(height);
        game.setWidth(width);

        score.setHeight(height);
        score.setWidth(width);

        game.setPosition(buttonX, yLogo - height);
        score.setPosition(buttonX, yLogo - 2.5f * height);
    }


}
