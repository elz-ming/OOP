package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.game.javex.entities.EntityManager;

public class PlayScene extends AbstractScene {
	
//	For all entities
	private World world;
	private static SpriteBatch spriteBatch;
	private EntityManager entityManager;
	
//	For player
	private OrthographicCamera camera;
	private Viewport viewport;
	
//	For terrain
	private Box2DDebugRenderer b2dr;
	
	protected PlayScene(SceneManager sceneManager) {
		super(sceneManager);
		entityManager = new EntityManager();
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
	protected void handleInput(float dt) {
	    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	        // Pause the game
	        sceneManager.push(new PauseScene(sceneManager));
	    }
	}

	@Override
	protected void update(float dt) {
		handleInput(dt);

	}

	@Override
	protected void render(SpriteBatch sb) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void dispose() {

	}

}
