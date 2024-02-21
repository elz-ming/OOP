package com.game.javex.tools;

import com.game.javex.entities.Player;
import com.game.javex.inouts.InputManager;

public class PlayerControlManager{
	private Player player;
	private final InputManager inputManager;
	
	public PlayerControlManager(Player player, InputManager inputManager) {
		this.player = player;
        this.inputManager = inputManager;
    }
	
	public void update(float dt) {
        if (inputManager.isLeftPressed()) {
        	player.moveLeft();
        }
        
        if (inputManager.isRightPressed()) {
        	player.moveRight();
        }
        
        if (inputManager.isUpPressed()) {
            if (player.getCanJump()) { 
            	player.jump();
            }
        }
    }
}
