package com.game.javex.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class EntityManager {
	private World world;
	private Player player;
	private Enemy boss;
	private Array<Enemy> enemies;
	private Array<Terrain> terrains;
	private Array<Reward> coins;
	
	private int enemiesKilled = 0;
	private int coinsCollected = 0;
	
	public EntityManager(World world) {
		this.world = world;
		this.enemies = new Array<>();
		this.terrains = new Array<>();
		this.coins = new Array<>();
	}
	
	public void initialize() {
		createPlayer(new Vector2(64, 32));
		
		createBoss(new Vector2(576, 32));
		createEnemy(new Vector2(192, 32));
		createEnemy(new Vector2(384, 32));
		
		createCoin(new Vector2(256, 32));
		createCoin(new Vector2(448, 32));
		
		createTerrain(new Vector2(0, 0), 1056, 32);
		createTerrain(new Vector2(0, 32), 32, 32);
		createTerrain(new Vector2(128, 32), 32, 32);
		createTerrain(new Vector2(320, 32), 32, 32);
		createTerrain(new Vector2(512, 32), 32, 32);
		createTerrain(new Vector2(924, 32), 32, 32);
	}
	
	public void createPlayer(Vector2 position) {
		this.player = new Player(world, position);
	}
	
	public void createBoss(Vector2 position) {
		this.boss = new Enemy(world, position, true);
	}
	
	public void createEnemy(Vector2 position) {
		enemies.add(new Enemy(world, position, false));
	}
	
	public void createTerrain(Vector2 position, int width, int height) {
		terrains.add(new Terrain(world, position, width, height));
	}
	
	public void createCoin(Vector2 position) {
		coins.add(new Reward(world, position));
	}

	public void update(float dt) {
		if (player != null) {
			player.update(dt);
		}
		
		if (boss != null) {
			boss.update(dt);
            if (boss.getKilled()) {
                world.destroyBody(boss.getBody());
                boss = null; 
                enemyKilled(); // Remove the boss if it's dead
            }
        }
		
		Array<Enemy> enemyToRemove = new Array<>();
		for (Enemy enemy : enemies) {
			enemy.update(dt);
			if (enemy.getKilled()) {
				world.destroyBody(enemy.getBody());
				enemyToRemove.add(enemy);
				enemyKilled();
			}
		}
		enemies.removeAll(enemyToRemove, true);
		
		Array<Reward> coinToRemove = new Array<>();
		for (Reward coin : coins) {
			if (coin.isCollected()) {
				world.destroyBody(coin.getBody());
				coinToRemove.add(coin);
				coinsCollected();
			}
		}
		coins.removeAll(coinToRemove, true);
	}
	
	public void render(SpriteBatch spriteBatch) {
		if (player != null) {
			player.render(spriteBatch);
		}
		
		if (boss != null) {
			boss.render(spriteBatch);
		}
		
		for (Enemy enemy : enemies) {
			enemy.render(spriteBatch);
		}
		
		for (Reward coin : coins) {
			coin.render(spriteBatch);
		}
		
		for (Terrain terrain : terrains) {
			terrain.render(spriteBatch);
		}
	}
	
	public Player getPlayer() {
	    return this.player;
	}
	
	public Enemy getBoss() {
	    return this.boss;
	}
	
	public Array<Enemy> getEnemies() {
		return this.enemies;
	}
	
	 public void enemyKilled() {
	        enemiesKilled++;
	    }

	    // Getter method for the number of enemies killed
	    public int getEnemiesKilled() {
	        return enemiesKilled;
	    }
	    
	 public void coinsCollected() {
	    	coinsCollected++;
	    }
	 public int getCoinsCollected() {
	        return coinsCollected;
	    }
	 
	 public int getTotalEnemies() {
		    return enemies.size + (boss != null ? 1 : 0);
		    
		}
	    
	    
}
