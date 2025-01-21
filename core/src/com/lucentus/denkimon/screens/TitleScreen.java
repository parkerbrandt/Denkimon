package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lucentus.denkimon.DenkimonGame;
import com.lucentus.denkimon.entities.Denkimon;
import com.lucentus.denkimon.users.Player;
import java.net.Socket;


/**
 * The Main Title Screen for the Denkimon game
 * Allows users to login once interacted with
 */
public class TitleScreen implements Screen {

    // Properties
    private final DenkimonGame game;
    private OrthographicCamera camera;

    private Player player;

    // Socket Properties
    private Socket loginSocket;

    // UI Properties
    private final Skin skin;
    private final Stage stage;
    private final Table table;
    private final Table buttonTable;

    private final Label usernameLabel;
    private final TextField usernameField;
    private final Label passwordLabel;
    private final TextField passwordField;

    private final TextButton loginButton;
    private final TextButton registerButton;


    /*
     * Constructors
     */

    /**
     * Main constructor to initialize camera for this screen
     * @param game the main game class
     */
    public TitleScreen(final DenkimonGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DenkimonGame.VIEWPORT_WIDTH, DenkimonGame.VIEWPORT_HEIGHT);

        // Initialize the Player
        player = new Player();

        // TODO: Early Testing - Give player a FireAnt Denkimon
        Denkimon fireant = new Denkimon(game, "FireAnt");

        // Initialize Scene2d UI components
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        stage.addActor(table);
        table.setSize(DenkimonGame.VIEWPORT_WIDTH, DenkimonGame.VIEWPORT_HEIGHT);
        table.align(Align.center);
        table.setDebug(true);

        // Initialize skin components
        skin = new Skin(Gdx.files.internal("assets/skins/beta/uiskin.json"));

        // Add text boxes for username and password input
        usernameLabel = new Label("Username: ", skin);
        usernameField = new TextField("", skin);

        passwordLabel = new Label("Password: ", skin);
        passwordField = new TextField("", skin);

        TextureRegion upRegion = skin.getRegion("default-slider-knob");
        TextureRegion downRegion = skin.getRegion("default-slider-knob");
        BitmapFont buttonFont = skin.getFont("default-font");

        table.add(usernameLabel);
        table.add(usernameField);
        table.add(passwordLabel);
        table.add(passwordField);

        // Add buttons
        buttonTable = new Table();
        stage.addActor(buttonTable);
        buttonTable.setFillParent(true);
        buttonTable.bottom();

        // Log-In Button
        loginButton = new TextButton("Log In", skin);
        buttonTable.add(loginButton);
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: Currently just sets player's username
                player.setName(usernameField.getText());
                game.setScreen(new HomeScreen(game, player));
            }
        });

        // Register Button
        registerButton = new TextButton("Register", skin);
        buttonTable.add(registerButton);
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: Currently just sets player's username
                player.setName(usernameField.getText());
                game.setScreen(new HomeScreen(game, player));
            }
        });
    }


    /*
     * Override Methods
     */

    @Override
    public void show() { }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.begin();
        game.font.draw(game.batch, "DENKIMON", 100, DenkimonGame.VIEWPORT_HEIGHT - 200);
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        skin.dispose();
    }
}
