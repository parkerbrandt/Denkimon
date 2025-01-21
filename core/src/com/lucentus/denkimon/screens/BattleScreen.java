package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lucentus.denkimon.DenkimonGame;
import com.lucentus.denkimon.battle.field.Battlefield;
import com.lucentus.denkimon.battle.field.BattlefieldSquare;
import com.lucentus.denkimon.entities.Denkimon;
import com.lucentus.denkimon.users.Player;


/**
 * Screen to display battles between two Players
 * Each player can place their 5 Denkimon from their party onto the battlefield onto any grid
 * The Denkimon will move on their own once each player is ready
 */
public class BattleScreen implements Screen {

    // Static Properties
    public static final int GRID_WIDTH = 4;
    public static final int GRID_HEIGHT = 4;

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

    private Player player;

    private Player bluePlayer;
    private String bluePlayerName = "Blue";
    private int bluePlayerScore = 0;

    private Player redPlayer;
    private String redPlayerName = "Red";
    private int redPlayerScore = 0;

    private Battlefield battlefield;

    private BATTLE_PHASE currentPhase = BATTLE_PHASE.PLANNING;


    /*
     * Constructors
     */

    /**
     * Initializes the Battle and Battlefield for two Players
     */
    public BattleScreen(final DenkimonGame game, Player player) {
        this.game = game;
        this.battlefield = new Battlefield(game);
        this.player = player;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DenkimonGame.VIEWPORT_WIDTH, DenkimonGame.VIEWPORT_HEIGHT);

        // Determine locations for the battlefield grids
        // 32 squares total, 16 per player
        // side-length = 0.10w
        double width = DenkimonGame.VIEWPORT_WIDTH;
        double height = DenkimonGame.VIEWPORT_HEIGHT;

        // Blue Side
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                float x = (float) (0.10 * width + 0.1 * width * i);
                float y = (float) (0.05 * height + 0.1 * width * j);

                BattlefieldSquare square = new BattlefieldSquare(i, j, x, y, true);
                battlefield.addSquare(square);
            }
        }

        // Red Side
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                float x = (float) (0.525 * width + 0.1 * width * i);
                float y = (float) (0.05 * height + 0.1 * width * j);

                BattlefieldSquare square = new BattlefieldSquare(i, j, x, y, false);
                battlefield.addSquare(square);
            }
        }
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

        // Render the scoreboard at the top of the screen
        drawScoreboard();

        // Render the battlefield
        drawBattlefield();

        // TODO: Render the players and their Denkimon

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
     * Draw the scoreboard at the top of the screen
     */
    private void drawScoreboard() {

        double width = DenkimonGame.VIEWPORT_WIDTH;
        double height = DenkimonGame.VIEWPORT_HEIGHT;

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        // Write each player's score above their side
        game.font.draw(game.batch, bluePlayerName + "'s Score: " + bluePlayerScore, (int) (0.1 * width), (int) (0.9 * height));
        game.font.draw(game.batch, redPlayerName + "'s Score: " + redPlayerScore, (int) (width - (0.1 * width + redPlayerName.length() + 8)), (int) (0.9 * height));

        // Write the current phase at the top of the screen
        String displayStr;

        switch (currentPhase) {
            case START:
                displayStr = "GAME START";
                game.font.draw(game.batch, displayStr, (int) (0.45 * width - displayStr.length()), (int) (0.9 * height));
                break;

            case PLANNING:
                displayStr = "PLANNING PHASE";
                game.font.draw(game.batch, displayStr, (int) (0.5 * width - displayStr.length()), (int) (0.9 * height));
                break;

            case FIGHT:
                displayStr = "FIGHT!!";
                game.font.draw(game.batch, displayStr, (int) (0.5 * width - displayStr.length()), (int) (0.9 * height));
                break;

            case REWARDS:
                displayStr = "REWARDS";
                game.font.draw(game.batch, displayStr, (int) (0.5 * width - displayStr.length()), (int) (0.9 * height));
                break;

            default:
                displayStr = "ERROR - Cannot Determine Phase";
                game.font.draw(game.batch, displayStr, (int) (0.5 * width - displayStr.length()), (int) (0.9 * height));
                break;
        }

        game.batch.end();
    }

    /**
     * Draw a grid of squares to represent the Denkimon on the battlefield
     * There will be an additional square where the player will be located
     * NOTE: game.shape.rect() draws rectangles using the point at the lower left of the square
     */
    private void drawBattlefield() {

        // TODO: Draw a background

        double width = DenkimonGame.VIEWPORT_WIDTH;
        double height = DenkimonGame.VIEWPORT_HEIGHT;

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.shape.begin(ShapeRenderer.ShapeType.Line);
        game.shape.setColor(Color.GRAY);

        // Draw each player's square

        // Player 1 square
        // Bottom-left Point at (0.025w, 0.45h), side-length = 0.05w
        game.shape.rect((float) (0.025 * width), (float) (0.45 * height),
                (float) (0.05 * width), (float) (0.05 * width));

        // Player 2 square
        // Bottom-left Point at (w - 0.075w, 0.45h), side-length = 0.50w
        game.shape.rect((float) (width - 0.075 * width), (float) (0.45 * height),
                (float) (0.05 * width), (float) (0.05 * width));


        // Draw the battlefield
        battlefield.draw();

        game.shape.end();

        game.batch.end();
    }

}
