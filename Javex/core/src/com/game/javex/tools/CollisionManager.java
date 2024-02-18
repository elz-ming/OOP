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
		
		System.out.println(collisionDef);
		System.out.println(Constants.PLAYER_HEAD_BIT | Constants.ENEMY_BIT);
		
		
		switch (collisionDef) {
	
//			Player land on enemy top
			case Constants.PLAYER_HEAD_BIT | Constants.ENEMY_BIT:
				System.out.println("landed on enemy head");
	            if (fixA.getFilterData().categoryBits == Constants.PLAYER_HEAD_BIT)
	                ((Enemy) fixB.getUserData()).hitOnHead();
	            else
	                ((Enemy) fixA.getUserData()).hitOnHead();
	            break;
				
	            
//	        Enemy touch player from side
			case Constants.PLAYER_BIT | Constants.ENEMY_BIT:
	            if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT)
	                ((Player) fixA.getUserData()).hit((Enemy) fixB.getUserData());
	            else
	                ((Player) fixB.getUserData()).hit((Enemy) fixA.getUserData());
	            break;
			
			case Constants.PLAYER_BIT | Constants.TERRAIN_BIT:
                // Determine which fixture is the player
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player)fixA.getUserData()).setCanJump(true);
                } else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player)fixB.getUserData()).setCanJump(true);
                }
                break;
                
			case Constants.ENEMY_BIT | Constants.TERRAIN_BIT:
                // Determine which fixture is the enemy
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                    ((Enemy)fixA.getUserData()).reverseVelocity();
                } else if (fixB.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                    ((Enemy)fixB.getUserData()).reverseVelocity();
                }
                break;
		
			case Constants.ENEMY_BIT | Constants.ENEMY_BIT:
		        // If you want enemies to interact with each other (e.g., bounce off each other)
	            ((Enemy)fixA.getUserData()).reverseVelocity();
	            ((Enemy)fixB.getUserData()).reverseVelocity();
		        break;
		}
	}
	
	@Override 
	public void endContact(Contact contact) {
		if ((fixA.getFilterData().categoryBits == Constants.PLAYER_BIT && fixB.getFilterData().categoryBits == Constants.TERRAIN_BIT) ||
			(fixB.getFilterData().categoryBits == Constants.PLAYER_BIT && fixA.getFilterData().categoryBits == Constants.TERRAIN_BIT)) {
			if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
				((Player)fixA.getUserData()).setCanJump(false);
			} else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
				((Player)fixB.getUserData()).setCanJump(false);
            }
	    }
	}
	
	// ========================= //
	// ===== EMPTY METHODS ===== //	
	// ========================= //
	@Override public void preSolve(Contact contact, Manifold oldManifold) {}
	@Override public void postSolve(Contact contact, ContactImpulse impulse) {}
}
