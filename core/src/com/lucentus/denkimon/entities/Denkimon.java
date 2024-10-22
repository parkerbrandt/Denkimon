package com.lucentus.denkimon.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lucentus.denkimon.DenkimonGame;

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
        FIRE,
        EARTH,
        NATURE,
        HYDRO,
        SHOCK,
        BLANK
    };


    /*
     * Static Properties
     */
    private static final String DENKIMON_STAT_FILE = "stats/denkimon_stats.csv";

    private static final String IDLE_SHEET = "";
    private static final int IDLE_COLS = 0;
    private static final int IDLE_ROWS = 0;

    private static final String MOVE_SHEET = "";
    private static final int MOVE_COLS = 0;
    private static final int MOVE_ROWS = 0;

    private static final String ATTACK_SHEET = "";
    private static final int ATTACK_COLS = 0;
    private static final int ATTACK_ROWS = 0;


    /*
     * Properties
     */
    private final DenkimonGame game;

    // Stat Properties
    private String name;

    private TYPE type =             TYPE.BLANK;
    private STATUS currentStatus =  STATUS.ALIVE;

    private double maxHealthPoints;
    private double currentHealthPoints;
    private double atkDamage;
    private double resist;
    private double range;

    // Animation properties
    private Animation<TextureRegion> currentAnimation;
    private String currentAnimationName;

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> moveAnimation;
    private Animation<TextureRegion> attackAnimation;


    /*
     * Constructors
     */

    /**
     * Initialize all information about the Denkimon including stats and animations
     * @param name the name of the Denkimon
     */
    public Denkimon(final DenkimonGame game, String name) {

        // Initialize variables
        this.game = game;
        this.name = name;

        // Load all stats from the CSV file
        loadDenkimonInfo(name);

        // Load all animations
        this.idleAnimation = loadAnimation(IDLE_SHEET, IDLE_COLS, IDLE_ROWS);
        this.moveAnimation = loadAnimation(MOVE_SHEET, MOVE_COLS, MOVE_ROWS);
        this.attackAnimation = loadAnimation(ATTACK_SHEET, ATTACK_COLS, ATTACK_ROWS);

        this.currentAnimation = idleAnimation;
        this.currentAnimationName = "idle";
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
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(Gdx.files.internal(DENKIMON_STAT_FILE))));
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
