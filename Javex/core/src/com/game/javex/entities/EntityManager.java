package com.game.javex.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class EntityManager {
	private World world;
	private TiledMap map;
	private Player player;
	private Enemy boss;
	private Array<Enemy> enemies;
	private Array<Reward> coins;
	private Array<Terrain> terrains;
	
	private int enemiesKilled = 0;
	private int coinsCollected = 0;
	
	public EntityManager(World world, TiledMap map) {
		this.world = world;
		this.map = map;
		this.enemies = new Array<>();
		this.terrains = new Array<>();
		this.coins = new Array<>();
	}
	
	public void initialize() {
		createPlayer(new Vector2(64, 320));
		
		createBoss(new Vector2(3002, 64));
		
//		createEnemy(new Vector2(192, 32));
//		createEnemy(new Vector2(384, 32));
//		
//		createCoin(new Vector2(256, 32));
//		createCoin(new Vector2(448, 32));
		
//		Render Common Objects
		for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createTerrain(position, width, height);
        }
		
//		Render Earth
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createTerrain(position, width, height);
        }
		
//		Render Mars
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createTerrain(position, width, height);
        }
		
//		Render Venus
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createTerrain(position, width, height);
        }
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
	
	public void createCoin(Vector2 position) {
		coins.add(new Reward(world, position));
	}
	
	public void createTerrain(Vector2 position, int width, int height) {
		terrains.add(new Terrain(world, position, width, height));
	}

	public void update(float delta) {
		if (player != null) {
			player.update(delta);
		}
		
		if (boss != null) {
			boss.update(delta);
            if (boss.getKilled()) {
                world.destroyBody(boss.getBody());
                boss = null; 
                enemyKilled(); // Remove the boss if it's dead
            }
        }
		
//		Array<Enemy> enemyToRemove = new Array<>();
//		for (Enemy enemy : enemies) {
//			enemy.update(delta);
//			if (enemy.getKilled()) {
//				world.destroyBody(enemy.getBody());
//				enemyToRemove.add(enemy);
//				enemyKilled();
//			}
//		}
//		enemies.removeAll(enemyToRemove, true);
//		
//		Array<Reward> coinToRemove = new Array<>();
//		for (Reward coin : coins) {
//			if (coin.isCollected()) {
//				world.destroyBody(coin.getBody());
//				coinToRemove.add(coin);
//				coinsCollected();
//			}
//		}
//		coins.removeAll(coinToRemove, true);
	}
	
	public void render(SpriteBatch spriteBatch) {
		if (player != null) {
			player.render(spriteBatch);
		}
		
		if (boss != null) {
			boss.render(spriteBatch);
		}
//		
//		for (Enemy enemy : enemies) {
//			enemy.render(spriteBatch);
//		}
//		
//		for (Reward coin : coins) {
//			coin.render(spriteBatch);
//		}
		
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

    public int getEnemiesKilled() {
        return enemiesKilled;
    }
    
    public int getTotalEnemies() {
		return enemies.size + (boss != null ? 1 : 0);
	 } 
	    
	public void coinsCollected() {
	    coinsCollected++;
	}
	 
	public int getCoinsCollected() {
	    return coinsCollected;
	}
	 
	 public int getTotalCoins() {
		return coins.size;
	 } 
}
