package com.game.javex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.game.javex.inouts.*;
import com.game.javex.scenes.*;


// This is our SIMULATION LIFECYCLE MANAGER
public class SimulationLifecycleManager extends ApplicationAdapter {
	private SceneManager sceneManager;
	private InputManager inputManager;
	private OutputManager outputManager;
	
	 @Override
	 public void create() {
		 inputManager = new InputManager();
	     outputManager = new OutputManager(); // Initialize AudioManager here
	     outputManager.play("hadi.mp3", true);
	     
	     sceneManager = new SceneManager(); // Assume SceneManager modified to accept AudioManager
	     sceneManager.push(new MenuScene(sceneManager, inputManager, outputManager)); // Pass AudioManager to your scenes if needed
	 }

	@Override
	public void render() {
	    sceneManager.update(Gdx.graphics.getDeltaTime());
	    sceneManager.render();
	}
	
	@Override
	public void dispose () {
		sceneManager.dispose();
		outputManager.dispose();
	}
}