package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screen.MainMenu;

public class MainClass extends Game {
    public SpriteBatch batch;
    public Viewport screenPort;


    @Override
    public void create() {
        batch = new SpriteBatch();
        screenPort = new ScreenViewport();
        this.setScreen(new MainMenu(this));

    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
