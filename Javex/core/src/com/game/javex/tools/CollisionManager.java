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
import com.game.javex.entities.Signboard;
import com.game.javex.entities.Coin;
import com.game.javex.entities.TreasureChest;;

public class CollisionManager implements ContactListener{
	private boolean playerOnSignboard = false;
	private String currentSignboardIdentifier = null;
	private String currentTreasureChestIdentifier;
	private String currentChestIdentifier = null;
	 private boolean playerOnChest = false;
	
//	For contacts would affect the physics (velocity and acceleration
	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		Player player;
		
		switch (collisionDef) {         
//	        #1 PLAYER &&& ENEMY
//	        Lose
			case Constants.PLAYER_BIT | Constants.ENEMY_BIT:
				Enemy enemy_1;
	            if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
	            	player = (Player)fixA.getUserData();
	            	enemy_1 = (Enemy)fixB.getUserData();
	            } else {
	            	player = (Player)fixB.getUserData();
	            	enemy_1 = (Enemy)fixA.getUserData();
	            }
	            
	            if (!enemy_1.getKilled()) {
	            	player.setKilled();
	            }
	            break;
	        
			case Constants.PLAYER_BIT | Constants.TERRAIN_TOP_BIT:
	            if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
	            	player = (Player)fixA.getUserData();
	            } else {
	            	player = (Player)fixB.getUserData();
	            }
	            player.setCanJump(true);
	            break;    
	        
//    		#2 PLAYER BOTTOM &&& BOUNDARY TOP
//    	    Lose
			case Constants.PLAYER_BIT | Constants.BOUNDARY_TOP_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                	player = (Player)fixA.getUserData();
                } else {
                	player = (Player)fixB.getUserData();
                }
                player.setKilled();
                break;
                         
//          #3 PLAYER &&& FLAG
//    		WIN
			case Constants.PLAYER_BIT | Constants.FLAG_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                	player = (Player)fixA.getUserData();
                } else {
                	player = (Player)fixA.getUserData();
                }
                player.setWon();
                break;
                
                
//        	#4 PLAYER &&& SIGNBOARD
//        	Popup Signboard
			case Constants.PLAYER_BIT | Constants.SIGNBOARD_BIT:
                if (fixA.getFilterData().categoryBits == Constants.SIGNBOARD_BIT) {
                	currentSignboardIdentifier = (String)fixA.getUserData();
                } else {
                	currentSignboardIdentifier = (String)fixB.getUserData();
                }
				playerOnSignboard = true;
                break;
                
//          #5 PLAYER &&& TREASURE CHEST
//          Popup Quiz
			case Constants.PLAYER_BIT | Constants.TREASURE_CHEST_BIT:
	           
	            TreasureChest chest;

	            if (fixA.getUserData() instanceof Player) {
	                player = (Player) fixA.getUserData();
	                chest = (TreasureChest) fixB.getUserData();
	            } else {
	                player = (Player) fixB.getUserData();
	                chest = (TreasureChest) fixA.getUserData();
	            }

	            if (!player.isEngagedWithChest()) {
	                player.setEngagedWithChest(true);
	                Gdx.app.log("TreasureChest", "engaged treasure chest");
	                currentChestIdentifier = chest.getIdentifier();
	                // Add your code to push the QuizScene here.
	            }
	            break;
	            
	            

			

//          #9 ENEMY &&& TERRAIN
//          Enemy move in opposite direction
			case Constants.ENEMY_BIT | Constants.TERRAIN_BIT:
				Enemy enemy;
                // Determine which fixture is the enemy
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                	enemy = ((Enemy)fixA.getUserData());
                } else {
                	enemy = ((Enemy)fixB.getUserData());
                }
                enemy.moveOpposite();
                break;
    		
//			#10 ENEMY &&& ENEMY
//          Enemies will move in opposite direction once touch each other
			case Constants.ENEMY_BIT | Constants.ENEMY_BIT:
	            ((Enemy)fixA.getUserData()).moveOpposite();
	            ((Enemy)fixB.getUserData()).moveOpposite();
		        break;
		}
	}
	
	@Override 
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		 Player player;
		
		 switch (collisionDef) { 
		 	case Constants.PLAYER_BIT | Constants.SIGNBOARD_BIT:
				playerOnSignboard = false;
				currentSignboardIdentifier = null;
		        break;
		        
		       
			case Constants.PLAYER_BIT | Constants.TREASURE_CHEST_BIT:
				if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
		        	player = (Player)fixA.getUserData();
		        } else {
		        	player = (Player)fixB.getUserData();
		        }
				player.setSolving(false);
	            if (currentChestIdentifier != null) {
	                player.setEngagedWithChest(false);
	                currentChestIdentifier = null;
	            }
	            break;
		}
	}		

	public String getCurrentSignboardIdentifier() {
	    return currentSignboardIdentifier;
	}
	
	public String getCurrentTreasureChestIdentifier() {
        return currentTreasureChestIdentifier;
    }
	
	 public boolean isPlayerOnChest() {
	        return playerOnChest;
	    }
	
	@Override 
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		Player player;
		
		switch (collisionDef) {
	//		#1 PLAYER &&& ENEMY HEAD
	//		Enemy disappears, score increases
			case Constants.PLAYER_BIT | Constants.ENEMY_HEAD_BIT:
				Enemy enemy;
	            if (fixA.getFilterData().categoryBits == Constants.ENEMY_HEAD_BIT) {
	            	enemy = (Enemy) fixA.getUserData();
	            }
	            else {
	            	enemy = (Enemy) fixB.getUserData();
	            }
	            enemy.setKilled();
	            contact.setEnabled(false);
	            break;
	            
//      	#5 PLAYER &&& COIN
//	    	Coins disappear, score increases
			case Constants.PLAYER_BIT | Constants.COIN_BIT:
				Coin coin;
	            if (fixA.getFilterData().categoryBits == Constants.COIN_BIT) {
	                coin = (Coin)fixA.getUserData();
	            } else {
	                coin = (Coin)fixB.getUserData();
	            }
	            coin.collect();
	            contact.setEnabled(false);
	            break;
	        
			case Constants.PLAYER_BIT | Constants.TERRAIN_TOP_BIT:
	            contact.setEnabled(false);
	            break;
	            
			case Constants.PLAYER_BIT | Constants.SIGNBOARD_BIT:
                contact.setEnabled(false);
                break;
                
			case Constants.PLAYER_BIT | Constants.TREASURE_CHEST_BIT:
  				contact.setEnabled(false);
  				break;
		}
	}
	
	@Override 
	public void postSolve(Contact contact, ContactImpulse impulse) {}
}
