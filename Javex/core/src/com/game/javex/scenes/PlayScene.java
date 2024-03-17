package com.game.javex.scenes;

import java.security.KeyStore.LoadStoreParameter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.javex.Constants;
//import com.game.javex.entities.Enemy;
import com.game.javex.entities.EntityManager;
import com.game.javex.entities.Player;
import com.game.javex.entities.Signboard;
import com.game.javex.entities.TreasureChest;
import com.game.javex.inouts.*;
//import com.game.javex.tools.AiControlManager;
import com.game.javex.tools.CollisionManager;
import com.game.javex.tools.HUD;
import com.game.javex.tools.PlayerControlManager;

public class PlayScene extends Scene {
	private OrthographicCamera camera;
	private Viewport port;
	private World world;
	private SpriteBatch spriteBatch;
	
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//	For debug purposes
	private Box2DDebugRenderer b2dr;
	
//	Managers to help structure the game
	private EntityManager entityManager;
	private PlayerControlManager playerControlManager;
//	private AiControlManager aiControlManager;
	private CollisionManager collisionManager;
	private HUD hudManager;
	
	private Stage frontStage;
	private Vector2 gravity;
	private String audioPath;
	private String backgroundImagePath;
	private String mapString;
	private float cameraZoomValue;
	private int countdownTimer;
	private Label signboardText;
	private Image signboardBackground;
	
	private Player player;
	
	public PlayScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager, String selectedWorld) {
		// Using universal attribute across all scenes
		super(sceneManager, inputManager, outputManager);

		width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	
    	switch (selectedWorld) {
        case "Earth":
            gravity = new Vector2(0, -12f);
            audioPath = Constants.EARTH_AUDIO_PATH;
            backgroundImagePath = Constants.EARTH_IMG_PATH;
            cameraZoomValue = 0.4f;
            mapString = Constants.EARTH_MAP_PATH;
            break;
            
        case "Mars":
            gravity = new Vector2(0, -9f);
            audioPath = Constants.MARS_AUDIO_PATH;
            backgroundImagePath = Constants.MARS_IMG_PATH;
            cameraZoomValue = 0.6f;
            mapString = Constants.MARS_MAP_PATH;
            break;
            
        case "Venus":
            gravity = new Vector2(0, -6f);
            audioPath = Constants.VENUS_AUDIO_PATH;
            backgroundImagePath = Constants.VENUS_IMG_PATH;
            cameraZoomValue = 0.8f;
            mapString = Constants.VENUS_MAP_PATH;
            break;
            
        default:
            gravity = new Vector2(0, -10f); // Default to Earth's gravity
            audioPath = Constants.EARTH_AUDIO_PATH;
            backgroundImagePath = Constants.EARTH_IMG_PATH;
            cameraZoomValue = 0.4f;
            mapString = Constants.EARTH_MAP_PATH;
            break;
    	}
    	
    	
    	// Play music
    	outputManager.play(audioPath, true);
    	
    	// Set background
    	backgroundImage = new Image(new Texture(Gdx.files.internal(backgroundImagePath)));
    	backgroundImage.setSize(width, height); // Set the size to fill the screen
    	backgroundImage.setZIndex(0); // Make sure the background is drawn first (before the buttons)
    	
    	//Initialize HUD
    	hudManager = new HUD(countdownTimer);
    	
    	// Add buttons to stage
        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); // Add the background image to the stage
        
        frontStage = new Stage(new ScreenViewport());
        frontStage.addActor(hudManager.getTable());
        
		camera = new OrthographicCamera();
		port = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load(mapString);
		renderer = new OrthogonalTiledMapRenderer(map);
		
		camera.position.set(Constants.VIEWPORT_WIDTH /2, Constants.VIEWPORT_HEIGHT /2, 0);
		camera.zoom = cameraZoomValue;
		camera.update();
		world = new World(gravity, true);
	    
		spriteBatch = new SpriteBatch();
		
//		Initialize entityManager and create relevant entities in the game world
		entityManager = new EntityManager(world, map, selectedWorld, inputManager);
		initialize();
		player = entityManager.getPlayer();
//		Enemy boss = entityManager.getBoss();
		
//		Initialize playerControlManager and link the control to the main player
		playerControlManager = new PlayerControlManager(player, inputManager);
		
//		Initialize aiControlManager and link the control to the boss
//		aiControlManager = new AiControlManager(boss, player);
		
