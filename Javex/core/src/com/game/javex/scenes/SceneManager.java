package com.game.javex.scenes;

import java.util.Stack;

public class SceneManager {
    private Stack<Scene> scenes;
    
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
		while (!scenes.isEmpty()) {
            scenes.pop().dispose();
        }
		scenes.push(scene);
	}
	
	public void update(float dt) {
    	scenes.peek().update(dt);
    }
    
    public void render() {
    	scenes.peek().render();
    }
    
    public void dispose() {
    	while (!scenes.isEmpty()) {
            scenes.pop().dispose();
        }
    }
}


