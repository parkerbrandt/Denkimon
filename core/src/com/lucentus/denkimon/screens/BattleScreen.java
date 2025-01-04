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
        PLANNING,
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

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DenkimonGame.VIEWPORT_WIDTH, DenkimonGame.VIEWPORT_HEIGHT);
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
//        for (Denkimon denkimon : bluePlayer.getDenkimon()) {
//
//        }
//
//        // TODO: Render each of the Red Player's Denkimon
//        for (Denkimon denkimon : redPlayer.getDenkimon()) {
//
//        }

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
     * There will be an additional square where the player will be located
     *
     * NOTE: game.shape.rect() draws rectangles using the point at the lower left of the square
     */
    private void drawBattlefield() {

        double width = DenkimonGame.VIEWPORT_WIDTH;
        double height = DenkimonGame.VIEWPORT_HEIGHT;

        game.shape.begin(ShapeRenderer.ShapeType.Line);
        game.shape.setColor(Color.GRAY);

        // Draw each player's square

        // Player 1 square
        // Bottom-left Point at (0.025w, 0.45h), side-length = 0.50w
        game.shape.rect((float) (0.025 * width), (float) (0.45 * height),
                (float) (0.05 * width), (float) (0.05 * width));

        // Player 2 square
        // Bottom-left Point at (w - 0.075w, 0.45h), side-length = 0.50w
        game.shape.rect((float) (width - 0.075 * width), (float) (0.45 * height),
                (float) (0.05 * width), (float) (0.05 * width));

        // Draw all the squares on the battlefield
        // 32 squares total, 16 per player
        // side-length = 0.10w
        // TODO: Add padding b/w both player's sides and add padding b/w cells
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                game.shape.rect((float)(0.10 * width + 0.1 * width * i), (float) (0.05 * height + 0.1 * width * j),
                        (float) (0.1 * width), (float) (0.1 * width));
            }
        }

        game.shape.end();
    }


}
