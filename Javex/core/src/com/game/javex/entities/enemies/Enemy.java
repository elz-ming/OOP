package com.game.javex.entities.enemies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Enemy extends ApplicationAdapter{
	
	private OrthographicCamera camera;
	private float health;
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w/2,h/2);
	}
	
	@Override 
	public void render() {
		//Render
//		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width /2, height/2);
	}
	
	@Override
	public void dispose() {
		
	}
}
