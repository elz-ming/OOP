package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {
    private int currKey = Keys.UNKNOWN;
    private int prevKey = Keys.UNKNOWN;

    public InputManager() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
    	prevKey = currKey; // Update previous key
        currKey = keycode; // Update current key
        
        return false;
    }
    
    @Override
    public boolean keyUp(int keycode) {
    	if (currKey == keycode) {
            currKey = Keys.UNKNOWN; // Reset current key if it's the one released
        }
        return true;
    }
    
	    public void resetKeys() {
	    	prevKey = Keys.UNKNOWN;
	    	currKey = Keys.UNKNOWN;
	    }
	    
	    public int getPrevKey() {
	    	return prevKey;
	    }
	    
	    public int getCurrKey() {
	    	return currKey;
	    }

    // ========================= //
 	// ===== EMPTY METHODS ===== //	
 	// ========================= //
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
}
