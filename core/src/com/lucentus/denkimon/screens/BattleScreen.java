package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lucentus.denkimon.DenkimonGame;
import com.lucentus.denkimon.entities.Denkimon;
import com.lucentus.denkimon.users.Player;


/**
 * Screen to display battles between two Players
 * Each player can place their 5 Denkimon from their party onto the battlefield onto any grid
 * The Denkimon will move on their own once each player is ready
 */
public class BattleScreen implements Screen {

    // Properties
    private final DenkimonGame game;

    private OrthographicCamera camera;
    private float stateTime;

    private Player bluePlayer;
    private Player redPlayer;

    private Denkimon[][] battleField = new Denkimon[5][5];


    /*
     * Constructors
     */

    public BattleScreen(final DenkimonGame game) {
        this.game = game;
    }

    public BattleScreen(final DenkimonGame game, Player bluePlayer, Player redPlayer) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DenkimonGame.VIEWPORT_WIDTH, DenkimonGame.VIEWPORT_HEIGHT);
    }


    /*
     * Override Methods
     */

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0.2f, 0, 1);

        // Increment state time
        stateTime += Gdx.graphics.getDeltaTime();

        // Update camera
        camera.update();


        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        // Render the battlefield
        drawBattlefield();

        // TODO: Render each of the Blue Player's Denkimon

        // TODO: Render each of the Red Player's Denkimon

        game.batch.end();
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


    /*
     * Drawing methods
     */

    /**
     * Draw a grid of squares to represent the Denkimon on the battlefield
     */
    private void drawBattlefield() {

    }


}
