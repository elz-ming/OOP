package com.game.javex.inouts;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Keyboard implements InputProcessor {
    private boolean isInAir = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveDown = false;

    // Indicates whether the input manager is in game mode or menu mode
    private boolean gameMode = true; // true for game mode, false for menu mode

    public Keyboard() {
        // Initialize the input processor
        com.badlogic.gdx.Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (gameMode) {
            // Game mode controls
            switch (keycode) {
                case Keys.UP:
                    if (!isInAir) {
                        isInAir = true; // Start jumping
                        // Trigger jump here, e.g., player.jump()
                    }
                    break;
                case Keys.LEFT:
                    moveLeft = true;
                    break;
                case Keys.RIGHT:
                    moveRight = true;
                    break;
                case Keys.DOWN:
                    moveDown = true; // Start ducking or specific action
                    break;
            }
        } else {
            // Menu mode controls
            switch (keycode) {
                case Keys.UP:
                    // Navigate up in the menu
                    break;
                case Keys.DOWN:
                    // Navigate down in the menu
                    break;
                case Keys.ENTER:
                    // Select a menu item
                    break;
                // Add other menu navigation keys as needed
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (gameMode) {
            // Game mode key release handling
            switch (keycode) {
                case Keys.LEFT:
                    moveLeft = false;
                    break;
                case Keys.RIGHT:
                    moveRight = false;
                    break;
                case Keys.DOWN:
                    moveDown = false; // Stop ducking or specific action
                    break;
            }
        } else {
            // Menu mode key release handling (if needed)
        }
        return false;
    }

    public void switchToGameMode() {
        this.gameMode = true;
    }

    public void switchToMenuMode() {
        this.gameMode = false;
    }

    // Getter methods for game mode movement flags
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
