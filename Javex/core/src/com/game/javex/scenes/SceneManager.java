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
		scenes.pop().dispose();
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
}
