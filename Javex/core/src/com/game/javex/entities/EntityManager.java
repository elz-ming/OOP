package com.game.javex.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
	
	public Body createPlayer(Vector2 position, int width, int height) {
		this.player = new Player(world, position, width, height);
		
		return this.player.getBody();
	}
	
	public Body createEnemy(Vector2 position, int width, int height) {
		Enemy enemy = new Enemy(world, position, width, height);
		enemies.add(enemy);
	    return enemy.getBody();
		//nemies.add(new Enemy(world, position, width, height));
	}
	
	public void createTerrain(Vector2 position, int width, int height) {
		terrains.add(new Object(world, position, width, height, false, true));
		System.out.println("Terrain printed");
	}
	
	public void createCoin(Vector2 position, int diameter) {
		coins.add(new Object(world, position, diameter, diameter, true, false));
	}
	
	
	
	public void update(float dt) {
		if (player != null) {
			player.update(dt);
		}
//		
//		for (Enemy enemy : enemies) {
//			enemy.update(dt);
//		}
	}
	
	public void dispose() {
		
	}
	
	public Player getPlayer() {
	    return this.player;
	}

	public void loadEntitiesFromMap(TiledMap map) {
		// TODO Auto-generated method stub
		
	}
}
