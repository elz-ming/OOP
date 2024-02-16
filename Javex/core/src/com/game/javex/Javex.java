package com.game.javex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.game.javex.inouts.*;
import com.game.javex.scenes.*;


// This is our SIMULATION LIFECYCLE MANAGER
public class Javex extends ApplicationAdapter {
	private SceneManager sceneManager;
	private InputManager inputManager;
	private OutputManager outputManager;
	
	 @Override
	  public void create() {
		 inputManager = new InputManager();
	     outputManager = new OutputManager(); // Initialize AudioManager here
	     sceneManager = new SceneManager(); // Assume SceneManager modified to accept AudioManager
	     sceneManager.push(new StartMenuScene(sceneManager, inputManager, outputManager)); // Pass AudioManager to your scenes if needed
	 }

	@Override
	public void render() {
	    float dt = Gdx.graphics.getDeltaTime();
	    sceneManager.update(dt);
	    sceneManager.render(dt);
	}
	@Override
	public void dispose () {
		outputManager.dispose();
	}
}
