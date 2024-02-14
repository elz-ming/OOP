package com.game.javex.inouts;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Keyboard implements InputProcessor {
    private boolean isInAir = false; // Indicates if the player is in the air or not
    private boolean moveLeft = false; // Move left
    private boolean moveRight = false; // Move right
    private boolean moveDown = false; // Move down or specific action

    public Keyboard() {
        // Initialize the input processor
        com.badlogic.gdx.Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.UP:
                if (!isInAir) {
                    isInAir = true; // Indicate the start of a jump, actual jump logic to be handled elsewhere
                    // Trigger jump here, e.g., player.jump()
                }
                break;
            case Keys.LEFT:
                moveLeft = true; // Start moving left
                break;
            case Keys.RIGHT:
                moveRight = true; // Start moving right
                break;
            case Keys.DOWN:
                moveDown = true; // Start ducking or specific action
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                moveLeft = false; // Stop moving left
                break;
            case Keys.RIGHT:
                moveRight = false; // Stop moving right
                break;
            case Keys.DOWN:
                moveDown = false; // Stop ducking or specific action
                break;
            // Note: You might want to reset isInAir elsewhere based on your game's physics/collision detection
        }
        return false;
    }

    // Getter methods for movement flags and isInAir status
    public boolean isMovingLeft() {
        return moveLeft;
    }

    public boolean isMovingRight() {
        return moveRight;
    }

    public boolean isMovingDown() {
        return moveDown;
    }

    public boolean getIsInAir() {
        return isInAir;
    }

    public void setIsInAir(boolean isInAir) {
        this.isInAir = isInAir;
    }

    // Implement other required methods of InputProcessor with empty bodies
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
}
