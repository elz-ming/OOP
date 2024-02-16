import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LeaderboardScene extends AbstractScene {

    private Stage stage;
    private List<TextButton> buttons; // Array to store buttons\
    private SceneManager sceneManager;
    private float finalScore;

    public LeaderboardScene(SceneManager sceneManager, String backgroundImagePath, float finalScore) {
        super(new SpriteBatch(), backgroundImagePath);
        this.sceneManager = sceneManager;
        this.finalScore = finalScore;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buttons = new ArrayList<>(); // Initialize the list

        addButton("Back To Menu",0.5f, 0.5f);

    }

    private void addButton(String label,float customX, float customY) {
        Skin skin = new Skin(Gdx.files.internal("glassy-ui.json")); // Replace with your actual skin file
// Create a button
        TextButton button = new TextButton(label, skin);

        // Access the label inside the button and set its font size
        Label labell = button.getLabel();
        labell.setFontScale(0.5f); // Adjust label font size

        float buttonX = Gdx.graphics.getWidth() * customX - button.getWidth() * 0.5f;
        float gapFactor = 0.4f;
        float buttonY = Gdx.graphics.getHeight() * customY - button.getHeight() * 0.5f - buttons.size() * button.getHeight() * gapFactor;

        button.setPosition(buttonX, buttonY);



        // Add click listener to the button
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleButtonClick(label);
            }
        });

        // Add the button to the list and stage
        buttons.add(button);
        stage.addActor(button);
    }

    private void handleButtonClick(String label) {
        if (label.equals("Back To Menu")) {
            sceneManager.set(new StartMenuScene(sceneManager,"R.jpg" ));
        }
    }

    @Override
    public void update(float dt) {
        // Update the stage
        stage.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render() {
        // Draw the stage
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(spriteBatch, "Final Score: " + (int) finalScore * 1000, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 100);
        spriteBatch.end();
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.draw();

    }
@Override
    public void dispose() {
        for (TextButton button : buttons) {
            button.clear(); // Clears any listeners
            button.remove(); // Removes the button from the stage
            button.getSkin().dispose(); // Disposes of the button's skin
        }
        buttons.clear(); // Clears the list of buttons
        // Dispose of the stage's resources
        stage.getRoot().clearChildren();
        System.out.println("Number of actors after clearing: " + stage.getRoot().getChildren().size);
        stage.dispose();
    }

@Override
public void show() {
	// TODO Auto-generated method stub
	
}

@Override
public void render(float delta) {
	// TODO Auto-generated method stub
	
}

@Override
public void resize(int width, int height) {
	// TODO Auto-generated method stub
	
}

@Override
public void pause() {
	// TODO Auto-generated method stub
	
}

@Override
public void resume() {
	// TODO Auto-generated method stub
	
}

@Override
public void hide() {
	// TODO Auto-generated method stub
	
}

@Override
public boolean keyDown(int keycode) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean keyUp(int keycode) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean keyTyped(char character) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean touchDragged(int screenX, int screenY, int pointer) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean mouseMoved(int screenX, int screenY) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean scrolled(float amountX, float amountY) {
	// TODO Auto-generated method stub
	return false;
}

@Override
protected void handleInput(float dt) {
	// TODO Auto-generated method stub
	
}

@Override
protected void render(SpriteBatch sb) {
	// TODO Auto-generated method stub
	
}
}
﻿