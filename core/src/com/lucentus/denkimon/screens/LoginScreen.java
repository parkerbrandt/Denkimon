package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.lucentus.denkimon.Denkimon;
import com.lucentus.denkimon.servers.LoginServer;
import com.sun.corba.se.pept.encoding.OutputObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private Table buttonTable;

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
        stage.addActor(table);
        table.setSize(Denkimon.VIEWPORT_WIDTH, Denkimon.VIEWPORT_HEIGHT / 2.0f);
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

        // Log In Button
        // Will attempt to log in to the server and check that the user's information is valid
        loginButton = new TextButton("Log In", skin);
        buttonTable.add(loginButton);
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: Connect to server
                try {
                    InetAddress host = InetAddress.getLocalHost();
                    game.loginSocket = new Socket(host.getHostName(), LoginServer.PORT);
                    ObjectInputStream inputStream = new ObjectInputStream(game.loginSocket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(game.loginSocket.getOutputStream());

                    // Send the username and password info to the server for validation
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    String[] message = {"validate", username, password};

                    System.out.println("Sending login request to server...");
                    outputStream.writeObject(message);

                    // Read server response
                    String[] response = (String[]) inputStream.readObject();

                    // Close all connection objects
                    game.loginSocket.close();
                    inputStream.close();
                    outputStream.close();

                    // If the information matches up, take the user to the main home screen
                    if (response[0].equals("valid"))
                        game.setScreen(new HomeScreen(game));
                    else {
                        throw new Exception("Invalid Login Information: " + response[1]);
                    }

                } catch(Exception e) {
                    System.out.println("Error connecting to login server occurred:\n" + e.getMessage());
                }
            }
        });

        // Register Button
        // Will register user's information in the server if it does not already exist
        registerButton = new TextButton("Register", skin);
        buttonTable.add(registerButton);
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
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
