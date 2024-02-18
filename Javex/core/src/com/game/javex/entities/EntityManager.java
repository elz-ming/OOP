package com.game.javex.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class EntityManager implements Disposable{
	private World world;
	private Player player;
	private Enemy boss;
	private Array<Enemy> enemies;
	private Array<Terrain> terrains;
	private Array<Reward> coins;
	
	public EntityManager(World world) {
		this.world = world;
		this.enemies = new Array<>();
		this.terrains = new Array<>();
		this.coins = new Array<>();
	}
	
	@Override
	public void dispose() {
		
	}
	
	public void createPlayer(Vector2 position) {
		this.player = new Player(world, position);
	}
	
	public void createEnemy(Vector2 position) {
		enemies.add(new Enemy(world, position, false));
	}
	
	public void createBoss(Vector2 position) {
		this.boss = new Enemy(world, position, true);
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
		
		Array<Enemy> enemyToRemove = new Array<>();
		for (Enemy enemy : enemies) {
			if (enemy.getKilled()) {
				world.destroyBody(enemy.getBody());
				enemyToRemove.add(enemy);
			}
		}
		enemies.removeAll(enemyToRemove, true);
		
		if (boss != null) {
            if (boss.getKilled()) {
                world.destroyBody(boss.getBody());
                boss = null; // Remove the boss if it's dead
            }
        }
		
		Array<Reward> coinToRemove = new Array<>();
		for (Reward coin : coins) {
			if (coin.isCollected()) {
				world.destroyBody(coin.getBody());
				coinToRemove.add(coin);
			}
		}
		coins.removeAll(coinToRemove, true);
	}
	
	public Player getPlayer() {
	    return this.player;
	}
	
	public Array<Enemy> getEnemies() {
		return this.enemies;
	}
}
