package com.game.javex.scenes;

import java.util.Stack;

import com.game.javex.inouts.AudioManager; // Import AudioManager

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    private Stack<AbstractScene> scenes;
    private AbstractScene currentScene;
    private AudioManager audioManager; // Declare AudioManager
    
	
	public SceneManager(AudioManager audioManager) {
		scenes = new Stack<AbstractScene>();
		audioManager = new AudioManager(); // Initialize AudioManager
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
		//Audio 
		if (scene instanceof StartMenuScene) {
	        audioManager.playMenuMusic();
	    } else if (scene instanceof PlayScene) {
	        audioManager.playGameplayMusic();
	    } //else if (scene instanceof GameOverScene) {
	        //audioManager.playGameoverMusic();
	    //}
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
