package com.game.javex.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.game.javex.inouts.*;

// This is the Scene Parent Class
public abstract class Scene implements Disposable{
    protected SceneManager sceneManager;
    protected InputManager inputManager;
    protected OutputManager outputManager;
    
    protected float width;
    protected float height;
    protected Image backgroundImage;

    protected Scene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        this.sceneManager = sceneManager;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    protected abstract void update(float dt);
    protected abstract void render();
    protected abstract void handleInput();
//    protected abstract void resize(float dt);
}
