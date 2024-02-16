package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.game.javex.entities.EntityManager;
import com.game.javex.inouts.InputManager;
import com.game.javex.inouts.OutputManager;

public class PlayScene extends AbstractScene {
	
	
	private BitmapFont timerFont;
    private float timer = 60; // 60 seconds for the countdown
    private GlyphLayout timerLayout;
    private SpriteBatch spriteBatch; // You should not have this as static in your scene.
	
//	For all entities
	private World world;
	private EntityManager entityManager;
	
//	For player
	private OrthographicCamera camera;
	private Viewport viewport;
	
//	For terrain
	private Box2DDebugRenderer b2dr;
	
	
	
	protected PlayScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
		super(sceneManager, inputManager, outputManager);
		entityManager = new EntityManager();
		
		this.spriteBatch = spriteBatch; // Assign the passed spriteBatch to the local spriteBatch.
        
        // Initialize the timer font and layout
        timerFont = new BitmapFont();
        timerFont.getData().setScale(1);
        timerFont.setColor(Color.WHITE);
        timerLayout = new GlyphLayout();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		update(delta);
        
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Begin drawing
        spriteBatch.begin();
        
        // Draw the timer
        String timerText = String.format("%.1f", timer);
        timerLayout.setText(timerFont, timerText);
        float x = Gdx.graphics.getWidth() - timerLayout.width - 20;
        float y = Gdx.graphics.getHeight() - 20;
        timerFont.draw(spriteBatch, timerText, x, y);

        // End drawing
        spriteBatch.end();
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
	        Gdx.app.log("PlayScene", "ESC key pressed. Pausing the game.");
	        sceneManager.push(new PauseScene(sceneManager, inputManager, outputManager));
	    }
	}
	@Override
	protected void update(float dt) {
		handleInput(dt);

	}

	@Override
	protected void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void dispose() {

	}

}
