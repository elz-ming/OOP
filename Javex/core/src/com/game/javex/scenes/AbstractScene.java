package com.game.javex.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.javex.scenes.SceneManager;

// This is the Scene Parent Class
public abstract class AbstractScene implements Screen {
    protected SceneManager sceneManager;
    protected SpriteBatch spriteBatch;

    protected AbstractScene(SceneManager sceneManager, SpriteBatch spriteBatch) {
        this.sceneManager = sceneManager;
        this.spriteBatch = spriteBatch;
    }

    // For Menu, Pause, and End Scene
    protected abstract void handleInput();
    protected abstract void update();
    
    // You could provide a default implementation here if it makes sense for your game architecture
    protected void render() {
        // Default implementation could be empty
    }

    // For Play Scene
    protected abstract void handleInput(float dt);
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch spriteBatch);

}
