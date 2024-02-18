package com.game.javex.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.game.javex.entities.Enemy;
import com.game.javex.entities.Player;
import com.game.javex.tools.Constants;

public class CollisionManager implements ContactListener{
	private Fixture fixA;
	private Fixture fixB;
	
	
	@Override
	public void beginContact(Contact contact) {
		fixA = contact.getFixtureA();
		fixB = contact.getFixtureB();
		
		int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		Fixture playerFixture = fixA.getUserData() instanceof Player ? fixA : fixB;
		
		switch (collisionDef) {
			case Constants.PLAYER_BIT | Constants.ENEMY_BIT :
				
				Fixture enemyFixture = playerFixture == fixA ? fixB : fixA;
				
				if (attackEnemy(playerFixture.getBody(), enemyFixture.getBody(), contact)) {
					Enemy enemy = (Enemy)enemyFixture.getUserData();
					enemy.reduceHealth();
				}
				
				if (attackPlayer(playerFixture.getBody(), enemyFixture.getBody(), contact)) {
					Player player = (Player)playerFixture.getUserData();
					player.reduceHealth();
					System.out.println(player.getHealth());
				}
				
				break;
				
			case Constants.PLAYER_BIT | Constants.REWARD_BIT :
				Fixture coinFixture = playerFixture == fixA ? fixB : fixA;
				
//				coinFixture.setIsCollected();
				
				break;
				
				
			case Constants.PLAYER_BIT | Constants.TERRAIN_BIT : 
				Fixture terrainFixture = playerFixture == fixA ? fixB : fixA;
				
				break;
		}
		
	}
	
	private boolean attackEnemy(Body player, Body enemy, Contact contact) {
		Vector2 playerPosition = player.getPosition();
		Vector2 enemyPosition = enemy.getPosition();
		
		float playerBottom = Math.round(playerPosition.y - 32 /Constants.PPM);
		float enemyTop = Math.round(enemyPosition.y + 16 /Constants.PPM); 
		
		if (playerBottom >= enemyTop) {
			WorldManifold worldManifold = contact.getWorldManifold();
			for (Vector2 point : worldManifold.getPoints()) {
				if (point.y >= enemyTop && player.getLinearVelocity().y < 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean attackPlayer(Body player, Body enemy, Contact contact) {
		Vector2 playerPosition = player.getPosition();
		Vector2 enemyPosition = enemy.getPosition();
		
		float playerLeft = Math.round(playerPosition.x - 16 /Constants.PPM);
		float playerRight = Math.round(playerPosition.x + 16 /Constants.PPM);
		float enemyLeft = Math.round(enemyPosition.x - 16 /Constants.PPM); 
		float enemyRight = Math.round(enemyPosition.x + 16 /Constants.PPM); 
		
		if (playerRight >= enemyLeft) {
			WorldManifold worldManifold = contact.getWorldManifold();
			for (Vector2 point : worldManifold.getPoints()) {
				if (point.x >= enemyLeft && (enemy.getLinearVelocity().x < 0 || player.getLinearVelocity().x > 0)) {
					return true;
				}
			}
		}
		
		if (playerLeft <= enemyRight) {
			WorldManifold worldManifold = contact.getWorldManifold();
			for (Vector2 point : worldManifold.getPoints()) {
				if (point.x <= enemyRight && (enemy.getLinearVelocity().x > 0 || player.getLinearVelocity().x <0)) {
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	// ========================= //
	// ===== EMPTY METHODS ===== //	
	// ========================= //
	@Override public void endContact(Contact contact) {}
	@Override public void preSolve(Contact contact, Manifold oldManifold) {}
	@Override public void postSolve(Contact contact, ContactImpulse impulse) {}
	
}
