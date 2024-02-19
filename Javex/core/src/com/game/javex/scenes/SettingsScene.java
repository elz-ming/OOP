package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.game.javex.inouts.*;

public class SettingsScene extends Scene {
    private Stage stage;
    private Skin skin;
    private Image backgroundImage;

    private TextButton muteButton, backButton;
    private TextButton[] menuButtons;
    private int currentButtonIndex = 0;

    public SettingsScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);

        stage = new Stage(new ScreenViewport());

        backgroundImage = new Image(new Texture(Gdx.files.internal("background.png")));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
        backgroundImage.setZIndex(0);

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
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private void updateButtonStyles() {
        for (int i = 0; i < menuButtons.length; i++) {
            if (i == currentButtonIndex) {
                menuButtons[i].setColor(Color.YELLOW);
            } else {
                menuButtons[i].setColor(Color.WHITE);
            }
        }
    }

    public void handleInput() {
        if (inputManager.getCurrKey() == Keys.UP || inputManager.getCurrKey() == Keys.DOWN) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
            inputManager.resetKeys();
        }

        if (inputManager.getCurrKey() == Keys.ENTER) {
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
            inputManager.resetKeys();
        }
    }
}
