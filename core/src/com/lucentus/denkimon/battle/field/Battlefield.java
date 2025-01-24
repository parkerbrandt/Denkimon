package com.lucentus.denkimon.battle.field;

import com.lucentus.denkimon.DenkimonGame;
import com.lucentus.denkimon.screens.BattleScreen;
import com.lucentus.denkimon.users.Player;

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
     * Will always draw the player's side of the field,
     * but will only display enemy's once planning phase is over
     */
    public void draw(Player player, BattleScreen.BATTLE_PHASE phase) {
        // Draw the blue-side grid
        if (player.isBlueside() || phase != BattleScreen.BATTLE_PHASE.PLANNING) {
            for (BattlefieldSquare square : bluePlayerField)
                square.draw(game);
        }

        // Draw the red-side grid
        if (!player.isBlueside() || phase != BattleScreen.BATTLE_PHASE.PLANNING) {
            for (BattlefieldSquare square : redPlayerField)
                square.draw(game);
        }
    }


    /*
     * Getters & Setters
     */
    public ArrayList<BattlefieldSquare> getBluePlayerField() {
        return this.bluePlayerField;
    }

    public ArrayList<BattlefieldSquare> getRedPlayerField() {
        return this.redPlayerField;
    }
}
