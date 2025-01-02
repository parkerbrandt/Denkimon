package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    // Static Properties
    public static final int GRID_WIDTH = 5;
    public static final int GRID_HEIGHT = 5;

    // Enumerations
    private enum BATTLE_PHASE {
        START,
        PLACEMENT,
        FIGHT,
        REWARDS
    };

    // Properties
    private final DenkimonGame game;

    private OrthographicCamera camera;
    private float stateTime;

    private Player bluePlayer;
    private Player redPlayer;

    private BATTLE_PHASE currentPhase = BATTLE_PHASE.START;

    private Denkimon[][] battleField = new Denkimon[5][5];


    /*
     * Constructors
     */

    public BattleScreen(final DenkimonGame game) {
        this.game = game;
    }

    public BattleScreen(final DenkimonGame game, Player bluePlayer, Player redPlayer) {
        this.game = game;
        this.bluePlayer = bluePlayer;
        this.redPlayer = redPlayer;

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
        for (Denkimon denkimon : bluePlayer.getDenkimon()) {

        }

        // TODO: Render each of the Red Player's Denkimon
        for (Denkimon denkimon : redPlayer.getDenkimon()) {

        }

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

        // Divide the viewport area into a grid
        float square_width = (float) DenkimonGame.VIEWPORT_WIDTH / GRID_WIDTH;
        float square_height = (float) DenkimonGame.VIEWPORT_HEIGHT / GRID_HEIGHT;

        game.shape.begin(ShapeRenderer.ShapeType.Line);
        game.shape.setColor(Color.GRAY);

        // TODO: Draw a grid of squares for the battlefield
        for(int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                game.shape.rect(i * square_width / 2, j * square_height / 2, square_width, square_height);
            }
        }

        game.shape.end();
    }


}
