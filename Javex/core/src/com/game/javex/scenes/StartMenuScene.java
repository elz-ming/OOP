package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.scenes.AbstractScene;
import com.game.javex.scenes.SceneManager;

public class StartMenuScene extends AbstractScene {
    private static SpriteBatch spriteBatch;
	private Stage stage;
    private Skin skin;
    private TextButton playButton, exitButton;
    private TextButton[] menuButtons;
    private int currentButtonIndex = 0;

    public StartMenuScene(SceneManager sceneManager) {
        super(sceneManager, spriteBatch);
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
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (currentButtonIndex == 0) {
                // Play button action
                sceneManager.set(new PlayScene(sceneManager)); // Assuming you have a PlayScene class
            } else if (currentButtonIndex == 1) {
                // Exit button action
                Gdx.app.exit();
            }
        }
    }
    
    public void update() {
    	handleInput();
    }  

	@Override
	protected void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
	}
	
	@Override
    public void dispose() {
        // Dispose of resources (like textures, sounds) and cleanup
        // If you have other disposables, dispose of them here
		stage.dispose();
    }
	
	// ========================= //
	// ===== EMPTY METHODS ===== //	
	// ========================= //
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
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
	protected void handleInput(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		
	}	
}
