package com.lucentus.denkimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.lucentus.denkimon.DenkimonGame;
import com.lucentus.denkimon.users.Player;

import java.io.IOException;
import java.net.Socket;


/**
 * The Main Screen of the Game
 * Displayed once logged in
 * Allows a user to adjust their Denkimon in their party or queue for a game
 */
public class HomeScreen implements Screen {

    // Properties
    private final DenkimonGame game;
    private OrthographicCamera camera;

    private Player player;

    // Socket Properties
    private Socket socket;

    // UI Properties
    private Skin skin;
    private Stage stage;
    private Table displayTable;
    private Table partyTable;
    private Table startTable;
    private Table table;

    private TextButton playButton;
    private TextButton editPartyButton;


    /*
     * Constructors
     */

    public HomeScreen(final DenkimonGame game, Player p) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, DenkimonGame.VIEWPORT_WIDTH, DenkimonGame.VIEWPORT_HEIGHT);

        // Initialize the Player
        player = p;

        // Initialize the main game server connection
        game.gameSocket = new Socket();

        // Initialize Scene2d UI components
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true);

        // Initialize skin components
        skin = new Skin(Gdx.files.internal("assets/skins/beta/uiskin.json"));

        editPartyButton = new TextButton("Edit Party", skin);
        table.add(editPartyButton);
        editPartyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: Bring up a pop-up allowing the user to change their party
            }
        });

        playButton = new TextButton("Play", skin);
        table.add(playButton);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new BattleScreen(game, player));
            }
        });
    }


    /*
     * GDX Override Methods
     */

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.begin();
        game.font.draw(game.batch, "Hello, " + player.getName(), 100, DenkimonGame.VIEWPORT_HEIGHT - 200);
        game.batch.end();

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
