package com.game.javex.entities;

import com.game.javex.entities.enemies.EnemyManager;
import com.game.javex.entities.players.PlayerManager;
import com.game.javex.entities.terrains.TerrainManager;

public class EntityManager {
	
	private PlayerManager playerManager;
	private EnemyManager enemyManager;
	private TerrainManager terrainManager;
	
	public EntityManager() {
		playerManager = new PlayerManager();
		enemyManager = new EnemyManager();
		terrainManager = new TerrainManager();
	}
}
