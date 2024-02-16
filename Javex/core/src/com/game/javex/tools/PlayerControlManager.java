package com.game.javex.tools;

import com.badlogic.gdx.Input.Keys;
import com.game.javex.inouts.InputManager;

public class PlayerControlManager {
	private final InputManager inputManager;
	private boolean canJump = true;
	
	public PlayerControlManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
	
	public void update() {
        // Logic for jumping based on current and previous keys
        if (inputManager.getCurrKey() == Keys.UP && canJump) {
            // Initiate jump
            jump();
            canJump = false; // Prevent further jumps until conditions allow it again
        }

        // Other player control logic goes here
    }

    private void jump() {
        // Jump logic implementation
        // This is where you'd typically apply a force or change the player's vertical velocity
    }

    // Method to reset jump capability, likely to be called when the player lands
    public void resetJump() {
        canJump = true;
    }
}
