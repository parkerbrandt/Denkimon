package com.lucentus.denkimon.battle.field;

import com.badlogic.gdx.math.Rectangle;
import com.lucentus.denkimon.DenkimonGame;
import com.lucentus.denkimon.entities.Denkimon;


/**
 * Class to represent each square in the Battlefield Grids
 */
public class BattlefieldSquare {

    /*
     * Static Properties
     */
    public static final float WIDTH = (float) (0.1 * DenkimonGame.VIEWPORT_WIDTH);

    /*
     * Properties
     */
    private int gridX = -1;
    private int gridY = -1;

    private float xPx = 0;        // NOTE: (x, y) location determines lower-left point
    private float yPx = 0;

    private boolean blueSide;
    private Denkimon denkimon = null;

    private Rectangle hitbox;


    /*
     * Constructor
     */

    /**
     *
     * @param gridX the x location in the "grid" of squares
     * @param gridY the y location in the "grid" of squares
     * @param x the lower-left corner's x position (in pixels)
     * @param y the lower-left corner's y position (in pixels)
     * @param blueSide true if this is the blue player's side of the arena, false for red
     */
    public BattlefieldSquare(int gridX, int gridY, float x, float y, boolean blueSide) {

        // Initialize variables
        this.gridX = gridX;
        this.gridY = gridY;
        this.xPx = x;
        this.yPx = y;
        this.blueSide = blueSide;

        this.hitbox = new Rectangle(x, y, WIDTH, WIDTH);
    }


    /*
     * Methods
     */

    /**
     * Draws the current square at its specified (x, y) coords in pixels
     * @param game the current game being played
     */
    public void draw(final DenkimonGame game) {
        game.shape.rect(xPx, yPx, WIDTH, WIDTH);
    }


    /*
     * Getters & Setters
     */

    public boolean isBlueSide() {
        return blueSide;
    }

    public void setDenkimon(Denkimon denkimon) {
        this.denkimon = denkimon;
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }
}
