package com.game.javex.tools;

import com.badlogic.gdx.Input.Keys;
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
        int currKey = inputManager.getCurrKey();
        
        if (currKey == Keys.LEFT) {
        	player.moveLeft();
        }
        
        if (currKey == Keys.RIGHT) {
        	player.moveRight();
        }
        
        if (currKey == Keys.UP) {
            if (player.getCanJump()) { 
            	player.jump();
            }
        }
    }
}
