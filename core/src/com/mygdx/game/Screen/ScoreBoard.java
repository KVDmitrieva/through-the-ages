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
import com.mygdx.game.MainClass;
import com.mygdx.game.Tools.MyButton;
import com.mygdx.game.Tools.MyDatabase;

import java.util.ArrayList;

import static com.mygdx.game.Tools.Constants.HEIGHT;
import static com.mygdx.game.Tools.Constants.WIDTH;
import static com.mygdx.game.Tools.Constants.backgroundX;
import static com.mygdx.game.Tools.Constants.buttonHeight;
import static com.mygdx.game.Tools.Constants.buttonWidth;
import static com.mygdx.game.Tools.Constants.buttonX;
import static com.mygdx.game.Tools.Constants.logoHeight;
import static com.mygdx.game.Tools.Constants.logoWidth;
import static com.mygdx.game.Tools.Constants.logoX;
import static com.mygdx.game.Tools.Constants.scoreLogoY;
import static com.mygdx.game.Tools.Constants.scrollPaneHeight;
import static com.mygdx.game.Tools.Constants.scrollPaneScale;
import static com.mygdx.game.Tools.Constants.scrollPaneWidth;
import static com.mygdx.game.Tools.Constants.scrollPaneX;
import static com.mygdx.game.Tools.Constants.scrollPaneY;
import static com.mygdx.game.Tools.Constants.sizeOfBackground;

public class ScoreBoard implements Screen {

    private MainClass mainClass;
    private Texture background, title;


    //params for background image
    private float xBG = backgroundX;
    private int sizeBG = sizeOfBackground;

    //params for logo
    private float xLogo = logoX, yLogo = scoreLogoY;
    private int widthLogo = logoWidth, heightLogo = logoHeight;

    private MyButton back;
    private Stage stage;


    ScoreBoard(final MainClass mainClass) {
        this.mainClass = mainClass;

        background = new Texture("main1.png");
        title = new Texture("score.png");


        back = new MyButton("back");
        back.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                //go to main menu
                mainClass.setScreen(new MainMenu(mainClass));
                return true;
            }

        });
        getParamsForButtons();

        TextureAtlas atlas = new TextureAtlas("font.atlas");
        Skin skin = new Skin(Gdx.files.internal("font.json"), atlas);

        //list with statistic of all games
        List list = new List<String>(skin);
        list.setHeight(HEIGHT);
        list.setWidth(scrollPaneWidth);

        MyDatabase db = new MyDatabase();
        ArrayList<String> str = db.selectAll();
        int k = 0;
        String[] st = new String[str.size()];
        for (String s : str) {
            st[k] = s;
            k++;
        }

        list.setItems(st);

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setWidth(scrollPaneWidth);
        scrollPane.setHeight(scrollPaneHeight);
        scrollPane.scaleBy(scrollPaneScale);
        scrollPane.setPosition(scrollPaneX, scrollPaneY);
        //scrollPane.setScrollPercentY(100);


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

    }

    private void getParamsForButtons() {
        //set position and size for button
        float height = buttonHeight;
        float width = buttonWidth;

        back.setHeight(height);
        back.setWidth(width);

        back.setPosition(buttonX, height);
    }
}
