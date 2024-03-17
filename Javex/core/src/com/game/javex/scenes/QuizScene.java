	package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;


public class QuizScene extends Scene {
    private TextButton[] answerButtons;
    private Label questionLabel;
    private QuizQuestion quizQuestion;
    private Skin skin;

    public QuizScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager, QuizQuestion quizQuestion) {
        super(sceneManager, inputManager, outputManager);
        this.quizQuestion = quizQuestion;

        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

        // Create the question label
        questionLabel = new Label(quizQuestion.getQuestion(), skin);
        questionLabel.setFontScale(0.5f);
        questionLabel.setPosition(50, Gdx.graphics.getHeight() - 100);
        stage.addActor(questionLabel);

        // Create the answer buttons
        answerButtons = new TextButton[quizQuestion.getAnswers().length];
        for (int i = 0; i < answerButtons.length; i++) {
            final int index = i;
            answerButtons[i] = new TextButton(quizQuestion.getAnswers()[i], skin);
            answerButtons[i].setPosition(50, Gdx.graphics.getHeight() - 150 - i * 60);
            answerButtons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (index == quizQuestion.getCorrectAnswerIndex()) {
                        // Correct answer
                        sceneManager.pop(); // Go back to the PlayScene
                    } else {
                        // Incorrect answer
                        // You can handle incorrect answers here (e.g., display a message or deduct points)
                    }
                }
            });
            stage.addActor(answerButtons[i]);
        }
    }
}
