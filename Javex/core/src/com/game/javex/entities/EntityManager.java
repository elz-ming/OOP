package com.game.javex.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class EntityManager {
	private World world;
	private Player player;
	private Array<Enemy> enemies;
	private Array<Object> terrains;
	private Array<Object> coins;
	
	public EntityManager(World world) {
		this.world = world;
		this.enemies = new Array<>();
		this.terrains = new Array<>();
		this.coins = new Array<>();
	}
	
	public void createPlayer(Vector2 position) {
		this.player = new Player(world, position);
	}
	
	public void createEnemy(Vector2 position, boolean isBoss) {
		enemies.add(new Enemy(world, position, isBoss));
	}
	
	public void createTerrain(Vector2 position, int width, int height) {
		terrains.add(new Object(world, position, width, height, false, true));
	}
	
	public void createCoin(Vector2 position, int width, int height) {
		coins.add(new Object(world, position, width, height, true, false));
	}
	
	
	
	public void update(float dt) {
		if (player != null) {
			
		}
		
		Array<Enemy> enemyToRemove = new Array<>();
		for (Enemy enemy : enemies) {
			if (enemy.getHealth() <= 0) {
				world.destroyBody(enemy.getBody());
				enemyToRemove.add(enemy);
			}
		}
		enemies.removeAll(enemyToRemove, true);
		
		Array<Object> coinToRemove = new Array<>();
		for (Object coin : coins) {
			if (coin.isCollected()) {
//				world.destroyBody(coin.getBody());
				coinToRemove.add(coin);
			}
		}
		coins.removeAll(coinToRemove, true);
	}
	
	public void dispose() {
		
	}
	
	public Player getPlayer() {
	    return this.player;
	}
	
	public Array<Enemy> getEnemies() {
		return this.enemies;
	}

	public void loadEntitiesFromMap(TiledMap map) {
		// TODO Auto-generated method stub
		
	}
}
