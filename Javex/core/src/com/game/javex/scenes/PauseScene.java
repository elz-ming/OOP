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
    private SpriteBatch spriteBatch;
    
    public PauseScene(SceneManager sceneManager) {
        super(sceneManager);
        spriteBatch = new SpriteBatch();
        
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
    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Unpause the game
            sceneManager.pop();
        }
    }
    
	@Override   
	protected void update(float dt) {
		// TODO Auto-generated method stub
		handleInput(dt);
	}
    
    @Override
    protected void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        pauseLayout.setText(pauseFont, "PAUSE");
        float x = (Gdx.graphics.getWidth() - pauseLayout.width) / 2;
        float y = (Gdx.graphics.getHeight() / 2) + (pauseLayout.height / 2);
        pauseFont.draw(sb, "PAUSE", x, y);
        sb.end();
    }
    
    @Override
    public void dispose() {
    	pauseFont.dispose();
        // Dispose of any resources that you've created for this screen to prevent memory leaks.
        // For example, if you created a Stage for your UI, you would call stage.dispose();
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
    public void resume() {
        // When the game resumes, you might need to reinitialize some resources or just leave it if nothing is needed.
    }

    @Override
    public void hide() {
        // This is called when the screen is no longer the current screen.
        // You might want to dispose of the pause screen resources if they won't be reused.
    }

    

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
