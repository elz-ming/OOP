package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.scenes.AbstractScene;
import com.game.javex.scenes.SceneManager;
import com.game.javex.scenes.SceneManager.SceneType;

public class StartMenuScene extends AbstractScene {
    private Stage stage;
    private Skin skin;
    private TextButton playButton, exitButton;
    private TextButton[] menuButtons;
    private int currentButtonIndex = 0;

    public StartMenuScene(SceneManager sceneManager) {
        super(sceneManager);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Assuming you have a skin.json file in the assets directory
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create buttons
        playButton = new TextButton("Play", skin);
        exitButton = new TextButton("Exit", skin);

     // Increase the button size
        playButton.setSize(300, 80);
        exitButton.setSize(300, 80);

 
        // Position buttons
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() - 60);

        // Add buttons to stage
        stage.addActor(playButton);
        stage.addActor(exitButton);

        // Create an array for navigation
        menuButtons = new TextButton[]{playButton, exitButton};

        // Initialize button styles for selection
        updateButtonStyles();
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
    public void show() {
        // Initialize anything else for your show here if necessary
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (currentButtonIndex == 0) {
                // Play button action
                sceneManager.changeScene(SceneManager.SceneType.PLAYING);
            } else if (currentButtonIndex == 1) {
                // Exit button action
                Gdx.app.exit();
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Implement behavior during pause if necessary
    }

    @Override
    public void resume() {
        // Implement behavior during resume if necessary
    }

    @Override
    public void hide() {
        // Implement behavior when hiding if necessary
    }

    @Override
    public void dispose() {
        // Dispose of resources (like textures, sounds) and cleanup
        stage.dispose();
        // If you have other disposables, dispose of them here
    }

}
