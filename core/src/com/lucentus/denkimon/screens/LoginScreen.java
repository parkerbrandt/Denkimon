package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lucentus.denkimon.Denkimon;

/**
 * The Log-In Screen
 * Allows a user to either Login or Register with a username and password
 */
public class LoginScreen implements Screen {

    // Properties
    private final Denkimon game;
    private OrthographicCamera camera;


    /*
     * Constructors
     */
    public LoginScreen(final Denkimon game) {
        this.game = game;

        // Initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Denkimon.VIEWPORT_WIDTH, Denkimon.VIEWPORT_HEIGHT);
    }

    @Override
    public void show() { }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
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
