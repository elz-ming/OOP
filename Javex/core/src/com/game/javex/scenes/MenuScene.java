package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class MenuScene extends Scene {
	private Stage stage;
    private Skin skin;
    
    private TextButton playButton, exitButton, settingsButton;
    private TextButton[] menuButtons;
    private int currentButtonIndex = 0;

    public MenuScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        // Using universal attribute across all scenes
    	super(sceneManager, inputManager, outputManager);
    	width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
    	backgroundImage.setSize(width, height); // Set the size to fill the screen
    	backgroundImage.setZIndex(0); // Make sure the background is drawn first (before the buttons)
        
        // Create buttons
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        playButton = new TextButton("Play", skin);
        settingsButton = new TextButton("Settings", skin);
        exitButton = new TextButton("Exit", skin);

        // Set the font scale for each button's label
        playButton.getLabel().setFontScale(0.5f); // Adjust the scale value to your preference
        settingsButton.getLabel().setFontScale(0.5f);
        exitButton.getLabel().setFontScale(0.5f);
        
        // Increase the button size
        playButton.setSize(200, 80);
        settingsButton.setSize(200, 80);
        exitButton.setSize(200, 80);
        
        // Position buttons
        float spaceBetweenButtons = 20; // Adjust the space to your preference

     // Calculate the total height that the buttons will occupy on the screen
        float totalButtonsHeight = 3 * playButton.getHeight() + 2 * spaceBetweenButtons;

     // Calculate the starting Y position for the playButton
     // This will start drawing buttons from the bottom of the screen upward
        float startY = (height - totalButtonsHeight) / 2;

     // Position the buttons starting from the bottom of the screen
        float xOffset = 30; // Adjust this value to move the buttons further to the left

     // Position the buttons starting from the bottom of the screen
        playButton.setPosition((width / 2 - playButton.getWidth() / 2) - xOffset, startY + 2 * (playButton.getHeight() + spaceBetweenButtons));
        settingsButton.setPosition((width / 2 - settingsButton.getWidth() / 2) - xOffset, startY + playButton.getHeight() + spaceBetweenButtons);
        exitButton.setPosition((width / 2 - exitButton.getWidth() / 2) - xOffset, startY);

        // Add buttons to stage
        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        stage.addActor(playButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);

        // Create an array for navigation
        menuButtons = new TextButton[]{playButton, settingsButton, exitButton};

        // Initialize button styles for selection
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
	
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        if (inputManager.isUpPressed() || inputManager.isDownPressed()) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        }
        
        if (inputManager.isEnterPressed()) {
            switch (currentButtonIndex) {
                case 0: // Play button
                    sceneManager.set(new PlayScene(sceneManager, inputManager, outputManager));
                    break;
                case 1: // Settings button
                    // Add your logic for the settings button here
                	sceneManager.set(new SettingsScene(sceneManager, inputManager, outputManager));
                    break;
                case 2: // Exit button
                    Gdx.app.exit();
                    break;
            }
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}