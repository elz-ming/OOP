package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.game.javex.entities.EntityManager;
import com.game.javex.inouts.*;
	import com.game.javex.tools.AiControlManager;
	import com.game.javex.tools.CollisionManager;
	import com.game.javex.tools.PlayerControlManager;
	import com.game.javex.tools.Constants;
	
	public class PlayScene extends Scene {
		private OrthographicCamera camera;
		private Viewport viewport;
		
		private World world;
		private Box2DDebugRenderer b2dr;
		
		private EntityManager entityManager;
		private PlayerControlManager playerControlManager;
		private Array<AiControlManager> aiControlManagers;
		private CollisionManager collisionManager;
		
		private int currentLevel = 1;
		
		public PlayScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
			// Using universal attribute across all scenes
			super(sceneManager, inputManager, outputManager);
			
			
			// Creating own attributes specific to this scene	
			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();
			camera = new OrthographicCamera();
			camera.setToOrtho(false, w, h);
			viewport = new FitViewport(w, h, camera);
			
			world = new World(new Vector2(0, -9.8f), false);
			b2dr = new Box2DDebugRenderer();
			
	//		Initialize entityManager and create relevant entities in the game world
			entityManager = new EntityManager(world);
			initialize();
			Player player = entityManager.getPlayer();
			
	//		Initialize playerControlManager and link the control to the main player
			playerControlManager = new PlayerControlManager(player, inputManager);
			
	//		Initialize aiControlManager(s) and link the control to the each enemy
			aiControlManagers = new Array<>();
			for (Enemy enemy : entityManager.getEnemies()) {
	            AiControlManager aiControlManager = new AiControlManager(enemy, player);
	    		aiControlManagers.add(aiControlManager);
	        }
			
	//		Initialize collisionManager to listen for collisions in the game world
			collisionManager = new CollisionManager();
			world.setContactListener(collisionManager);
		}
		
		@Override
		protected void update(float dt) {
			world.step(1 /60f, 6, 2);
			
			cameraUpdate(dt);
			pauseListener(dt);
			playerControlManager.update(dt);
			for (AiControlManager aiControlManager : aiControlManagers) {
				aiControlManager.update(dt);
	        }
			entityManager.update(dt);
		}
		
		@Override
		public void render(float dt) {
	        // Clear the screen
	        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        b2dr.render(world, camera.combined.scl(Constants.PPM)); 
		}
		
		@Override
		public void dispose() {
			world.dispose();
			b2dr.dispose();
		}
		
		public void resize(int width, int height) {
			camera.setToOrtho(false, width /2, height /2);
		}
	
		private void initialize() {
			entityManager.createPlayer(new Vector2(64, 32));
			
			entityManager.createBoss(new Vector2(576, 32));
			entityManager.createEnemy(new Vector2(192, 32));
			entityManager.createEnemy(new Vector2(384, 32));
			
			entityManager.createCoin(new Vector2(256, 32));
			entityManager.createCoin(new Vector2(448, 32));
			
			entityManager.createTerrain(new Vector2(0, 0), 736, 32);
			entityManager.createTerrain(new Vector2(0, 32), 32, 32);
			entityManager.createTerrain(new Vector2(128, 32), 32, 32);
			entityManager.createTerrain(new Vector2(320, 32), 32, 32);
			entityManager.createTerrain(new Vector2(512, 32), 32, 32);
			entityManager.createTerrain(new Vector2(704, 32), 32, 32);
		}
	

	private Image backgroundImage;
	 
	private HUDManager hudManager;
	
	private int currentLevel = 1;
	
	public PlayScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
		// Using universal attribute across all scenes
		super(sceneManager, inputManager, outputManager);
		
		
		
		// Creating own attributes specific to this scene	
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/2, h/2);
		viewport = new FitViewport(w, h, camera);
		
		
		hudManager = new HUDManager();
		
		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();
		
	
		Texture backgroundTexture = new Texture(Gdx.files.internal("playbackground.png"));
	    backgroundImage = new Image(backgroundTexture);
	    backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		
		entityManager = new EntityManager(world);
		initialize();
		
		playerControlManager = new PlayerControlManager(entityManager.getPlayer(), inputManager);
//		aiControlManager = new AiControlManager(entityManager.getEnemies());	
		
		collisionManager = new CollisionManager();
		world.setContactListener(collisionManager);
	}
	
	@Override
	protected void update(float dt) {
		world.step(1 /60f, 6, 2);
		
		cameraUpdate(dt);
		pauseListener(dt);
		playerControlManager.update(dt);
		entityManager.update(dt);
		hudManager.update(entityManager.getEnemiesKilled(), entityManager.getCoinsCollected());
	}
	
	@Override
	public void render(float dt) {
	    // Clear the screen
	    Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    
	    SpriteBatch batch = new SpriteBatch();
        batch.begin();
        backgroundImage.draw(batch, 1);
        batch.end();
	    
	    if (b2dr != null && world != null && camera != null) {
	        b2dr.render(world, camera.combined.scl(Constants.PPM));
	    }

	    if (hudManager != null) {
	        hudManager.draw();
	    }
	}
	
	@Override
	public void dispose() {
		world.dispose();
	    b2dr.dispose();
	    entityManager.dispose();
	    camera = null;
	    viewport = null;
	    hudManager.dispose();
	   
		
	}
	
	public void resize(int width, int height) {
		camera.setToOrtho(false, width /2, height /2);
	}

	private void initialize() {
		entityManager.createPlayer(new Vector2(64, 32));
		
		entityManager.createBoss(new Vector2(576, 32));
		entityManager.createEnemy(new Vector2(192, 32));
		entityManager.createEnemy(new Vector2(384, 32));
		
		entityManager.createCoin(new Vector2(256, 32));
		entityManager.createCoin(new Vector2(448, 32));
		
		entityManager.createTerrain(new Vector2(0, 0), 736, 32);
		entityManager.createTerrain(new Vector2(0, 32), 32, 32);
		entityManager.createTerrain(new Vector2(128, 32), 32, 32);
		entityManager.createTerrain(new Vector2(320, 32), 32, 32);
		entityManager.createTerrain(new Vector2(512, 32), 32, 32);
		entityManager.createTerrain(new Vector2(704, 32), 32, 32);
	}

	protected void pauseListener(float dt) {
		int currKey = inputManager.getCurrKey();
	    if (currKey == Keys.ESCAPE) {
	        Gdx.app.log("PlayScene", "ESC key pressed. Pausing the game.");
	        sceneManager.push(new PauseScene(sceneManager, inputManager, outputManager));
	    }
	    
	   else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
		   		
            	Gdx.app.log("PauseScene", "L key pressed. Ending the game.");
            	
                sceneManager.set(new EndScene(sceneManager, inputManager, outputManager, hudManager));
	    }
	}
	
	public void cameraUpdate(float dt) {
		Vector3 position = camera.position;
		position.x = entityManager.getPlayer().getBody().getPosition().x *Constants.PPM;
		position.y = entityManager.getPlayer().getBody().getPosition().y *Constants.PPM;
		camera.position.set(position);
		camera.update();
	}
}
