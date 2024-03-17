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
	
	private Array<Terrain> terrains;
	private Array<Boundary> boundaries;
	private Flag flag;
	private Array<FlagBorder> flagBorders;
	private Array<SignBoard> signboards;
	private Array<TreasureChest> treasureChests;
	
	private Array<Coin> coins;
//	private Array<Letter> letters;
	
	private Player player;
	private Enemy boss;
	private Array<Enemy> enemies;

	
	
	private int enemiesKilled = 0;
	private int coinsCollected = 0;
	
	public EntityManager(World world, TiledMap map) {
		this.world = world;
		this.map = map;
		this.terrains = new Array<>();
		this.boundaries = new Array<>();
		this.flagBorders = new Array<>();
		this.signboards = new Array<>();
		this.treasureChests = new Array<>();
		
		this.coins = new Array<>();
		this.enemies = new Array<>();
	}
	
	public void initialize() {
//		Create Boundary
		for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createBoundary(position, width, height);
        }
		
//		Create Terrain
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createTerrain(position, width, height);
        }
		
//		Create Flag
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createFlag(position, width, height);
        }
		
//		Create FlagBorder
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createFlagBorder(position, width, height);
        }
		
//		Create Signboard
		for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createSignboard(position, width, height);
        }
		
//		Create TreasureChest
		for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createTreasureChest(position, width, height);
        }
		
//		Create Coin
		for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            
            createCoin(position);
        }
		
//		Create Player
		for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY()); 
            createPlayer(position);
        }
		
//		Create Enemy
		for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            
            createEnemy(position);
        }
	}
	
	
	
	public void createTerrain(Vector2 position, int width, int height) {
		terrains.add(new Terrain(world, position, width, height));
	}
	
	public void createBoundary(Vector2 position, int width, int height) {
		boundaries.add(new Boundary(world, position, width, height));
	}
	
	public void createFlag(Vector2 position, int width, int height) {
		this.flag = new Flag(world, position, width, height);
	}
	
	public void createFlagBorder(Vector2 position, int width, int height) {
		flagBorders.add(new FlagBorder(world, position, width, height));
	}
	
	public void createSignboard(Vector2 position, int width, int height) {
		signboards.add(new SignBoard(world, position, width, height));
	}
	
	public void createTreasureChest(Vector2 position, int width, int height) {
		treasureChests.add(new TreasureChest(world, position, width, height));
	}
	
	public void createPlayer(Vector2 position) {
		this.player = new Player(world, position);
	}
	
	public void createEnemy(Vector2 position) {
		enemies.add(new Enemy(world, position));
	}
	
	public void createCoin(Vector2 position) {
		coins.add(new Coin(world, position));
	}

	public void update(float delta) {
		if (player != null) {
			player.update(delta);
//			System.out.println(player.getReading());
//			System.out.printf("%b %b \n", player.getSolving(), player.getResetSolving());
		}
		
		if (boss != null) {
			boss.update(delta);
            if (boss.getKilled()) {
                world.destroyBody(boss.getBody());
                boss = null; 
                enemyKilled(); // Remove the boss if it's dead
            }
        }
		
		Array<Enemy> enemyToRemove = new Array<>();
		for (Enemy enemy : enemies) {
			enemy.update(delta);
			if (enemy.getKilled()) {
				world.destroyBody(enemy.getBody());
				enemyToRemove.add(enemy);
				enemyKilled();
			}
		}
		enemies.removeAll(enemyToRemove, true);
		
		Array<Coin> coinToRemove = new Array<>();
		for (Coin coin : coins) {
			if (coin.isCollected()) {
				world.destroyBody(coin.getBody());
				coinToRemove.add(coin);
				coinsCollected();
			}
		}
		coins.removeAll(coinToRemove, true);
	}
	
	public void render(SpriteBatch spriteBatch) {
		if (flag != null) {
			flag.render(spriteBatch);
		}
		
		for (TreasureChest treasureChest : treasureChests) {
			treasureChest.render(spriteBatch);
		}
		
		if (player != null) {
			player.render(spriteBatch);
		}
		
		for (Enemy enemy : enemies) {
			enemy.render(spriteBatch);
		}
		
		for (Coin coin : coins) {
			coin.render(spriteBatch);
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
