package com.game.javex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.game.javex.entities.Player;
import com.game.javex.inouts.InputManager;

public class PlayerControlManager {
	private Player player;
	private final InputManager inputManager;
	
	public PlayerControlManager(Player player, InputManager inputManager) {
		this.player = player;
        this.inputManager = inputManager;
    }
	
	public void update(float delta) {
        if (inputManager.isLeftPressed()) {
        	player.moveLeft(delta);
        }
        
        if (inputManager.isRightPressed()) {
        	player.moveRight(delta);
        }
        
        if (!inputManager.isLeftPressed() && !inputManager.isRightPressed()) {
        	player.slowDown(delta);
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.UP)) {
        	if (player.getCanJump()) {
        		player.jump(delta);
        	}
        }
    }
}
