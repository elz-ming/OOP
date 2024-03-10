package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class EndScene extends Scene {
    private TextButton homeButton;

    public EndScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        outputManager.play("audio/end.mp3", true);
        width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	
    	// Set background
    	backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.END_IMG_PATH)));
    	backgroundImage.setSize(width, height); // Set the size to fill the screen
    	backgroundImage.setZIndex(0); // Make sure the background is drawn first (before the buttons)
    	
    	// Set skin
    	skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
    	
    	// Create buttons
        homeButton = new TextButton("Home", skin);
        
        // Set the font scale for each button's label
        homeButton.getLabel().setFontScale(0.5f); // Adjust the scale value to your preference
        
        // Increase the button size
        homeButton.setSize(200, 80);
        
        // Position the buttons starting from the bottom of the screen
        homeButton.setPosition((width / 2 - homeButton.getWidth() / 2),  2 * (homeButton.getHeight()));
    	
    	stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        stage.addActor(homeButton);
  
        // Create an array for navigation
        menuButtons = new TextButton[]{homeButton};

        updateButtonStyles();
    }
    
    
    
    
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        this.width = width;
        this.height = height;

        backgroundImage.setSize(width, height);

        // Recalculate button size and position based on the new resolution
        float buttonWidth = width * 0.4f; // Increase to 40% of the screen width
        float buttonHeight = height * 0.15f; // Increase to 15% of the screen height

        homeButton.setSize(buttonWidth, buttonHeight);

        // Adjust the font scale based on the button size
        float fontScale = buttonHeight / 120f; // Adjust if needed
        homeButton.getLabel().setFontScale(fontScale);

        // Center the button
        homeButton.setPosition((width - homeButton.getWidth()) / 2, (height - homeButton.getHeight()) / 2);
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
}
