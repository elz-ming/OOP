package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.game.javex.inouts.*;

public class PauseScene extends Scene {
    private SpriteBatch sb;
    private Stage stage;
    private Skin skin;
    
    private TextButton resumeButton;
    private TextButton menuButton;
    
    private int currentButtonIndex = 0;
    private TextButton[] menuButtons;

    public PauseScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
    	// Using universal attribute across all scenes
    	super(sceneManager, inputManager, outputManager);
    	
    	// Creating own attributes specific to this scene
        sb = new SpriteBatch();

        // Load the skin
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

        // Create the buttons
        resumeButton = new TextButton("Resume Game", skin);
        menuButton = new TextButton("Return to Main Menu", skin);

        resumeButton.setSize(300, 80); // Use the size that fits your needs
        menuButton.setSize(300, 80); // Use the size that fits your needs

        // Position the buttons (centers horizontally and positions vertically with space in between)
        resumeButton.setPosition((Gdx.graphics.getWidth() - resumeButton.getWidth()) / 2, Gdx.graphics.getHeight() / 2 + resumeButton.getHeight() / 2 + 10);
        menuButton.setPosition((Gdx.graphics.getWidth() - menuButton.getWidth()) / 2, Gdx.graphics.getHeight() / 2 - menuButton.getHeight() / 2 - 60);

        // Create an array of buttons for navigation
        menuButtons = new TextButton[]{resumeButton, menuButton};
        updateButtonStyles();
    }
    
    @Override
    public void update(float dt) {
    	handleInput();
    }  
    
    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        // Log the positions and sizes of the buttons
        resumeButton.draw(sb, 1);
        menuButton.draw(sb, 1);
        sb.end();
    }

    @Override
    public void dispose() {
        sb.dispose();
        skin.dispose();
    }

    private void updateButtonStyles() {
        for (int i = 0; i < menuButtons.length; i++) {
            if (i == currentButtonIndex) {
                // Highlight the selected button
                menuButtons[i].setColor(Color.YELLOW);
            } else {
                // Other buttons are white
                menuButtons[i].setColor(Color.WHITE);
            }
        }
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            currentButtonIndex = (currentButtonIndex - 1 + menuButtons.length) % menuButtons.length;
            updateButtonStyles();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (currentButtonIndex == 0) {
                // Resume game
                sceneManager.pop();
            } else if (currentButtonIndex == 1) {
                // Return to main menu
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Pop the PauseScene and return to the PlayScene
            sceneManager.pop();
        }
    }
}
