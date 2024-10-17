package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.lucentus.denkimon.Denkimon;
import com.lucentus.denkimon.servers.LoginServer;

import java.net.InetAddress;
import java.net.Socket;


/**
 * The Log-In Screen
 * Allows a user to either Login or Register with a username and password
 */
public class LoginScreen implements Screen {

    // Properties
    private final Denkimon game;
    private OrthographicCamera camera;

    // Socket Properties
    private Socket socket;

    // UI Properties
    private Skin skin;
    private Stage stage;
    private Table table;

    private Label usernameLabel;
    private TextField usernameField;
    private Label passwordLabel;
    private TextField passwordField;

    private TextButton loginButton;
    private TextButton registerButton;


    /*
     * Constructors
     */
    public LoginScreen(final Denkimon game) {
        this.game = game;

        // Initialize Scene2d UI components
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true);

        // Initialize skin components
        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());


        // TODO: Add text boxes for username and password input


        // Add buttons
        // Create the style that will be used for both buttons
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.newDrawable("white", Color.DARK_GRAY);
        style.down = skin.newDrawable("white", Color.DARK_GRAY);
        style.checked = skin.newDrawable("white", Color.BLUE);
        style.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        style.font = skin.getFont("default");
        skin.add("default", style);

        // Log In Button
        // Will attempt to log in to the server and check that the user's information is valid
        loginButton = new TextButton("Log In", skin);
        table.add(loginButton);
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: Connect to server
                try {
                    InetAddress host = InetAddress.getLocalHost();
                    game.loginSocket = new Socket(host.getHostName(), LoginServer.PORT);

                    // If the information matches up, take the user to the main home screen
                    game.setScreen(new HomeScreen(game));

                } catch(Exception e) {
                    System.out.println("Error connecting to login server occurred:\n" + e.getMessage());
                }
            }
        });

        // Register Button
        // Will register user's information in the server if it does not already exist
        registerButton = new TextButton("Register", skin);
        table.add(registerButton);
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: Connect to server
                try {
                    // Temporarily will just take us to the home screen
                    game.setScreen(new HomeScreen(game));
                } catch (Exception e) {
                    System.out.println("Error connecting to login server occurred:\n" + e.getMessage());
                }
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
