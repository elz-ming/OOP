package com.game.javex.scenes;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    private Stack<AbstractScene> scenes;
    private AbstractScene currentScene;
	
	public SceneManager() {
		scenes = new Stack<AbstractScene>();
	}

	// Only for pause	
	public void push(AbstractScene scene) {
		scenes.push(scene);
	}
	
	// Only for pause
	public void pop() {
		scenes.pop().dispose();
	}
	
	// For Menu, Play and End
	public void set(AbstractScene scene) {
		scenes.pop().dispose();
		scenes.push(scene);
	}
    
    public void update(float dt) {
    	currentScene = scenes.peek();
    	currentScene.update(dt);
    }
    
    public void render(SpriteBatch sb) {
    	currentScene = scenes.peek();
    	currentScene.render(sb);
    }
}
