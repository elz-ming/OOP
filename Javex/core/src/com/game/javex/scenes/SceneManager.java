package com.game.javex.scenes;

import java.util.Stack;

import com.badlogic.gdx.Gdx;

public class SceneManager {
    private Stack<Scene> scenes;
    
	public SceneManager() {
		scenes = new Stack<Scene>();
	}

	// Only for pause	
	public void push(Scene scene) {
		scenes.push(scene);
		scene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
	    scene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Update the layout of the new scene
	}
	
	public void update(float delta) {
    	scenes.peek().update(delta);
    }
    
    public void render() {
    	scenes.peek().render();
    }

    
    public void resize(int width, int height) {
        if (!scenes.isEmpty()) {
            scenes.peek().resize(width, height);
        }
    }
    
    public void dispose() {
    	while (!scenes.isEmpty()) {
            scenes.pop().dispose();
        }
    }
}


