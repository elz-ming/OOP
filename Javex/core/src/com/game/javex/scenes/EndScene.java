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
    private TextButton retryButton;
    private float inputDelayTimer = 0f;
    private final float INPUT_DELAY = 0.2f; // 0.2 seconds


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
        retryButton = new TextButton("Retry", skin);
        homeButton = new TextButton("Home", skin);

        // Set the font scale for each button's label
        homeButton.getLabel().setFontScale(0.5f); // Adjust the scale value to your preference
        retryButton.getLabel().setFontScale(0.5f); // Adjust the scale value to your preference

        // Increase the button size
        float buttonWidth = width * 0.4f; // Increase to 40% of the screen width
        float buttonHeight = height * 0.15f; // Increase to 15% of the screen height
        homeButton.setSize(buttonWidth, buttonHeight);
        retryButton.setSize(buttonWidth, buttonHeight);

        // Position the buttons starting from the bottom of the screen
        homeButton.setPosition((width / 2 - homeButton.getWidth() / 2), 2 * (homeButton.getHeight()));
        retryButton.setPosition((width / 2 - retryButton.getWidth() / 2), homeButton.getY() + homeButton.getHeight() + 10);

        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        stage.addActor(retryButton);
        stage.addActor(homeButton);

        // Create an array for navigation
        menuButtons = new TextButton[]{retryButton, homeButton};

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

        retryButton.setSize(buttonWidth, buttonHeight);
        homeButton.setSize(buttonWidth, buttonHeight);

        // Adjust the font scale based on the button size
        float fontScale = buttonHeight / 120f; // Adjust if needed
        retryButton.getLabel().setFontScale(fontScale);
        homeButton.getLabel().setFontScale(fontScale);

        // Center the buttons
        retryButton.setPosition((width - retryButton.getWidth()) / 2, (height - retryButton.getHeight()) / 2);
        homeButton.setPosition((width - homeButton.getWidth()) / 2, retryButton.getY() - homeButton.getHeight() - 10);
    }

    
    @Override
    protected void handleInput() {
        // Update the input delay timer
        inputDelayTimer += Gdx.graphics.getDeltaTime();

        if (inputDelayTimer < INPUT_DELAY) {
            return; // Input delay not reached yet
        }

        if (inputManager.isUpPressed()) {
            currentButtonIndex = (currentButtonIndex - 1 + menuButtons.length) % menuButtons.length;
            updateButtonStyles();
            inputDelayTimer = 0f; // Reset the input delay timer
        } else if (inputManager.isDownPressed()) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
            inputDelayTimer = 0f; // Reset the input delay timer
        } else if (inputManager.isEnterPressed()) {
            if (currentButtonIndex == 0) {
                sceneManager.set(new WorldSelectionScene(sceneManager, inputManager, outputManager));
            } else if (currentButtonIndex == 1) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
            inputDelayTimer = 0f; // Reset the input delay timer
        }
    }

}
