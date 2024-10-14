package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lucentus.denkimon.Denkimon;

/**
 * The Main Title Screen for the Denkimon game
 * Allows users to choose to either login or find a game (will be removed when login functionality works)
 */
public class TitleScreen implements Screen {

    // Properties
    private final Denkimon game;
    private OrthographicCamera camera;

    /*
     * Constructors
     */

    /**
     * Main constructor to initialize camera for this screen
     * @param game the main game class
     */
    public TitleScreen(final Denkimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Denkimon.VIEWPORT_WIDTH, Denkimon.VIEWPORT_HEIGHT);
    }


    /*
     * Override Methods
     */

    @Override
    public void show() { }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.begin();
        game.font.draw(game.batch, "DENKIMON", 100, Denkimon.VIEWPORT_HEIGHT - 200);
        game.font.draw(game.batch, "Touch Anywhere to Begin", 100, Denkimon.VIEWPORT_HEIGHT - 300);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new BattleScreen());
            dispose();
        }
    }

    @Override
    public void resize(int i, int i1) {

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
}
