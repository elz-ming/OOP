package com.game.javex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.game.javex.inouts.*;
import com.game.javex.scenes.*;


// This is our SIMULATION LIFECYCLE MANAGER
public class SimulationLifecycleManager extends ApplicationAdapter{
	private SceneManager sceneManager;
	private InputManager inputManager;
	private OutputManager outputManager;
	
	@Override
	public void create() {
		inputManager = new InputManager();
		outputManager = new OutputManager();
		sceneManager = new SceneManager();
		 
		sceneManager.push(new MenuScene(sceneManager, inputManager, outputManager));
	}

	@Override
	public void render() {
	    sceneManager.update(Gdx.graphics.getDeltaTime());
	    sceneManager.render();
	}
	
	
	@Override
    public void resize(int width, int height) {
        sceneManager.resize(width, height);
    }
	
	
	@Override
	public void dispose () {
		sceneManager.dispose();
		outputManager.dispose();
	}
}