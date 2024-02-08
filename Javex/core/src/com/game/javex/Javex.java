package com.game.javex;

import com.badlogic.gdx.ApplicationAdapter;
import com.game.javex.inouts.AudioManager; // Import AudioManager
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Javex extends ApplicationAdapter {
	private SpriteBatch batch;
	private SceneManager sceneManager;
	private AudioManager audioManager; // Declare audioManager at the class level
	
	 @Override
	  public void create() {
	     batch = new SpriteBatch();
	     audioManager = new AudioManager(); // Initialize AudioManager here
	     sceneManager = new SceneManager(audioManager); // Assume SceneManager modified to accept AudioManager
	     sceneManager.push(new StartMenuScene(sceneManager, audioManager)); // Pass AudioManager to your scenes if needed
	     audioManager.playMenuMusic(); // Start playing menu music
	 }

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		if (batch != null) {
            batch.dispose(); // Dispose of batch resources
        }
        if (audioManager != null) {
            audioManager.dispose(); // Ensure music is stopped and resources are freed
        }
	}
}

// TODO Edmund
