package com.game.javex.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.javex.scenes.SceneManager;

// This is the Scene Parent Class
public abstract class AbstractScene implements Screen {
    protected SceneManager sceneManager;

    protected AbstractScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    // For Menu, Pause and End Scene
    protected abstract void handleInput();
    protected abstract void update();
    protected abstract void render();
    
    // For Play Scene
    protected abstract void handleInput(float dt);
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch sb);
    
    public abstract void dispose();
    // Implement the common Screen methods here or leave them abstract to be implemented by subclasses
}
