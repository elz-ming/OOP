package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.entities.TreasureChest;
import com.game.javex.inouts.InputManager;
import com.game.javex.inouts.OutputManager;

public class QuizScene extends Scene {
    private TreasureChest treasureChest;
    private String question;
    private String[] answers;
    private int correctAnswerIndex;
    private Label questionLabel, resultLabel;
    private boolean enterPressedAfterAnswer = false;
    private float inputDelay = 0.2f; // 200 milliseconds delay
    private float timeSinceLastInput = 0f;
    private Image questionBackground, resultBackground;
    private boolean answerSelected = false;

    public QuizScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager, TreasureChest treasureChest) {
        super(sceneManager, inputManager, outputManager);

        this.treasureChest = treasureChest;

        this.question = treasureChest.getQuestion();
        this.answers = treasureChest.getAnswers();
        this.correctAnswerIndex = treasureChest.getCorrectAnswerIndex();

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // Load the skin
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

        stage = new Stage(new ScreenViewport());
        backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
        backgroundImage.setSize(width, height);
        stage.addActor(backgroundImage);
        backgroundImage.setZIndex(0); 
      
        // Create the question label
        questionLabel = new Label(question, skin);
        questionLabel.setPosition(width / 2 - questionLabel.getWidth() / 2, height - questionLabel.getHeight() - 50);
       
        // Create the answer buttons
        menuButtons = new TextButton[answers.length];
        for (int i = 0; i < answers.length; i++) {
            final int index = i;
            menuButtons[i] = new TextButton(answers[i], skin);
            menuButtons[i].setSize(500, 80);
            menuButtons[i].setPosition((width - menuButtons[i].getWidth()) / 2, height / 2 - (i * (menuButtons[i].getHeight() + 10)));
            menuButtons[i].addListener(event -> {
                if (!answerSelected) {
                    handleAnswerSelection(index);
                }
                return true;
            });
            stage.addActor(menuButtons[i]);
        }
        resultLabel = new Label("", skin);
     
        Texture questionBgTexture = new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH));
        Texture resultBgTexture = new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH));

        
        questionBackground = new Image(questionBgTexture);
        resultBackground = new Image(resultBgTexture);

        
        questionBackground.setSize(questionLabel.getPrefWidth() + 30, questionLabel.getPrefHeight() + 20);
        questionBackground.setPosition(width / 2 - questionBackground.getWidth() / 2, questionLabel.getY() - 10);

        
        resultBackground.setSize(resultLabel.getPrefWidth() + 20, resultLabel.getPrefHeight() + 20);
        resultBackground.setPosition(width / 2 - resultBackground.getWidth() / 2, questionBackground.getY() - resultBackground.getHeight() - 10);
        resultBackground.setVisible(false);

    
        stage.addActor(questionBackground);
        stage.addActor(resultBackground);

       
        stage.addActor(questionLabel);
        stage.addActor(resultLabel);
    
        questionLabel.setZIndex(questionBackground.getZIndex() + 1);
        resultLabel.setZIndex(resultBackground.getZIndex() + 1);

        updateButtonStyles();
    }

    @Override
    protected void handleInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        timeSinceLastInput += deltaTime;

        if (answerSelected) {
            if (enterPressedAfterAnswer && inputManager.isEnterPressed()) {
                treasureChest.setSolving(false);
                sceneManager.pop();
            } else if (!enterPressedAfterAnswer && timeSinceLastInput >= inputDelay && inputManager.isEnterPressed()) {
                enterPressedAfterAnswer = true;
                timeSinceLastInput = 0;
            }
            return;
        }

        if (timeSinceLastInput >= inputDelay) {
            if (inputManager.isUpPressed()) {
                currentButtonIndex = (currentButtonIndex + menuButtons.length - 1) % menuButtons.length;
                updateButtonStyles();
                timeSinceLastInput = 0;
            } else if (inputManager.isDownPressed()) {
                currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
                updateButtonStyles();
                timeSinceLastInput = 0;
            } else if (inputManager.isEnterPressed()) {
                handleAnswerSelection(currentButtonIndex);
                timeSinceLastInput = 0;
            }
        }
    }

    private void handleAnswerSelection(int index) {
        answerSelected = true;
        for (int i = 0; i < menuButtons.length; i++) {
            TextButton button = menuButtons[i];
            if (i == correctAnswerIndex) {
                button.getLabel().setColor(Color.GREEN);
            } else {
                button.getLabel().setColor(Color.RED);
            }
        }

        TextButton selectedButton = menuButtons[index];
        selectedButton.getLabel().setColor(index == correctAnswerIndex ? Color.GREEN : Color.FIREBRICK);

        if (index == correctAnswerIndex) {
            resultLabel.setText("Correct! Press Enter to proceed.");
            resultLabel.setColor(Color.GREEN);
            treasureChest.setSolved(true);
        } else {
            resultLabel.setText("Wrong! Press Enter to proceed.");
            resultLabel.setColor(Color.RED);
        }

        
        resultLabel.setPosition(width / 2 - resultLabel.getWidth() / 2, questionLabel.getY() - questionLabel.getHeight() - 20);

        
        float moveLeftAmount = 0; 
        float newXPosition = (width / 2 - resultLabel.getPrefWidth() / 2) - moveLeftAmount; 

        
        resultLabel.setPosition(newXPosition, questionLabel.getY() - questionLabel.getHeight() - 20);
     
        
        resultLabel.setVisible(true);
        resultBackground.setVisible(true);

        // Update the size and position of the result background to encapsulate the result label
        resultBackground.setSize(resultLabel.getPrefWidth() + 20, resultLabel.getPrefHeight() + 20);
        resultBackground.setPosition(width / 2 - resultBackground.getWidth() / 2, questionBackground.getY() - resultBackground.getHeight() - 10);
    }
    
    @Override
    protected void updateButtonStyles() {
        for (int i = 0; i < menuButtons.length; i++) {
            TextButton button = menuButtons[i];
            if (i == currentButtonIndex) {
                button.getLabel().setColor(Color.YELLOW);
            } else {
                button.getLabel().setColor(Color.WHITE);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        questionLabel.setPosition(width / 2 - questionLabel.getWidth() / 2, height - questionLabel.getHeight() - 50);
        for (int i = 0; i < menuButtons.length; i++) {
        	menuButtons[i].setPosition((width - menuButtons[i].getWidth()) / 2, height / 2 - (i * (menuButtons[i].getHeight() + 10)));
        }
    }
}
