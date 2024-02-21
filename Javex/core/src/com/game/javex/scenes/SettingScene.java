package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class SettingScene extends Scene {
    private TextButton muteButton, backButton;

    public SettingScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
    	backgroundImage.setSize(width, height); // Set the size to fill the screen
    	backgroundImage.setZIndex(0); // Make sure the background is drawn first (before the buttons)
    	stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

        muteButton = new TextButton("Mute", skin);
        backButton = new TextButton("Back", skin);

        muteButton.getLabel().setFontScale(0.5f);
        backButton.getLabel().setFontScale(0.5f);

        muteButton.setSize(200, 80);
        backButton.setSize(200, 80);

        float spaceBetweenButtons = 20;
        float totalButtonsHeight = 2 * muteButton.getHeight() + spaceBetweenButtons;
        float startY = (Gdx.graphics.getHeight() - totalButtonsHeight) / 2;
        float xOffset = 30;

        muteButton.setPosition((Gdx.graphics.getWidth() / 2 - muteButton.getWidth() / 2) - xOffset, startY + muteButton.getHeight() + spaceBetweenButtons);
        backButton.setPosition((Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2) - xOffset, startY);

        stage.addActor(muteButton);
        stage.addActor(backButton);

        menuButtons = new TextButton[]{muteButton, backButton};

        updateButtonStyles();
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
                case 0: // Mute button
                    outputManager.setMuted(!outputManager.isMuted());
                    if (outputManager.isMuted()) {
                        muteButton.setText("Muted");
                    } else {
                        muteButton.setText("Mute");
                    }
                    break;
                case 1: // Back button
                    sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
                    break;
            }
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
