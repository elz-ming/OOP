package com.game.javex.scenes;

import com.badlogic.gdx.Screen;
import com.game.javex.inouts.*;

// This is the Scene Parent Class
public abstract class AbstractScene implements Screen {
    protected SceneManager sceneManager;
    protected InputManager inputManager;
    protected OutputManager outputManager;

    protected AbstractScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        this.sceneManager = sceneManager;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    protected abstract void update(float dt);
}
