package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private int currentButtonIndex = 0;
    
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
        stage.addActor(questionLabel);

        // Create the answer buttons
        answerButtons = new TextButton[answers.length];
        for (int i = 0; i < answers.length; i++) {
            final int index = i;
            answerButtons[i] = new TextButton(answers[i], skin);
            answerButtons[i].setSize(300, 60);
            answerButtons[i].setPosition((width - answerButtons[i].getWidth()) / 2, height / 2 - (i * (answerButtons[i].getHeight() + 10)));
            answerButtons[i].addListener(event -> {
                if (!answerSelected) {
                    handleAnswerSelection(index);
                }
                return true;
            });
            stage.addActor(answerButtons[i]);
        }

        updateButtonStyles();
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
    }

    @Override
    protected void handleInput() {
        if (answerSelected) {
            if (inputManager.isEnterPressed()) {
            	treasureChest.setSolving(false);
                sceneManager.pop();
            }
            return;
        }

        if (inputManager.isUpPressed()) {
            currentButtonIndex = (currentButtonIndex - 1 + answerButtons.length) % answerButtons.length;
            updateButtonStyles();
        } else if (inputManager.isDownPressed()) {
            currentButtonIndex = (currentButtonIndex + 1) % answerButtons.length;
            updateButtonStyles();
        } else if (inputManager.isEnterPressed()) {
            handleAnswerSelection(currentButtonIndex);
        }
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
