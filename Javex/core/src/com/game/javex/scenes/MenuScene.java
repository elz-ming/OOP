package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class MenuScene extends Scene {
    private TextButton playButton, settingButton, instructionsButton, exitButton;

    public MenuScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        audioPath = Constants.MENU_AUDIO_PATH;
        outputManager.play(audioPath, true);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        
        // Set background
        backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
        backgroundImage.setSize(width, height); 
        backgroundImage.setZIndex(0); 

        // Set skin
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

        // Create buttons
        playButton = new TextButton("Play", skin);
        settingButton = new TextButton("Settings", skin);
        instructionsButton = new TextButton("Instructions", skin);
        exitButton = new TextButton("Exit", skin);

        // Set the font scale for each button's label
        float fontScale = 0.5f; 
        playButton.getLabel().setFontScale(fontScale);
        settingButton.getLabel().setFontScale(fontScale);
        instructionsButton.getLabel().setFontScale(fontScale);
        exitButton.getLabel().setFontScale(fontScale);

        // Increase the button size
        float buttonWidth = 200;
        float buttonHeight = 80;
        playButton.setSize(buttonWidth, buttonHeight);
        settingButton.setSize(buttonWidth, buttonHeight);
        instructionsButton.setSize(buttonWidth, buttonHeight);
        exitButton.setSize(buttonWidth, buttonHeight);

        // Position buttons
        float spaceBetweenButtons = 20; 

       
        float totalButtonsHeight = 4 * buttonHeight + 3 * spaceBetweenButtons;

     
        float startY = (height - totalButtonsHeight) / 2;

        
        float xOffset = 30; 

        playButton.setPosition((width / 2 - playButton.getWidth() / 2) - xOffset, startY + 3 * (buttonHeight + spaceBetweenButtons));
        settingButton.setPosition((width / 2 - settingButton.getWidth() / 2) - xOffset, startY + 2 * (buttonHeight + spaceBetweenButtons));
        instructionsButton.setPosition((width / 2 - instructionsButton.getWidth() / 2) - xOffset, startY + buttonHeight + spaceBetweenButtons);
        exitButton.setPosition((width / 2 - exitButton.getWidth() / 2) - xOffset, startY);

        // Add buttons to stage
        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        stage.addActor(playButton);
        stage.addActor(settingButton);
        stage.addActor(instructionsButton);
        stage.addActor(exitButton);

        // Create an array for navigation
        menuButtons = new TextButton[]{playButton, settingButton, instructionsButton, exitButton};

        // Initialize button styles for selection
        updateButtonStyles();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        this.width = width;
        this.height = height;

        backgroundImage.setSize(width, height);

        
        float buttonWidth = width * 0.4f; 
        float buttonHeight = height * 0.15f; 
        float spaceBetweenButtons = height * 0.06f; 
        float totalButtonsHeight = 4 * buttonHeight + 3 * spaceBetweenButtons;
        float startY = (height - totalButtonsHeight) / 2;

        playButton.setSize(buttonWidth, buttonHeight);
        settingButton.setSize(buttonWidth, buttonHeight);
        instructionsButton.setSize(buttonWidth, buttonHeight);
        exitButton.setSize(buttonWidth, buttonHeight);

        
        float fontScale = buttonHeight / 120f; 
        playButton.getLabel().setFontScale(fontScale);
        settingButton.getLabel().setFontScale(fontScale);
        instructionsButton.getLabel().setFontScale(fontScale);
        exitButton.getLabel().setFontScale(fontScale);

        // Center the buttons
        playButton.setPosition((width - playButton.getWidth()) / 2, startY + 3 * (buttonHeight + spaceBetweenButtons));
        settingButton.setPosition((width - settingButton.getWidth()) / 2, startY + 2 * (buttonHeight + spaceBetweenButtons));
        instructionsButton.setPosition((width - instructionsButton.getWidth()) / 2, startY + buttonHeight + spaceBetweenButtons);
        exitButton.setPosition((width - exitButton.getWidth()) / 2, startY);
    }
    
    @Override
    protected void handleInput() {
        if (inputManager.isUpPressed()) {
            currentButtonIndex = (currentButtonIndex + menuButtons.length - 1) % menuButtons.length; // Move up one button
            updateButtonStyles();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (inputManager.isDownPressed()) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length; // Move down one button
            updateButtonStyles();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        if (inputManager.isReturnPressed()) {
        	Gdx.app.exit();
        }

        if (inputManager.isEnterPressed()) {
            switch (currentButtonIndex) {
                case 0: // Play button
                    sceneManager.set(new WorldSelectionScene(sceneManager, inputManager, outputManager));
                    break;
                case 1: // Settings button
                  
                    sceneManager.set(new SettingScene(sceneManager, inputManager, outputManager));
                    break;
                case 2: // Instructions button
                    sceneManager.set(new InstructionsScene(sceneManager, inputManager, outputManager));
                    break;
                case 3: // Exit button
                    Gdx.app.exit();
                    break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}