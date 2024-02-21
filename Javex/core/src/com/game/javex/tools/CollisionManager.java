package com.game.javex.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.javex.Constants;
import com.game.javex.entities.Enemy;
import com.game.javex.entities.Player;
import com.game.javex.entities.Reward;

public class CollisionManager implements ContactListener{
	private Fixture fixA;
	private Fixture fixB;
	private int collisionDef;
	
	@Override
	public void beginContact(Contact contact) {
		fixA = contact.getFixtureA();
		fixB = contact.getFixtureB();
		
		collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		switch (collisionDef) {
	
//			Player land on enemy top
			case Constants.PLAYER_BIT | Constants.ENEMY_HEAD_BIT:
	            if (fixA.getFilterData().categoryBits == Constants.ENEMY_HEAD_BIT) {
	            	((Enemy) fixA.getUserData()).hitOnHead();
	            	((Player) fixB.getUserData()).setCanJump(true);
	            }
	            else {
	                ((Enemy) fixB.getUserData()).hitOnHead();
	            	((Player) fixA.getUserData()).setCanJump(true);
	            }
	            break;
				
	            
//	        Enemy touch player from side
			case Constants.PLAYER_BIT | Constants.ENEMY_BIT:
	            if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
	                ((Player) fixA.getUserData()).hit((Enemy) fixB.getUserData());
	            	((Player) fixA.getUserData()).setCanJump(true);
	            }
	            else {
	                ((Player) fixB.getUserData()).hit((Enemy) fixA.getUserData());
	            	((Player) fixB.getUserData()).setCanJump(true);
	            }
	            break;
	            
//		    Player can jump again after touching terrain
			case Constants.PLAYER_BIT | Constants.REWARD_BIT:
                if (fixA.getFilterData().categoryBits == Constants.REWARD_BIT) {
                    ((Reward)fixA.getUserData()).collect();
                } else if (fixB.getFilterData().categoryBits == Constants.REWARD_BIT) {
                    ((Reward)fixB.getUserData()).collect();
                }
                break;
			
//	        Player can jump again after touching terrain
			case Constants.PLAYER_BIT | Constants.TERRAIN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player)fixA.getUserData()).setCanJump(true);
                } else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player)fixB.getUserData()).setCanJump(true);
                }
                break;
                
//              Enemy will move in opposite direction once touch terrain
			case Constants.ENEMY_BIT | Constants.TERRAIN_BIT:
                // Determine which fixture is the enemy
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                	Enemy enemy = (Enemy)fixA.getUserData();
                	if(!enemy.getIsBoss()) {
                		enemy.moveOpposite();
                	}
                } else if (fixB.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                	Enemy enemy = (Enemy)fixB.getUserData();
                	if(!enemy.getIsBoss()) {
                		enemy.moveOpposite();
                	}
                }             
                break;
    		
//              Enemies will move in opposite direction once touch each other
			case Constants.ENEMY_BIT | Constants.ENEMY_BIT:
		        // If you want enemies to interact with each other (e.g., bounce off each other)
	            ((Enemy)fixA.getUserData()).moveOpposite();
	            ((Enemy)fixB.getUserData()).moveOpposite();
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
