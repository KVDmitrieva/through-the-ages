package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screen.GameScreen;
import com.mygdx.game.Screen.MainMenu;
import com.mygdx.game.Screen.ScoreBoard;

public class MainClass extends Game {
	public SpriteBatch batch;
	public Viewport screenPort;
	public ShapeRenderer shape;


	@Override
	public void create () {
		batch = new SpriteBatch();
		screenPort = new ScreenViewport();
		shape = new ShapeRenderer();
		this.setScreen(new GameScreen(this));

	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
