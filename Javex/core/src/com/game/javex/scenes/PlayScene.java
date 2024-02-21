package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.javex.Constants;
import com.game.javex.entities.Enemy;
import com.game.javex.entities.EntityManager;
import com.game.javex.entities.Player;
import com.game.javex.inouts.*;
import com.game.javex.tools.AiControlManager;
import com.game.javex.tools.CollisionManager;
import com.game.javex.tools.PlayerControlManager;

public class PlayScene extends Scene {
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	private SpriteBatch sceneBatch;
	private SpriteBatch entityBatch;
	
	private HUDManager hudManager;
	private EntityManager entityManager;
	private PlayerControlManager playerControlManager;
	private AiControlManager aiControlManager;
	private CollisionManager collisionManager;
	
	public PlayScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
		// Using universal attribute across all scenes
		super(sceneManager, inputManager, outputManager);
		width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
    	backgroundImage.setSize(width, height); // Set the size to fill the screen
    	backgroundImage.setZIndex(0); // Make sure the background is drawn first (before the buttons)
	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		viewport = new FitViewport(width, height, camera);

		hudManager = new HUDManager();
		
		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();
		sceneBatch = new SpriteBatch();
		entityBatch = new SpriteBatch();
		
		
//		Initialize entityManager and create relevant entities in the game world
		entityManager = new EntityManager(world);
		initialize();
		Player player = entityManager.getPlayer();
		Enemy boss = entityManager.getBoss();
		
//		Initialize playerControlManager and link the control to the main player
		playerControlManager = new PlayerControlManager(player, inputManager);
		
//		Initialize aiControlManager and link the control to the boss
		aiControlManager = new AiControlManager(boss, player);
		
//		Initialize collisionManager to listen for collisions in the game world
		collisionManager = new CollisionManager();
		world.setContactListener(collisionManager);
	}
	
	@Override
	protected void update(float dt) {
		handleInput();
		world.step(1 / 60f, 6, 2);

	    cameraUpdate(dt);
	    playerControlManager.update(dt);
	    aiControlManager.update(dt);
	    entityManager.update(dt);
	    hudManager.update(entityManager.getEnemiesKilled(), entityManager.getCoinsCollected());
		
	    if (entityManager.getTotalEnemies() == 0) { // end logic to be improved in the future
	        sceneManager.set(new EndScene(sceneManager, inputManager, outputManager, hudManager));
	    }
	}
	
	@Override
	public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sceneBatch.begin();
			backgroundImage.draw(sceneBatch, 1);
		sceneBatch.end();
		
		entityBatch.setProjectionMatrix(camera.combined);
		entityBatch.begin();
			entityManager.render(entityBatch);
		entityBatch.end();
		
////		For debug purposes
//		if (b2dr != null && world != null && camera != null) {
//			b2dr.render(world, camera.combined.scl(Constants.PPM));
//		}
	
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
		
		entityManager.createTerrain(new Vector2(0, 0), 1056, 32);
		entityManager.createTerrain(new Vector2(0, 32), 32, 32);
		entityManager.createTerrain(new Vector2(128, 32), 32, 32);
		entityManager.createTerrain(new Vector2(320, 32), 32, 32);
		entityManager.createTerrain(new Vector2(512, 32), 32, 32);
		entityManager.createTerrain(new Vector2(924, 32), 32, 32);
	}

	@Override
	protected void handleInput() {
	    if (inputManager.isReturnPressed()) {
	        sceneManager.push(new PauseScene(sceneManager, inputManager, outputManager));
	        try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
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
