package com.game.javex.scenes;

import com.badlogic.gdx.utils.Disposable;
import com.game.javex.inouts.*;

// This is the Scene Parent Class
public abstract class Scene implements Disposable{
    protected SceneManager sceneManager;
    protected InputManager inputManager;
    protected OutputManager outputManager;

    protected Scene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        this.sceneManager = sceneManager;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    protected abstract void update(float dt);
    protected abstract void render(float dt);
//    protected abstract void resize(float dt);
}
