package com.game.javex;

import com.badlogic.gdx.ApplicationAdapter;
import com.game.javex.inouts.AudioManager; // Import AudioManager
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.javex.inouts.InoutManager;
import com.game.javex.scenes.SceneManager;
import com.game.javex.scenes.StartMenuScene;


// This is our SIMULATION LIFECYCLE MANAGER
public class Javex extends ApplicationAdapter {
	private SpriteBatch batch;
	private SceneManager sceneManager;
	private InoutManager inoutManager;
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
		sceneManager.update(Gdx.graphics.getDeltaTime());
		sceneManager.render(batch);
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
