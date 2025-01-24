package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    public enum BATTLE_PHASE {
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
    private int selectedMon = 0;

    // UI Properties
    private Button readyButton;


    // Planning Phase Properties
    private boolean hasSelected = false;
    private BattlefieldSquare lastSquareHovered = null;


    /*
     * Constructors
     */

    /**
     * Initializes the Battle and Battlefield for two Players
     */
    public BattleScreen(final DenkimonGame game, Player player) {
        this.game = game;
        this.battlefield = new Battlefield(game);

        // TODO: Assign both players and what side they are on randomly
        this.player = player;
        bluePlayer = player;
        player.setBlueside(true);

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

        // Render the players in their squares
        // NOTE: Only render enemy player after planning phase
        if (this.player.isBlueside() || this.currentPhase != BATTLE_PHASE.PLANNING) {
            // Draw the blue player

        }

        if (!this.player.isBlueside() || this.currentPhase != BATTLE_PHASE.PLANNING) {
            // Draw the red player

        }

        // For the Planning Phase, display the denkimon in a table and allow user to drag them to the board
        if (this.currentPhase == BATTLE_PHASE.PLANNING) {

            // TODO: Display number of each square to the player

            float partyX = (float) (player.isBlueside() ? 0.8 * DenkimonGame.VIEWPORT_WIDTH : 0.2 * DenkimonGame.VIEWPORT_WIDTH);
            float partyY = (float) (0.2 * DenkimonGame.VIEWPORT_HEIGHT);

            float width = Denkimon.DENKIMON_SPRITE_SIZE;

            // TODO: Add toggle for showing each mon's hitbox

            for (Denkimon denkimon : player.getDenkimon()) {
                // Draw a rectangle around the Denkimon
                game.shape.begin(ShapeRenderer.ShapeType.Line);
                game.shape.setColor(Color.BLACK);
                game.shape.rect(partyX, partyY, width, width);
                game.shape.end();

                // Display the Denkimon in its box unless the player is clicking & dragging the mon
                if (!Gdx.input.isTouched() || player.getDenkimon().indexOf(denkimon) != selectedMon) {
                    denkimon.render(camera, stateTime, partyX, partyY);
                    partyY += (float) (0.1 * DenkimonGame.VIEWPORT_WIDTH);

                    // If the user has moved a denkimon before but lets go, if the mon is in a square
                    if (lastSquareHovered != null) {
                        denkimon.render(camera, stateTime, lastSquareHovered.getHitbox().x, lastSquareHovered.getHitbox().y);
                        selectedMon += 1;
                    }

                } else {
                    // Check if the Denkimon is dragged over the square and if so, color it blue
                    for (BattlefieldSquare square : battlefield.getBluePlayerField()) {
                        if (square.getHitbox().overlaps(denkimon.getHitbox())){
                            // Draw a full blue square over the field square
                            game.shape.begin(ShapeRenderer.ShapeType.Filled);
                            game.shape.setColor(Color.BLUE);
                            game.shape.rect(square.getHitbox().x, square.getHitbox().y, BattlefieldSquare.WIDTH, BattlefieldSquare.WIDTH);
                            game.shape.end();

                            lastSquareHovered = square;
                        }
                    }

                    // Draw the denkimon where it is dragged
                    denkimon.render(camera, stateTime, Gdx.input.getX(), DenkimonGame.VIEWPORT_HEIGHT - Gdx.input.getY());
                }

                // TODO: Allow player to input a number to move selected mon
                if (!Gdx.input.isTouched()) {
                    if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                        // Set Denkimon to square 1
                        this.currentPhase = BATTLE_PHASE.FIGHT;
                    }
                }
            }
        } else if (this.currentPhase == BATTLE_PHASE.FIGHT) {

        } else if (this.currentPhase == BATTLE_PHASE.REWARDS) {

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
        game.font.draw(game.batch, bluePlayer.getName() + "'s Score: " + bluePlayerScore, (int) (0.1 * width), (int) (0.9 * height));
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
        battlefield.draw(player, this.currentPhase);

        game.shape.end();

        game.batch.end();
    }

}
