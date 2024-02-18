package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.math.MathUtils;

import com.game.javex.entities.EntityManager;
import com.game.javex.entities.Player;
import com.game.javex.inouts.*;
import com.game.javex.tools.AiControlManager;
import com.game.javex.tools.CollisionManager;
import com.game.javex.tools.PlayerControlManager;
import com.game.javex.tools.Constants;
import com.game.javex.tools.Utils;

public class PlayScene extends Scene {
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private EntityManager entityManager;
	private PlayerControlManager playerControlManager;
	private AiControlManager aiControlManager;
	private CollisionManager collisionManager;
	
	AiControlManager entity, target;
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
		
		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();
		
		entityManager = new EntityManager(world);
		initialize();
		
		playerControlManager = new PlayerControlManager(entityManager.getPlayer(), inputManager);
//		aiControlManager = new AiControlManager(entityManager.getEnemies());	
		
		collisionManager = new CollisionManager();
		world.setContactListener(collisionManager);
		Body playerBody = entityManager.createPlayer(new Vector2(800, 500), 32, 64);
		target = new AiControlManager(playerBody, 0);
		//entityManager.createPlayer(new Vector2(800, 500), 64, 32);
		
		//target = new AiControlManager(body, 30);
		
		//body = entityManager.createEnemy(new Vector2(600, 300), 32, 32);
		Body enemyBody = entityManager.createEnemy(new Vector2(600, 300), 32, 32);
		entity = new AiControlManager(enemyBody, 0);
		//entityManager.createEnemy(new Vector2(800, 300), 64, 32);
		//target = new AiControlManager(enemyBody, 30);
		
		
		entityManager.createCoin(new Vector2(500, 100 + 16), 16);
		entityManager.createCoin(new Vector2(700, 100 + 16), 16);
		
		Arrive<Vector2> arriveSB = new Arrive<Vector2>(entity, target)
				.setTimeToTarget(0.01f)
				.setArrivalTolerance(2f)
				.setDecelerationRadius(10);
		entity.setBehavior(arriveSB);
	}
	
	@Override
	protected void update(float dt) {
		world.step(1 /60f, 6, 2);
		
		cameraUpdate(dt);
		pauseListener(dt);
		playerControlManager.update(dt);
		entityManager.update(dt);
		entity.update(dt);
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
		
		entityManager.createBoss(new Vector2(576, 32), 64, 64);
		entityManager.createEnemy(new Vector2(192, 32), 32, 32);
		entityManager.createEnemy(new Vector2(384, 32), 32, 32);
		
		entityManager.createCoin(new Vector2(256, 32), 32, 32);
		entityManager.createCoin(new Vector2(448, 32), 32, 32);
		
		entityManager.createTerrain(new Vector2(0, 0), 736, 32);
		entityManager.createTerrain(new Vector2(0, 32), 32, 32);
		entityManager.createTerrain(new Vector2(128, 32), 32, 32);
		entityManager.createTerrain(new Vector2(320, 32), 32, 32);
		entityManager.createTerrain(new Vector2(512, 32), 32, 32);
		entityManager.createTerrain(new Vector2(704, 32), 32, 32);

		entityManager.createTerrain(new Vector2(400 , 100), 1600, 32);
		entityManager.createTerrain(new Vector2(600 , 100), 1600, 32);
		entityManager.createTerrain(new Vector2(500,100),16,320);
		entityManager.createTerrain(new Vector2(700,100),16,320);
	}

	protected void pauseListener(float dt) {
		int currKey = inputManager.getCurrKey();
	    if (currKey == Keys.ESCAPE) {
	        Gdx.app.log("PlayScene", "ESC key pressed. Pausing the game.");
	        sceneManager.push(new PauseScene(sceneManager, inputManager, outputManager));
	    }
	}
	
	public void cameraUpdate(float dt) {
		Vector3 position = camera.position;
		position.x = entityManager.getPlayer().getBody().getPosition().x *Constants.PPM;
		position.y = entityManager.getPlayer().getBody().getPosition().y *Constants.PPM;
		camera.position.set(position);
		camera.update();
		
		System.out.println("Player's x position: " + entityManager.getPlayer().getBody().getPosition().x);
	}
}
