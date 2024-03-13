package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.entities.Enemy;
import com.game.javex.entities.EntityManager;
import com.game.javex.entities.Player;
import com.game.javex.inouts.*;
import com.game.javex.tools.AiControlManager;
import com.game.javex.tools.CollisionManager;
import com.game.javex.tools.HUD;
import com.game.javex.tools.PlayerControlManager;

public class PlayScene extends Scene {
	private OrthographicCamera camera;
	private World world;
	private SpriteBatch spriteBatch;
//	//	For debug purposes
//	private Box2DDebugRenderer b2dr;
	
	private EntityManager entityManager;
	private PlayerControlManager playerControlManager;
	private AiControlManager aiControlManager;
	private CollisionManager collisionManager;
	private HUD hudManager;
	
	public PlayScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager, String selectedWorld) {
		// Using universal attribute across all scenes
		super(sceneManager, inputManager, outputManager);

		outputManager.play("audio/earth.mp3", true);
		width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	
    	// Set background
    	backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.PLAY_IMG_PATH)));
    	backgroundImage.setSize(width, height); // Set the size to fill the screen
    	backgroundImage.setZIndex(0); // Make sure the background is drawn first (before the buttons)
    	
    	//Initialize HUD
    	hudManager = new HUD();
    	
    	// Add buttons to stage
        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        stage.addActor(hudManager.getTable());
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		
		Vector2 gravity;
	    switch (selectedWorld) {
	        case "Earth":
	            gravity = new Vector2(0, -9.8f);
	            break;
	        case "Mars":
	            gravity = new Vector2(0, -3.7f);
	            break;
	        case "Venus":
	            gravity = new Vector2(0, -1.9f);
	            break;
	        default:
	            gravity = new Vector2(0, -9.8f); // Default to Earth's gravity
	            break;
	    }
	    world = new World(gravity, false);
	    
		spriteBatch = new SpriteBatch();
		
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

//	//	For debug purposes
//		b2dr = new Box2DDebugRenderer();
	}
	
	@Override
	public void update(float dt) {
		handleInput();
		world.step(1 / 60f, 6, 2);

	    cameraUpdate();
	    playerControlManager.update(dt);
	    aiControlManager.update(dt);
	    entityManager.update(dt);
	    hudManager.update(entityManager.getEnemiesKilled(), entityManager.getCoinsCollected());
		
	    if (entityManager.getTotalEnemies() == 0 && entityManager.getTotalCoins() == 0) { // end logic to be improved in the future
	        sceneManager.set(new EndScene(sceneManager, inputManager, outputManager));
	    }
	}
	
	@Override
	public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
		
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
			entityManager.render(spriteBatch);
		spriteBatch.end();
		
//	//	For debug purposes
//		if (b2dr != null && world != null && camera != null) {
//			b2dr.render(world, camera.combined.scl(Constants.PPM));
//		}
	}
	
	@Override
	protected void handleInput() {
	    if (inputManager.isReturnPressed()) {
	        sceneManager.push(new PauseScene(sceneManager, inputManager, outputManager));
	        try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
	    } 
	}
	
	
	
	@Override
	public void dispose() {
		if (world != null) {
	        world.dispose();
	    }
		
		if (stage != null) {
	        stage.dispose();
	    }
		
	    if (spriteBatch != null) {
	    	spriteBatch.dispose();
	    }
		
	    camera = null;
	    
//		if (b2dr != null) {
//	        b2dr.dispose();
//	    }
	}

	private void initialize() {
		entityManager.initialize();
	}
	
	
	@Override
	public void resize(int width, int height) {
	    // Update the stage's viewport to the new resolution
	    stage.getViewport().update(width, height, true);
	    
	    // Update the camera's viewport
	    camera.setToOrtho(false, width, height);
	    
	    // Update the position and size of elements based on the new resolution
	    this.width = width;
	    this.height = height;
	    
	    // Update the background image size
	    backgroundImage.setSize(width, height);
	    
	    // Update HUD
	    hudManager.resize(width, height);
	}


	
	private void cameraUpdate() {
		Vector3 position = camera.position;
		position.x = entityManager.getPlayer().getBody().getPosition().x *Constants.PPM;
		position.y = entityManager.getPlayer().getBody().getPosition().y *Constants.PPM;
		camera.position.set(position);
		camera.update();
	}
}
