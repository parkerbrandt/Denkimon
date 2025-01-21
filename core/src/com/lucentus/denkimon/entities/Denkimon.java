package com.lucentus.denkimon.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lucentus.denkimon.DenkimonGame;

import java.io.BufferedReader;
import java.io.FileReader;


/**
 * Class to represent each Denkimon and their respective stats
 */
public class Denkimon extends Entity {

    /*
     * Class Properties
     */
    public static final double MAX_DENKIMON_SIZE = 0.09;     // The max Denkimon sprite size as a percentage of screen width


    /**
     * Represent the current status of the denkimon
     */
    public enum STATUS {
        ALIVE,
        DEAD
    };

    public enum TYPE {
        BLANK,
        FIRE,
        AIR,
        WATER,
        EARTH,
        LIGHTNING,
        ARCANE,
        LIGHT,
        DARK,
        COSMIC,
        TOXIC,
        ATOMIC,
        PARANORMAL
    };

    public enum CLASS {
        OFFENSE,
        DEFENSE,
        SUPPORT,
        PARTNER
    };


    /*
     * Static Properties
     */
    private static final String DENKIMON_STAT_FILE = "assets/game_assets/stats/denkimon_stats.csv";


    /*
     * Properties
     */
    private final DenkimonGame game;

    // Stat Properties
    private String name;

    private TYPE type =             TYPE.BLANK;
    private CLASS denkiClass =      CLASS.OFFENSE;
    private STATUS currentStatus =  STATUS.ALIVE;

    boolean blueside = true;

    private int level = 1;

    private double maxHealthPoints;
    private double currentHealthPoints;
    private double hpPerLevel;

    private double atkDamage;
    private double adPerLevel;

    private double magicPower;
    private double mpPerLevel;

    private double atkResist;
    private double arPerLevel;

    private double magicResist;
    private double mrPerLevel;

    private double moveSpeed;
    private double atkRange;

    // Animation properties
    private Animation<TextureRegion> currentAnimation;
    private String currentAnimationName;

    private Animation<TextureRegion> idleAnimation;

    private String idleSheet = "";
    private int idleCols = 0;
    private int idleRows = 0;

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

        // Call super constructor
        super();

        // Initialize variables
        this.game = game;
        this.name = name;

        // Load all stats from the CSV file
        loadDenkimonInfo(name);

        // Load all animations
        this.idleSheet = String.format("assets/game_assets/denkimon/%s/sprites/Idle.png", this.name);
        this.idleAnimation = loadAnimation(idleSheet, idleCols, idleRows);

        /*
        this.moveAnimation = loadAnimation(MOVE_SHEET, MOVE_COLS, MOVE_ROWS);
        this.attackAnimation = loadAnimation(ATTACK_SHEET, ATTACK_COLS, ATTACK_ROWS);
         */

        this.currentAnimation = idleAnimation;
        this.currentAnimationName = "idle";
    }


    /*
     * Methods
     */

    /**
     * Load Denkimon information from the stat CSV file
     * On failure, load default stats
     * FORMAT:
     * Name,Type,Class,IdleSheetRows,IdleSheetCols,BaseHP,HP/Level,AttackDamage,AD/Level,
     * MagicPower,MP/Level,AttackResist,AR/Level,MagicResist,MR/Level,MoveSpeed,Range
     *
     * @param name the name of the Denkimon to find
     */
    private void loadDenkimonInfo(String name) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(Gdx.files.internal(DENKIMON_STAT_FILE))));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(name)) {

                    // Determine the type and class
                    switch(data[1]) {
                        case "Blank":
                            this.type = TYPE.BLANK;
                            break;

                        case "Fire":
                            this.type = TYPE.FIRE;
                            break;

                        case "Air":
                            this.type = TYPE.AIR;
                            break;

                        case "Water":
                            this.type = TYPE.WATER;
                            break;

                        case "Earth":
                            this.type = TYPE.EARTH;
                            break;

                        case "Lightning":
                            this.type = TYPE.LIGHTNING;
                            break;

                        case "Arcane":
                            this.type =  TYPE.ARCANE;
                            break;

                        case "Light":
                            this.type = TYPE.LIGHT;
                            break;

                        case "Dark":
                            this.type = TYPE.DARK;
                            break;

                        case "Paranormal":
                            this.type = TYPE.PARANORMAL;
                            break;

                        case "Cosmic":
                            this.type = TYPE.COSMIC;
                            break;

                        case "Atomic":
                            this.type = TYPE.ATOMIC;
                            break;

                        case "Toxic":
                            this.type = TYPE.TOXIC;
                            break;

                        default:
                            throw new Exception("Invalid type read...");
                    }

                    switch(data[2]) {
                        case "Offense":
                            this.denkiClass = CLASS.OFFENSE;
                            break;

                        case "Defense":
                            this.denkiClass = CLASS.DEFENSE;
                            break;

                        case "Support":
                            this.denkiClass = CLASS.SUPPORT;
                            break;

                        case "Partner":
                            this.denkiClass = CLASS.PARTNER;
                            break;

                        default:
                            throw new Exception("Invalid class read...");
                    }

                    // Initialize rows and columns for each sprite sheet
                    this.idleRows = Integer.parseInt(data[3]);
                    this.idleCols = Integer.parseInt(data[4]);

                    // Initialize stats for this Denkimon
                    this.maxHealthPoints = Double.parseDouble(data[5]);
                    this.currentHealthPoints = this.maxHealthPoints;
                    this.hpPerLevel = Double.parseDouble(data[6]);

                    this.atkDamage = Double.parseDouble(data[7]);
                    this.adPerLevel = Double.parseDouble(data[8]);

                    this.magicPower = Double.parseDouble(data[9]);
                    this.mpPerLevel = Double.parseDouble(data[10]);

                    this.atkResist = Double.parseDouble(data[11]);
                    this.arPerLevel = Double.parseDouble(data[12]);

                    this.magicResist = Double.parseDouble(data[13]);
                    this.mrPerLevel = Double.parseDouble(data[14]);

                    this.moveSpeed = Double.parseDouble(data[15]);
                    this.atkRange = Double.parseDouble(data[16]);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading the file...\n" + e.getMessage());
        }
    }


    /*
     * Override Methods
     */

    @Override
    public void render(OrthographicCamera camera, float time) {
        game.batch.begin();

        boolean flip = !blueside;
        // game.batch.draw(getCurrentFrame(time));

        game.batch.end();
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

    @Override
    public void dispose() {

    }


    /*
     * Getters & Setters
     */
    public TextureRegion getCurrentFrame(float time) {
        return currentAnimation.getKeyFrame(time, true);
    }

    public Denkimon.STATUS getStatus() {
        return this.currentStatus;
    }
}
