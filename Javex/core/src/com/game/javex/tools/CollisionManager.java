package com.game.javex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.javex.Constants;
import com.game.javex.entities.Enemy;
import com.game.javex.entities.Player;
import com.game.javex.entities.SignBoard;
import com.game.javex.entities.Coin;

public class CollisionManager implements ContactListener{
	private Fixture fixA;
	private Fixture fixB;
	private int collisionDef;
	private boolean playerOnSignboard = false;
	private String currentSignboardIdentifier = null;
	
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
	            
//		    Player collect rewards
			case Constants.PLAYER_BIT | Constants.COIN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.COIN_BIT) {
                    ((Coin)fixA.getUserData()).collect();
                } else if (fixB.getFilterData().categoryBits == Constants.COIN_BIT) {
                    ((Coin)fixB.getUserData()).collect();
                }
                break;
			
//	        Player can jump again after touching terrain
			case Constants.PLAYER_BOTTOM_BIT | Constants.TERRAIN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BOTTOM_BIT) {
                    ((Player)fixA.getUserData()).setCanJump(true);
                } else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BOTTOM_BIT) {
                    ((Player)fixB.getUserData()).setCanJump(true);
                }
                break;
                
//              Enemy will move in opposite direction once touch terrain
			case Constants.ENEMY_BIT | Constants.TERRAIN_BIT:
                // Determine which fixture is the enemy
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                	Enemy enemy = (Enemy)fixA.getUserData();
            		enemy.moveOpposite();
                } else if (fixB.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                	Enemy enemy = (Enemy)fixB.getUserData();
            		enemy.moveOpposite();
                }             
                break;
    		
//              Enemies will move in opposite direction once touch each other
			case Constants.ENEMY_BIT | Constants.ENEMY_BIT:
		        // If you want enemies to interact with each other (e.g., bounce off each other)
	            ((Enemy)fixA.getUserData()).moveOpposite();
	            ((Enemy)fixB.getUserData()).moveOpposite();
		        break;
		  
		        
			case Constants.PLAYER_BIT | Constants.SIGNBOARD_BIT:
				 if (collisionDef == (Constants.PLAYER_BIT | Constants.SIGNBOARD_BIT)) {
				        if (fixA.getFilterData().categoryBits == Constants.SIGNBOARD_BIT && fixA.getUserData() instanceof String) {
				            currentSignboardIdentifier = (String) fixA.getUserData();
				        } else if (fixB.getFilterData().categoryBits == Constants.SIGNBOARD_BIT && fixB.getUserData() instanceof String) {
				            currentSignboardIdentifier = (String) fixB.getUserData();
				        }
				        playerOnSignboard = true;
				    }
			break;
		        
		        
		}
	}
	
	@Override 
	public void endContact(Contact contact) {
		 Fixture fixA = contact.getFixtureA();
		 Fixture fixB = contact.getFixtureB();
		    
		 if ((fixA.getUserData() instanceof String && fixB.getUserData() instanceof Player) ||
			        (fixB.getUserData() instanceof String && fixA.getUserData() instanceof Player)) {
			        playerOnSignboard = false;
			        currentSignboardIdentifier = null;
			    }
	}
	
	public String getCurrentSignboardIdentifier() {
	    return currentSignboardIdentifier;
	}
	
	// ========================= //
	// ===== EMPTY METHODS ===== //	
	// ========================= //
	@Override public void preSolve(Contact contact, Manifold oldManifold) {}
	@Override public void postSolve(Contact contact, ContactImpulse impulse) {}
}
