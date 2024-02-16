package com.game.javex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.javex.scenes.SceneManager;
import com.game.javex.scenes.StartMenuScene;


// This is our SIMULATION LIFECYCLE MANAGER
public class Javex extends ApplicationAdapter {
	private SpriteBatch batch;
	private SceneManager sceneManager;
	
	@Override
	 public void create() {
        batch = new SpriteBatch();
        sceneManager = new SceneManager();
        sceneManager.push(new StartMenuScene(sceneManager)); // Pass the batch here
    }

	@Override
	public void render() {
	    float delta = Gdx.graphics.getDeltaTime();

	    sceneManager.update(delta);
	    sceneManager.render(batch);
	}
	@Override
	public void dispose () {
		super.dispose();
	}
}

// TODO Edmund
