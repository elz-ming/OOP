package com.game.javex.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.javex.inouts.InputManager;

public class EntityManager {
	private World world;
	private TiledMap map;
	private String selectedWorld;
	
	private Array<Terrain> terrains;
	private Array<Boundary> boundaries;
	private Array<FlagBorder> flagBorders;
	private Array<Signboard> signboards;
	private Array<TreasureChest> treasureChests;
	private Array<Coin> coins;
	
	private Player player;
	private Array<Enemy> enemies;
	private InputManager inputManager;
	
	private int enemiesKilled = 0;
	private int coinsCollected = 0;
	private int treasureChestSolved = 0;
	
	private Array<Flag> flags;
	
	public EntityManager(World world, TiledMap map, String selectedWorld, InputManager inputManager) {
		this.world = world;
		this.map = map;
		this.selectedWorld = selectedWorld;
		this.inputManager = inputManager;
		this.enemies = new Array<>();
		this.terrains = new Array<>();
		this.boundaries = new Array<>();
		this.flagBorders = new Array<>();
		this.signboards = new Array<>();
		this.treasureChests = new Array<>();
		
		this.coins = new Array<>();
		this.enemies = new Array<>();
		flags = new Array<>();
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
		
		// Create Flag
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
		    Rectangle rect = ((RectangleMapObject) object).getRectangle();

		    Vector2 position = new Vector2(rect.getX(), rect.getY());
		    int width = (int)rect.getWidth();
		    int height = (int)rect.getHeight();
		    
		    createFlag(world, position, width, height); // Pass 'world' object here
		}
		
//		Create FlagBorder
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 position = new Vector2(rect.getX(), rect.getY());
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            createFlagBorder(position, width, height);
        }
		
		// Create INSTruction Signboard
		int signboard_counter = 1;
		for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
		    Rectangle rect = ((RectangleMapObject) object).getRectangle();
		    Vector2 position = new Vector2(rect.getX(), rect.getY());
		    int width = (int)rect.getWidth();
		    int height = (int)rect.getHeight();

		    // Generate a unique identifier
		    int identifier = signboard_counter++;

		    // Now pass this identifier when creating a new SignBoard
		    createSignboard(position, width, height, selectedWorld, identifier);
		}
		
		// Create INFOrmation Signboard
		for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
		    Rectangle rect = ((RectangleMapObject) object).getRectangle();
		    Vector2 position = new Vector2(rect.getX(), rect.getY());
		    int width = (int)rect.getWidth();
		    int height = (int)rect.getHeight();

		    // Generate a unique identifier
		    int identifier = signboard_counter++;

		    // Now pass this identifier when creating a new SignBoard
		    createSignboard(position, width, height, selectedWorld, identifier);
		}
			
//		Create TreasureChest
		int treasureChest_counter = 1;
		if (map != null && map.getLayers().get(7) != null) {
		    for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
		        Rectangle rect = ((RectangleMapObject) object).getRectangle();
		        Vector2 position = new Vector2(rect.getX(), rect.getY());
		        int width = (int) rect.getWidth();
		        int height = (int) rect.getHeight();
		        
		        // Generate a unique identifier
		        int identifier = treasureChest_counter++;
		        
		     // Now pass this identifier when creating a new TreasureChest
		        createTreasureChest(position, width, height, selectedWorld, identifier);
		    }
		}
		

		//		Create Coin
		for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
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
	
	public void createFlag(World world, Vector2 position, int width, int height) {
        Flag flag = new Flag(world, position, width, height);
        flags.add(flag);
    }
	
	public void createFlagBorder(Vector2 position, int width, int height) {
		flagBorders.add(new FlagBorder(world, position, width, height));
	}
	
	public void createSignboard(Vector2 position, int width, int height, String selectedWorld, int identifier) {
	    signboards.add(new Signboard(world, position, width, height, selectedWorld, identifier));
	}
	
	public void createTreasureChest(Vector2 position, int width, int height, String selectedWorld, int identifier) {
	    treasureChests.add(new TreasureChest(world, position, width, height, selectedWorld, identifier));
	}
	
	public void createPlayer(Vector2 position) {
		this.player = new Player(world, position, inputManager);
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
		}
		
		for (TreasureChest treasureChest : treasureChests) {
	        treasureChest.update(delta);
	    }
		int solved = 0;
		for (TreasureChest treasureChest : treasureChests) {
			
			if (treasureChest.getSolved()) {
				solved++;
			}
		}
		treasureChestSolved = solved;
		
		if (treasureChestSolved == treasureChests.size) {
			Array<FlagBorder> flagBorderToRemove = new Array<>();
			for (FlagBorder flagBorder : flagBorders) {
				world.destroyBody(flagBorder.getBody());
				flagBorderToRemove.add(flagBorder);
			}
			flagBorders.removeAll(flagBorderToRemove, true);
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
		
		
		 for (Coin coin : coins) {
		        if (!coin.isCollected()) {
		            coin.update(delta);
		        }
		 }
		Array<Coin> coinToRemove = new Array<>();
		for (Coin coin : coins) {
		    if (coin.isCollected()) {
		        coin.update(delta); 
		        world.destroyBody(coin.getBody());
		        coinToRemove.add(coin);
		        coinsCollected();
		    }
		}
		coins.removeAll(coinToRemove, true);
		
		for (Flag flag : flags) {
	        flag.update(delta); // Update each flag
	    }
	}
	
	public void render(SpriteBatch spriteBatch) {
		for (Flag flag : flags) {
	        flag.render(spriteBatch); // Render each flag
	    }
		
		for (FlagBorder flagBorder : flagBorders) {
			flagBorder.render(spriteBatch);
		}
		
		for (Signboard signboard : signboards) {
			signboard.render(spriteBatch);
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
	
	public Array<Signboard> getSignboards() {
	    return signboards;
	}
	
	public Array<TreasureChest> getTreasureChests() {
	    return treasureChests;
	}
	
	public int getTreasureChestsSolved() {
		return treasureChestSolved;
	} 
	
	public int getTreasureChestsTotal() {
		return treasureChests.size;
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
	    
	public void coinsCollected() {
	    coinsCollected++;
	}
	 
	public int getScore() {
	    return enemiesKilled + coinsCollected;
	}


	
	public int getCoinsCollected() {
	    return coinsCollected;
	}
}
