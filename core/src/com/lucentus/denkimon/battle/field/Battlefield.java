package com.lucentus.denkimon.battle.field;

import com.lucentus.denkimon.DenkimonGame;

import java.util.ArrayList;

/**
 * Wrapper class to hold all squares in the Battlefield grid and enable access
 */
public class Battlefield {

    /*
     * Properties
     */
    private final DenkimonGame game;

    private ArrayList<BattlefieldSquare> bluePlayerField = new ArrayList<>();       // squares added row-wise
    private ArrayList<BattlefieldSquare> redPlayerField = new ArrayList<>();


    /*
     * Constructor
     */
    public Battlefield(final DenkimonGame game) {
        this.game = game;
    }


    /*
     * Methods
     */

    /**
     * Add a square to the array
     */
    public void addSquare(BattlefieldSquare square) {
        if (square.isBlueSide())
            bluePlayerField.add(square);
        else
            redPlayerField.add(square);
    }

    /**
     * Draw all the squares in the grid
     */
    public void draw() {
        // Draw the blue-side grid
        for (BattlefieldSquare square : bluePlayerField)
            square.draw(game);

        // Draw the red-side grid
        for (BattlefieldSquare square : redPlayerField)
            square.draw(game);
    }
}
