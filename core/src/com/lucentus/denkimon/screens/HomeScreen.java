package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lucentus.denkimon.Denkimon;

import java.net.Socket;


/**
 * The Main Screen of the Game
 * Displayed once logged in
 * Allows a user to adjust their Denkimon in their party or queue for a game
 */
public class HomeScreen implements Screen {

    // Properties
    private final Denkimon game;
    private OrthographicCamera camera;

    // Socket Properties
    private Socket socket;

    // UI Properties
    private Skin skin;
    private Stage stage;
    private Table table;


    /*
     * Constructors
     */

    public HomeScreen(final Denkimon game) {
        this.game = game;

        // Initialize Scene2d UI components
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true);

        // Initialize skin components
        skin = new Skin();
    }


    /*
     * Override Methods
     */

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
