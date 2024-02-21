package com.game.javex.scenes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class EndScene extends Scene {
    private List<TextButton> buttons;
    
    private Label enemiesKilledLabel;
    private Label coinsCollectedLabel;
    private Label timeLabel;
    
    private HUDManager hudManager;
    
    
    public EndScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager, HUDManager hudManager) {
        super(sceneManager, inputManager, outputManager);
        this.hudManager = hudManager; // Initialize the HUDManager reference
        width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.END_IMG_PATH)));
    	backgroundImage.setSize(width, height); // Set the size to fill the screen
    	backgroundImage.setZIndex(0); // Make sure the background is drawn first (before the buttons)
    	stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
  
        buttons = new ArrayList<>();
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
        handleInput();
        stage.act(dt);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);       
        stage.draw();
    }

    @Override
    public void dispose() {
    	if (stage != null) {
	        stage.dispose();
	    }
	    if (skin != null) {
	        skin.dispose();
	    }
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
    
    @Override
    protected void handleInput() {
        if (inputManager.isEnterPressed()) {
            if (currentButtonIndex == 0) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
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
