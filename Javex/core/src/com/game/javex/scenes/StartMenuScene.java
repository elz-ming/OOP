package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.game.javex.inouts.*;

public class StartMenuScene extends AbstractScene {
	private Stage stage;
    private Skin skin;
    
    private TextButton playButton, exitButton;
    private TextButton[] menuButtons;
    private int currentButtonIndex = 0;

    public StartMenuScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        // Using universal attribute across all scenes
    	super(sceneManager, inputManager, outputManager);
    	
    	// Creating own attributes specific to this scene
        stage = new Stage(new ScreenViewport());

        // Assuming you have a skin.json file in the assets directory
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

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

    public void handleInput() {
    	if (inputManager.getPrevKey() == Keys.UNKNOWN && 
                (inputManager.getCurrKey() == Keys.UP || inputManager.getCurrKey() == Keys.DOWN)) {
                currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
                updateButtonStyles();
                inputManager.resetKeys();
            }
    	
    	if (inputManager.getPrevKey() == Keys.UNKNOWN && inputManager.getCurrKey() == Keys.ENTER) {
            if (currentButtonIndex == 0) {
                sceneManager.set(new PlayScene(sceneManager, inputManager, outputManager));
            } else if (currentButtonIndex == 1) {
                Gdx.app.exit();
            }
            inputManager.resetKeys(); // Reset keys after handling
        }
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
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
	
	// ========================= //
	// ===== EMPTY METHODS ===== //	
	// ========================= //
	@Override public void show() {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
}