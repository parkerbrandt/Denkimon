package com.lucentus.denkimon.entities;


import com.badlogic.gdx.Gdx;
import com.lucentus.denkimon.DenkimonGame;
import jdk.net.SocketFlow;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Class to represent each Denkimon
 */
public class Denkimon extends Entity {

    /**
     * Represent the current status of the denkimon
     */
    public enum STATUS {
        ALIVE,
        DEAD
    };

    public enum TYPE {
        BLANK,
        SHOCK
    };


    /*
     * Static Properties
     */


    /*
     * Properties
     */
    private final DenkimonGame game;

    private String name;

    private TYPE type;
    private int maxHealthPoints;
    private int currentHealthPoints;
    private int atkDamage;
    private float resist;
    private int range;

    private STATUS currentStatus = STATUS.ALIVE;


    /*
     * Constructors
     */

    /**
     * Initialize all information about the Denkimon including stats and animations
     * @param name the name of the Denkimon
     */
    public Denkimon(final DenkimonGame game, String name) {
        this.game = game;
        this.name = name;
        loadDenkimonInfo(name);
    }


    /*
     * Methods
     */

    /**
     * Load Denkimon information from the stat CSV file
     * On failure, load default stats
     * @param name the name of the Denkimon to find
     */
    private void loadDenkimonInfo(String name) {

        try {
            String filename = "";

            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(Gdx.files.internal(filename))));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(name)) {

                }
            }

        } catch (Exception e) {
            System.out.println("Error reading the file...\n" + e.getMessage());

            // Set default stat values
            this.maxHealthPoints = 200;
            this.currentHealthPoints = this.maxHealthPoints;
            this.atkDamage = 15;
            this.range = 1;
        }
    }


    /*
     * Override Methods
     */

    @Override
    public void render() {

    }

    @Override
    public void onAttack() {

    }

    @Override
    public void onHit() {

    }

    @Override
    public void onMove() {

    }


    /*
     * Getters & Setters
     */
    public Denkimon.STATUS getStatus() {
        return this.currentStatus;
    }
}
