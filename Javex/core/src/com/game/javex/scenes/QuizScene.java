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

    private Label questionLabel;
    private TextButton[] answerButtons;
    private Label resultLabel;
    private int currentButtonIndex = 0;
    private boolean enterPressedAfterAnswer = false;
    
    private float inputDelay = 0.2f; // 200 milliseconds delay
    private float timeSinceLastInput = 0f;

    private Image questionBackground; // Background for the question
    private Image resultBackground; // Background for the result

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
        answerButtons = new TextButton[answers.length];
        for (int i = 0; i < answers.length; i++) {
            final int index = i;
            answerButtons[i] = new TextButton(answers[i], skin);
            answerButtons[i].setSize(500, 80);
            answerButtons[i].setPosition((width - answerButtons[i].getWidth()) / 2, height / 2 - (i * (answerButtons[i].getHeight() + 10)));
            answerButtons[i].addListener(event -> {
                if (!answerSelected) {
                    handleAnswerSelection(index);
                }
                return true;
            });
            stage.addActor(answerButtons[i]);
        }

     // Create the result label
        resultLabel = new Label("", skin);
     
        
     // Load the texture for the backgrounds
        Texture questionBgTexture = new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH));
        Texture resultBgTexture = new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH));

        // Create the background images
        questionBackground = new Image(questionBgTexture);
        resultBackground = new Image(resultBgTexture);

        // Set the size and position of the question background
        questionBackground.setSize(questionLabel.getPrefWidth() + 30, questionLabel.getPrefHeight() + 20);
        questionBackground.setPosition(width / 2 - questionBackground.getWidth() / 2, questionLabel.getY() - 10);

        // Set the size and position of the result background (initially not visible)
        resultBackground.setSize(resultLabel.getPrefWidth() + 20, resultLabel.getPrefHeight() + 20);
        resultBackground.setPosition(width / 2 - resultBackground.getWidth() / 2, questionBackground.getY() - resultBackground.getHeight() - 10);
        resultBackground.setVisible(false);

     // Add the backgrounds to the stage
        stage.addActor(questionBackground);
        stage.addActor(resultBackground);

        // Add the question and result labels to the stage (on top of the backgrounds)
        stage.addActor(questionLabel);
        stage.addActor(resultLabel);
     // Set the z-index of the text labels to be higher than the backgrounds
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
                currentButtonIndex = (currentButtonIndex + answerButtons.length - 1) % answerButtons.length;
                updateButtonStyles();
                timeSinceLastInput = 0;
            } else if (inputManager.isDownPressed()) {
                currentButtonIndex = (currentButtonIndex + 1) % answerButtons.length;
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
        for (int i = 0; i < answerButtons.length; i++) {
            TextButton button = answerButtons[i];
            if (i == correctAnswerIndex) {
                button.getLabel().setColor(Color.GREEN);
            } else {
                button.getLabel().setColor(Color.RED);
            }
        }

        TextButton selectedButton = answerButtons[index];
        selectedButton.getLabel().setColor(index == correctAnswerIndex ? Color.GREEN : Color.FIREBRICK);

        if (index == correctAnswerIndex) {
            resultLabel.setText("Correct! Press Enter to proceed.");
            resultLabel.setColor(Color.GREEN);
            treasureChest.setSolved(true);
        } else {
            resultLabel.setText("Wrong! Press Enter to proceed.");
            resultLabel.setColor(Color.RED);
        }

        // Update the position of the result label to be below the question label
        resultLabel.setPosition(width / 2 - resultLabel.getWidth() / 2, questionLabel.getY() - questionLabel.getHeight() - 20);

        // Calculate the new x-position to move the label to the left
        float moveLeftAmount = 0; // Adjust this value to move the label further to the left
        float newXPosition = (width / 2 - resultLabel.getPrefWidth() / 2) - moveLeftAmount; // Use getPrefWidth() to get the preferred width of the label

        // Update the position of the result label to be below the question label and moved to the left
        resultLabel.setPosition(newXPosition, questionLabel.getY() - questionLabel.getHeight() - 20);

       
     
        // Make the result label and background visible
        resultLabel.setVisible(true);
        resultBackground.setVisible(true);

     // Update the size and position of the result background to encapsulate the result label
        resultBackground.setSize(resultLabel.getPrefWidth() + 20, resultLabel.getPrefHeight() + 20);
        resultBackground.setPosition(width / 2 - resultBackground.getWidth() / 2, questionBackground.getY() - resultBackground.getHeight() - 10);
    
    }

    
    

    protected void updateButtonStyles() {
        for (int i = 0; i < answerButtons.length; i++) {
            TextButton button = answerButtons[i];
            if (i == currentButtonIndex) {
                button.getLabel().setColor(Color.YELLOW);
            } else {
                button.getLabel().setColor(Color.WHITE);
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        questionLabel.setPosition(width / 2 - questionLabel.getWidth() / 2, height - questionLabel.getHeight() - 50);
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setPosition((width - answerButtons[i].getWidth()) / 2, height / 2 - (i * (answerButtons[i].getHeight() + 10)));
        }
    }
}
