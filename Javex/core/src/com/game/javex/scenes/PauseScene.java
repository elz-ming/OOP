package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class PauseScene extends AbstractScene {
    private BitmapFont pauseFont;
    private GlyphLayout pauseLayout;

    public PauseScene(SceneManager sceneManager, SpriteBatch spriteBatch) {
        super(sceneManager, spriteBatch);
        pauseFont = new BitmapFont();
        pauseFont.getData().setScale(10);
        pauseFont.setColor(Color.WHITE);
        pauseLayout = new GlyphLayout(pauseFont, "PAUSE");
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        pauseLayout.setText(pauseFont, "PAUSE");
        float x = (Gdx.graphics.getWidth() - pauseLayout.width) / 2;
        float y = (Gdx.graphics.getHeight() / 2) + (pauseLayout.height / 2);
        pauseFont.draw(spriteBatch, "PAUSE", x, y);
        spriteBatch.end();
    }


    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Unpause the game
            sceneManager.pop();
        }
    }
    
    @Override
    protected void handleInput(float dt) {
       
    }

    @Override
    public void resize(int width, int height) {
        // If you're using a Stage with Scene2D.ui, you would update the viewport here:
        // stage.getViewport().update(width, height, true);
    }

    
    @Override
    public void pause() {
        // Since this is a pause screen, you might not need to do anything when the game is paused again.
    }
    
    @Override
    protected void render() {
        // Empty implementation
    }

    @Override
    protected void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        pauseLayout.setText(pauseFont, "PAUSE");
        float x = (Gdx.graphics.getWidth() - pauseLayout.width) / 2;
        float y = (Gdx.graphics.getHeight() / 2) + (pauseLayout.height / 2);
        pauseFont.draw(spriteBatch, "PAUSE", x, y);
        spriteBatch.end();
    }

    
    
    
    @Override
    public void resume() {
        // When the game resumes, you might need to reinitialize some resources or just leave it if nothing is needed.
    }

    @Override
    public void hide() {
        // This is called when the screen is no longer the current screen.
        // You might want to dispose of the pause screen resources if they won't be reused.
    }

    @Override
    public void dispose() {
    	pauseFont.dispose();
        // Dispose of any resources that you've created for this screen to prevent memory leaks.
        // For example, if you created a Stage for your UI, you would call stage.dispose();
    }

    // Existing methods

  
    @Override
    public void update() {
        handleInput();
        // Here you can put other update logic for your pause screen if necessary.
    }

    @Override
    protected void update(float dt) {
        // This method should contain the logic for updating your scene
        // If you're handling input without dt, just call the handleInput() method here
        handleInput();
    }
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	

	

	

    // Other methods you might need to override from AbstractScene
    // ...
}
