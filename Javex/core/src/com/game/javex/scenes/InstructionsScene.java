package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;



    public class InstructionsScene extends Scene {
        private TextButton backButton;

        public InstructionsScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
            super(sceneManager, inputManager, outputManager);
            width = Gdx.graphics.getWidth();
            height = Gdx.graphics.getHeight();
            backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
            backgroundImage.setSize(width, height);
            backgroundImage.setZIndex(0);
            stage = new Stage(new ScreenViewport());
            stage.addActor(backgroundImage);
            skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

            backButton = new TextButton("Back", skin);
            backButton.getLabel().setFontScale(0.5f);
            backButton.setSize(200, 80);

            float spaceBetweenButtons = 20;
            float totalButtonsHeight = backButton.getHeight() + spaceBetweenButtons;
            float startY = (Gdx.graphics.getHeight() - totalButtonsHeight) / 4; // Adjusted startY value

            backButton.setPosition((Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2), startY - backButton.getHeight() - 10); // Adjusted button position

            stage.addActor(backButton);

            menuButtons = new TextButton[]{backButton};

            // Add instructions labels encapsulated in a box
            Table instructionsTable = new Table();
            instructionsTable.center();
            instructionsTable.padTop(20); // Adjusted padding to make the box smaller

            // Set the background image for the box
            Image backgroundImg = new Image(new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH)));
            instructionsTable.setBackground(backgroundImg.getDrawable()); // Use the image as background

            Label instructionLabel1 = new Label("Use Up/Down arrow keys to move", skin);
            Label instructionLabel2 = new Label("Press Enter to proceed", skin);
            Label instructionLabel3 = new Label("Press Escape to pause", skin);

            instructionLabel1.setFontScale(1.5f); // Adjusted font scale to fit the smaller box
            instructionLabel2.setFontScale(1.5f);
            instructionLabel3.setFontScale(1.5f);

            instructionsTable.add(instructionLabel1).padBottom(5).row(); // Adjusted padding between labels
            instructionsTable.add(instructionLabel2).padBottom(5).row();
            instructionsTable.add(instructionLabel3).padBottom(5).row();

            // Set the size of the instructions table
            float tableWidth = width * 0.6f; // Adjust the width of the table as needed
            float tableHeight = height * 0.3f; // Adjust the height of the table as needed
            instructionsTable.setSize(tableWidth, tableHeight);
            instructionsTable.setPosition((width - tableWidth) / 2, (height - tableHeight) / 2); // Center the table on the screen

            stage.addActor(instructionsTable);

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
        float totalButtonsHeight = buttonHeight + spaceBetweenButtons;
        float startY = (height - totalButtonsHeight) / 4; // Adjusted startY value

        backButton.setSize(buttonWidth, buttonHeight);

        float fontScale = buttonHeight / 120f;
        backButton.getLabel().setFontScale(fontScale);

        backButton.setPosition((width - backButton.getWidth()) / 2, startY - backButton.getHeight() - 10); // Adjusted button position
    }

    @Override
    protected void handleInput() {
        if (inputManager.isUpPressed() || inputManager.isDownPressed()) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (inputManager.isEnterPressed()) {
            switch (currentButtonIndex) {
                case 0: // Back button
                    sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
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
