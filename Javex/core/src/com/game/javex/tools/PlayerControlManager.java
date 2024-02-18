package com.game.javex.tools;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.game.javex.entities.Player;
import com.game.javex.inouts.InputManager;

public class PlayerControlManager{
	private Player player;
	private final InputManager inputManager;
	private boolean canJump = true;
	
	public PlayerControlManager(Player player, InputManager inputManager) {
		this.player = player;
        this.inputManager = inputManager;
    }
	
	public void update(float dt) {
        int currKey = inputManager.getCurrKey();
        int prevKey = inputManager.getPrevKey();
        
        if (currKey == Keys.LEFT) {
        	player.moveLeft();
        	canJump = true;
        }
        
        if (currKey == Keys.RIGHT) {
        	player.moveRight();
        	canJump = true;
        }
        
        if (currKey == Keys.UP) {
            if (canJump) { 
            	player.jump();
            	canJump = false; // Prevent further jumps until conditions allow it again
            }
        }

        // Other player control logic goes here
    }

    // Method to reset jump capability, likely to be called when the player lands
    public void resetJump() {
        canJump = true;
    }
}
