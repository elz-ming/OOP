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

    // For Menu, Pause, and End Scene, there's no dt and sb
    // For Play Scene
    protected abstract void handleInput(float dt);
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch sb);

}
