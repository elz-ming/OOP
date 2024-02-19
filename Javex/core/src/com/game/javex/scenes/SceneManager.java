package com.game.javex.scenes;

import java.util.Stack;

public class SceneManager {
    private Stack<Scene> scenes;
    private Scene currentScene;
    
	public SceneManager() {
		scenes = new Stack<Scene>();
	}

	// Only for pause	
	public void push(Scene scene) {
		scenes.push(scene);
	}
	
	// Only for pause
	public void pop() {
		if (!scenes.isEmpty()) {
	        scenes.pop().dispose();
	}
	}
	
	// For Menu, Play and End
	public void set(Scene scene) {
		scenes.pop().dispose();
		scenes.push(scene);
	}
    
    public void render(float dt) {
    	currentScene = scenes.peek();
    	currentScene.update(dt);
    	currentScene.render(dt);
    }
    
    public void popAllAndSet(Scene Scene) {
        // Dispose of the current scene if it exists
        if (currentScene != null) {
            currentScene.dispose();
        }

        // Clear and dispose of all scenes in the stack
        while (!scenes.isEmpty()) {
        	currentScene.dispose();
            scenes.pop().dispose();
        }

        // Set the new scene
        scenes.push(Scene);
        currentScene = Scene;
    }

}


