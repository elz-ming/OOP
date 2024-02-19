package com.game.javex.scenes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.inouts.*;

public class EndScene extends Scene {
    private SpriteBatch sb;
    private Stage stage;
    private List<TextButton> buttons;
    private int currentButtonIndex = 0;
    
    private Label enemiesKilledLabel;
    private Label coinsCollectedLabel;
    private Label timeLabel;
    
    private Skin skin;
    private HUDManager hudManager;
    
    private Image backgroundImage;
    
    public EndScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager, HUDManager hudManager) {
        super(sceneManager, inputManager, outputManager);
        this.hudManager = hudManager; // Initialize the HUDManager reference

        sb = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        
        

        buttons = new ArrayList<>();

        Texture backgroundTexture = new Texture(Gdx.files.internal("endbackground.png"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        enemiesKilledLabel = new Label("Enemies Killed: " + hudManager.getEnemiesKilled(), skin);
        coinsCollectedLabel = new Label("Coins Collected: " + hudManager.getCoinsCollected(), skin);
        timeLabel = new Label("Time: " + hudManager.getElapsedTime(), skin);

        // Add the labels to the stage
        stage.addActor(enemiesKilledLabel);
        stage.addActor(coinsCollectedLabel);
        stage.addActor(timeLabel);

        // Position the labels on the screen
        enemiesKilledLabel.setPosition(50, Gdx.graphics.getHeight() - 30);
        coinsCollectedLabel.setPosition(50, Gdx.graphics.getHeight() - 60);
        timeLabel.setPosition(50, Gdx.graphics.getHeight() - 90);

        addButton("Back To Menu", 0.5f, 0.5f);
        updateButtonStyles();
    }

    @Override
    public void update(float dt) {
        stage.act(Gdx.graphics.getDeltaTime());
        handleInput();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        sb.begin();
        backgroundImage.draw(sb, 1);
        sb.end();
        
        stage.draw();
    }

    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();
    }

    private void addButton(String label, float customX, float customY) {
        Skin skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        TextButton button = new TextButton(label, skin);
        float buttonX = Gdx.graphics.getWidth() * customX - button.getWidth() * 0.5f;
        float buttonY = Gdx.graphics.getHeight() * customY - button.getHeight() * 0.5f - buttons.size() * button.getHeight() * 0.4f;
        button.setPosition(buttonX, buttonY);
        buttons.add(button);
        stage.addActor(button);
    }

    private void handleInput() {
        if (inputManager.getPrevKey() == com.badlogic.gdx.Input.Keys.UNKNOWN && inputManager.getCurrKey() == com.badlogic.gdx.Input.Keys.ENTER) {
            if (currentButtonIndex == 0) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
            inputManager.resetKeys();
        }
    }

    private void updateButtonStyles() {
        for (int i = 0; i < buttons.size(); i++) {
            if (i == currentButtonIndex) {
                buttons.get(i).getLabel().setColor(com.badlogic.gdx.graphics.Color.YELLOW);
            } else {
                buttons.get(i).getLabel().setColor(com.badlogic.gdx.graphics.Color.WHITE);
            }
        }
    }
}
