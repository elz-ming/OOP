package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.game.javex.inouts.*;

// This is the Scene Parent Class
public abstract class Scene implements Disposable{
    protected SceneManager sceneManager;
    protected InputManager inputManager;
    protected OutputManager outputManager;
    
    protected float width;
    protected float height;
    
    protected Stage stage;
    protected Skin skin;
    protected Image backgroundImage;
    
    protected TextButton[] menuButtons;
    protected int currentButtonIndex = 0;

    protected Scene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        this.sceneManager = sceneManager;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }
    
    public void update(float delta) {
    	handleInput();
    	stage.act(delta);
    }  
    
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
	}
	
    public void dispose() {
    	if (stage != null) {
	        stage.dispose();
	    }
	    if (skin != null) {
	        skin.dispose();
	    }
    }
    
    
    protected abstract void resize(int width, int height);
    
    protected void updateButtonStyles() {
        for (int i = 0; i < menuButtons.length; i++) {
            if (i == currentButtonIndex) {
                menuButtons[i].setColor(Color.YELLOW);
            } else {
                menuButtons[i].setColor(Color.WHITE);
            }
        }
    }
    
    protected abstract void handleInput();
}