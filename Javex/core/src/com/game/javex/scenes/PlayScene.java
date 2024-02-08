package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayScene extends AbstractScene {

	private static SpriteBatch spriteBatch;

	protected PlayScene(SceneManager sceneManager) {
		super(sceneManager, spriteBatch);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleInput(float dt) {
	    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	        // Pause the game
	        sceneManager.push(new PauseScene(sceneManager, spriteBatch));
	    }
	}

	@Override
	protected void update(float dt) {
		handleInput(dt);
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void render(SpriteBatch sb) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
