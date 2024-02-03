package com.game.javex.scenes;

import com.badlogic.gdx.Screen;
import com.game.javex.scenes.SceneManager;

public abstract class AbstractScene implements Screen {
    protected SceneManager sceneManager;

    public AbstractScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // Implement the common Screen methods here or leave them abstract to be implemented by subclasses
}