//		Initialize collisionManager to listen for collisions in the game world
		collisionManager = new CollisionManager();
		world.setContactListener(collisionManager);

//		For debug purposes
		b2dr = new Box2DDebugRenderer();
		
		
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = new BitmapFont(); // Use your preferred font
		labelStyle.fontColor = Color.BLACK; // Set the font color to black
		signboardText = new Label("", labelStyle); //

		// Position the label higher on the screen
		float labelX = width / 2f;
		float labelY = height * 0.8f; // Adjust this value to move the label higher
		signboardText.setPosition(labelX, labelY);
		signboardText.setAlignment(1); // Center the text horizontally

		// Load the texture for the signboard background
		Texture signboardBackgroundTexture = new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH));
		signboardBackground = new Image(signboardBackgroundTexture);

		// Set the size and position of the background image based on the label size
		signboardBackground.setSize(signboardText.getPrefWidth() + 20, signboardText.getPrefHeight() + 20); // Add some padding
		signboardBackground.setPosition(labelX - signboardBackground.getWidth() / 2, labelY - signboardBackground.getHeight() / 2); // Center the background behind the label
		signboardBackground.setVisible(false); // Initially hide the background

		frontStage.addActor(signboardBackground);
		frontStage.addActor(signboardText); 
	}
	
	
	
	@Override
	public void update(float dt) {
	    handleInput();
	    world.step(1 / 60f, 6, 2);
	    
	    cameraUpdate();
	    playerControlManager.update(dt);
	    entityManager.update(dt);
	    hudManager.update(entityManager.getEnemiesKilled(), entityManager.getCoinsCollected());

//	    Lose Condition
	    long elapsedTime = hudManager.getElapsedTime();
	    if ((elapsedTime <= 0) || (player.getKilled())) {
	    	sceneManager.set(new EndScene(sceneManager, inputManager, outputManager));
	    }
	    
//	    Win Condition
	    if (player.getWon()) {
	    	sceneManager.set(new EndScene(sceneManager, inputManager, outputManager));
	    }

	    Boolean signBoardShow = false;
	    for (Signboard signboard:entityManager.getSignboards()) {
	    	if (signboard.getVisible()) {
	    		signboardText.setText(signboard.getContent());
				signboardBackground.setSize(signboardText.getPrefWidth() + 20, signboardText.getPrefHeight() + 20);
		        signboardBackground.setPosition(signboardText.getX() - (signboardBackground.getWidth() - signboardText.getWidth()) / 2, 
		                                        signboardText.getY() - (signboardBackground.getHeight() - signboardText.getHeight()) / 2);
		        signBoardShow = true;
		        break;
	    	}  		
	    }
	    
	    if (signBoardShow) {
	        signboardText.setVisible(true);
	        signboardBackground.setVisible(true);
	    } else {
	    	signboardText.setVisible(false);
	        signboardBackground.setVisible(false);
	    }
	    
	    for (TreasureChest treasureChest:entityManager.getTreasureChests()) {
	    	if (treasureChest.getSolving() && treasureChest.getResetSolving()) {
	    		treasureChest.setResetSolving(false);
	    		sceneManager.push(new QuizScene(sceneManager, inputManager, outputManager, treasureChest));
		        break;
	    	}  		
	    }
	}
	@Override
	public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.draw();
        renderer.render();
		
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
			entityManager.render(spriteBatch);
		spriteBatch.end();
		
		frontStage.draw();
		
		
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
	    
		if (b2dr != null) {
	        b2dr.dispose();
	    }
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
		float playerX = entityManager.getPlayer().getBody().getPosition().x *Constants.PPM;
		float minX = (Constants.VIEWPORT_WIDTH +32) *cameraZoomValue /2;
		float maxX = Constants.WORLD_WIDTH - Constants.VIEWPORT_WIDTH *cameraZoomValue /2;
		float cameraX = MathUtils.clamp(playerX, minX, maxX);
		
		float playerY = entityManager.getPlayer().getBody().getPosition().y *Constants.PPM;
		float minY = Constants.VIEWPORT_HEIGHT *cameraZoomValue /2;
		float maxY = Constants.WORLD_HEIGHT - Constants.VIEWPORT_HEIGHT *cameraZoomValue /2;
		float cameraY = MathUtils.clamp(playerY, minY, maxY);
	
		camera.position.set(cameraX, cameraY, 0);
		camera.zoom = cameraZoomValue;
		camera.update();
		renderer.setView(camera);
	}
}
