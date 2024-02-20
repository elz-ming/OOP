package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {
	private boolean upKey = false;
    private boolean downKey = false;
    private boolean leftKey = false;
    private boolean rightKey = false;
    private boolean enterKey = false;
    private boolean returnKey = false;

    public InputManager() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
    	switch (keycode) {
	        case Keys.UP:
	            upKey = true;
	            break;
	        case Keys.DOWN:
	            downKey = true;
	            break;
	        case Keys.LEFT:
	            leftKey = true;
	            break;
	        case Keys.RIGHT:
	            rightKey = true;
	            break;
	        case Keys.ENTER:
	        case Keys.SPACE: // Assuming you want SPACE to act like ENTER
	            enterKey = true;
	            break;
	        case Keys.ESCAPE:
	            returnKey = true;
	            break;
	    }
	    return false;
    }
    
    @Override
    public boolean keyUp(int keycode) {
    	switch (keycode) {
	        case Keys.UP:
	            upKey = false;
	            break;
	        case Keys.DOWN:
	            downKey = false;
	            break;
	        case Keys.LEFT:
	            leftKey = false;
	            break;
	        case Keys.RIGHT:
	            rightKey = false;
	            break;
	        case Keys.ENTER:
	        case Keys.SPACE: // Assuming you want SPACE to act like ENTER
	            enterKey = false;
	            break;
	        case Keys.BACKSPACE:
	            returnKey = false;
	            break;
	    }
	    return true;
    }
    
    public boolean isUpPressed() { return upKey; }
    public boolean isDownPressed() { return downKey; }
    public boolean isLeftPressed() { return leftKey; }
    public boolean isRightPressed() { return rightKey; }
    public boolean isEnterPressed() { return enterKey; }
    public boolean isReturnPressed() { return returnKey; }

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
