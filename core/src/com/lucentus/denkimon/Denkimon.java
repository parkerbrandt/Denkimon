package com.lucentus.denkimon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lucentus.denkimon.screens.MenuScreen;


/**
 * Main Game Class
 * Holds necessary variables and begins program by calling main menu screen
 */
public class Denkimon extends Game {

	/*
	 * Static Class Members
	 */
	public static final int VIEWPORT_HEIGHT = 1080;
	public static final int VIEWPORT_WIDTH = 1920;


	/*
	 * Variables
	 */
	public ShapeRenderer shape;
	public SpriteBatch batch;
	public BitmapFont font;


	/*
	 * Override Methods
	 */
	
	@Override
	public void create () {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();

		// Set the main menu screen
		this.setScreen(new MenuScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
