package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class PauseScene extends Scene {
    private TextButton resumeButton, menuButton;

    public PauseScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
    	// Using universal attribute across all scenes
    	super(sceneManager, inputManager, outputManager);
    	
        // Load the skin
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        
        stage = new Stage(new ScreenViewport());
        backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Set the size to fill the screen
        stage.addActor(backgroundImage); // Add the background image to the stage

        // Make sure the background is drawn first (before the buttons)
        backgroundImage.setZIndex(0);

        // Create the buttons
        resumeButton = new TextButton("Resume Game", skin);
        menuButton = new TextButton("Return to Main Menu", skin);
        float xOffset = 50; // Adjust this value to move the buttons further to the left

        resumeButton = new TextButton("Resume Game", skin);
        resumeButton.setSize(300, 80); // Use the size that fits your needs
        resumeButton.setPosition((Gdx.graphics.getWidth() - resumeButton.getWidth()) / 2 - xOffset, Gdx.graphics.getHeight() / 2 + resumeButton.getHeight() / 2 + 10);
        resumeButton.getLabel().setFontScale(0.41f); // Adjust the scale value to your preference
        stage.addActor(resumeButton); // Add the resume button to the stage

        menuButton = new TextButton("Return to Main Menu", skin);
        menuButton.setSize(300, 80); // Use the size that fits your needs
        menuButton.setPosition((Gdx.graphics.getWidth() - menuButton.getWidth()) / 2 - xOffset, Gdx.graphics.getHeight() / 2 - menuButton.getHeight() / 2 - 60);
        menuButton.getLabel().setFontScale(0.41f); // Adjust the scale value to your preference
        
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
        });
        
        stage.addActor(menuButton); // Add the menu button to the stage
        
        // Create an array of buttons for navigation
        menuButtons = new TextButton[]{resumeButton, menuButton};
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

    @Override
    protected void handleInput() {
        if (inputManager.isUpPressed()) {
            currentButtonIndex = (currentButtonIndex - 1 + menuButtons.length) % menuButtons.length;
            updateButtonStyles();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        } else if (inputManager.isDownPressed()) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        } else if (inputManager.isEnterPressed()) {
            if (currentButtonIndex == 0) {
                sceneManager.pop();
            } else if (currentButtonIndex == 1) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        } 
        
    }
}
