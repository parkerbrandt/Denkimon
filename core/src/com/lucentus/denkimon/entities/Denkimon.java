package com.lucentus.denkimon.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.lucentus.denkimon.DenkimonGame;
import com.lucentus.denkimon.entities.abilities.Skill;
import com.lucentus.denkimon.users.Player;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * Class to represent each Denkimon and their respective stats
 */
public class Denkimon extends Entity {

    /*
     * Class Properties
     */
    public static final double  MAX_DENKIMON_SIZE = 0.09;       // The max Denkimon sprite size as a percentage of screen width
    public static final int     DENKIMON_SPRITE_SIZE = 64;      // The height and width of the sprite of each denkimon
    public static final float   SPRITE_SCALE = DenkimonGame.VIEWPORT_WIDTH * 0.1f;

    private static final String DENKIMON_STAT_FILE = "assets/game_assets/stats/denkimon_stats.csv";


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
     * Properties
     */
    private final DenkimonGame game;

    // Stat Properties
    private String name;

    private Rectangle hitbox;

    private Player owner;

    private TYPE    type =           TYPE.BLANK;
    private CLASS   denkiClass =     CLASS.OFFENSE;
    private STATUS  currentStatus =  STATUS.ALIVE;

    private ArrayList<Skill> skills = new ArrayList<>();

    private double attackTimer = 0;

    private int level = 1;
    private int expToNextLevel;
    private int currentExp;

    private double maxHealthPoints;
    private double currentHealthPoints;
    private double hpPerLevel;

    private double maxEnergy;
    private double currentEnergy;
    private double energyPerHit;

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
    private String moveSheet = "";
    private int moveCols = 0;
    private int moveRows = 0;

    private Animation<TextureRegion> attackAnimation;
    private String attackSheet = "";
    private int atkCols = 0;
    private int atkRows = 0;

    private Animation<TextureRegion> skill1Animation;
    private String skill1Sheet = "";
    private int skill1Cols = 0;
    private int skill1Rows = 0;

    private Animation<TextureRegion> skill2Animation;
    private String skill2Sheet = "";
    private int skill2Cols = 0;
    private int skill2Rows = 0;

    private Animation<TextureRegion> hurtAnimation;
    private String hurtSheet = "";
    private int hurtCols = 0;
    private int hurtRows = 0;


    /*
     * Constructors
     */

    /**
     * Initialize all information about the Denkimon including stats and animations
     * @param player the Player owner of this Denkimon
     * @param name the name of the Denkimon
     */
    public Denkimon(final DenkimonGame game, Player player, String name) {

        // Call super constructor
        super();

        // Initialize variables
        this.game = game;
        this.name = name;
        this.owner = player;

        this.hitbox = new Rectangle(0, 0, DENKIMON_SPRITE_SIZE, DENKIMON_SPRITE_SIZE);

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

    /**
     * Render the Denkimon at its position on the battlefield
     * NOTE: The x and y positions will be determined automatically
     */
    @Override
    public void render(OrthographicCamera camera, float time) {
        game.batch.begin();

        boolean flip = !owner.isBlueside();
        // game.batch.draw(getCurrentFrame(time));

        game.batch.end();
    }

    /**
     * Alternate method for rendering if the Denkimon needs to be drawn at a specific position
     * Used for display not on battlefield (i.e. on party selection screen, home screen, etc.)
     */
    public void render(OrthographicCamera camera, float time, float x, float y) {
        game.batch.begin();

        boolean flip = !owner.isBlueside();
        game.batch.draw(this.getCurrentFrame(time), x, y);

        game.batch.end();

        // Update hitbox with new x and y positions
        this.hitbox.x = x;
        this.hitbox.y = y;
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
    public void onIdle() {
        this.currentAnimation = idleAnimation;
        this.currentAnimationName = "idle";
    }

    @Override
    public void dispose() {

    }


    /*
     * Getters & Setters
     */

    public String getName() {
        return this.name;
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }

    public Player getOwner() {
        return this.owner;
    }

    public Denkimon.TYPE getType() {
        return this.type;
    }

    public Denkimon.CLASS getDenkiClass() {
        return this.denkiClass;
    }

    public Denkimon.STATUS getStatus() {
        return this.currentStatus;
    }

    public int getLevel() {
        return this.level;
    }

    public double getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public double getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public void addToHealthPoints(int delta) {
        this.currentHealthPoints += delta;
    }

    public TextureRegion getCurrentFrame(float time) {
        return currentAnimation.getKeyFrame(time, true);
    }
}
