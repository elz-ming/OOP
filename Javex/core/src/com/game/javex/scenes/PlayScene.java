package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.game.javex.entities.enemies.Enemy;
import com.game.javex.entities.EntityManager;

public class PlayScene extends AbstractScene {
	
	// For Box2D world
    private World world;
    private Box2DDebugRenderer b2dr;
    
    // For OrthographicCamera
    private OrthographicCamera camera;
    
    // For Enemy
    private Enemy enemy[];

<<<<<<< Updated upstream
    protected PlayScene(SceneManager sceneManager) {
        super(sceneManager);
        world = new World(new Vector2(0, -10f), false); // Create Box2D world
        b2dr = new Box2DDebugRenderer(); // Create Box2DDebugRenderer
=======
		width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	
    	switch (selectedWorld) {
        case "Earth":
            gravity = new Vector2(0, -15f);
            audioPath = Constants.EARTH_AUDIO_PATH;
            backgroundImagePath = Constants.EARTH_IMG_PATH;
            cameraZoomValue = 0.4f;
            countdownTimer = 30000;
            break;
            
        case "Mars":
            gravity = new Vector2(0, -10f);
            audioPath = Constants.MARS_AUDIO_PATH;
            backgroundImagePath = Constants.MARS_IMG_PATH;
            cameraZoomValue = 0.5f;
            countdownTimer = 30;
            break;
            
        case "Venus":
            gravity = new Vector2(0, -4.5f);
            audioPath = Constants.VENUS_AUDIO_PATH;
            backgroundImagePath = Constants.VENUS_IMG_PATH;
            cameraZoomValue = 0.6f;
            countdownTimer = 15;
            break;
            
        default:
            gravity = new Vector2(0, -10f); // Default to Earth's gravity
            audioPath = Constants.EARTH_AUDIO_PATH;
            backgroundImagePath = Constants.EARTH_IMG_PATH;
            countdownTimer = 45;
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
>>>>>>> Stashed changes
        
        // Set up OrthographicCamera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        
        enemy = new Enemy[10];

        for (int i = 0; i < 10; i++) {
            float startX = MathUtils.random(0, Gdx.graphics.getWidth());
            float startY = MathUtils.random(0, Gdx.graphics.getHeight());
            enemy[i] = new Enemy();
            enemy[i].create(world);
        }
        
  // Pass the world instance to Enemy's create() method
	}
    
    

<<<<<<< Updated upstream
=======
	    cameraUpdate();
	    playerControlManager.update(dt);
//	    aiControlManager.update(dt);
	    entityManager.update(dt);
	    hudManager.update(entityManager.getEnemiesKilled(), entityManager.getCoinsCollected());
		
	    if (entityManager.getTotalEnemies() == 0 && entityManager.getTotalCoins() == 0) { // end logic to be improved in the future
	        sceneManager.set(new EndScene(sceneManager, inputManager, outputManager));
	    }
	    if (hudManager.getCountdownTimer() <= 0) {
	        sceneManager.set(new EndScene(sceneManager, inputManager, outputManager));
	    }
	}
	
>>>>>>> Stashed changes
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		// Update the game logic
        update(delta);

        // Clear the screen
        Gdx.gl.glClearColor(207, 178, 235, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the camera projection matrix
        camera.update();
        
        // Render the Box2D world using the camera's projection matrix
        b2dr.render(world, camera.combined);
        
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleInput(float dt) {
	    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	        // Pause the game
	        sceneManager.push(new PauseScene(sceneManager));
	    }
	}

	@Override
	protected void update(float dt) {
		handleInput(dt);

        // Update the Box2D world
        world.step(1/60f, 6, 2);

        // Update the enemy
        for(Enemy e : enemy) {
        	e.update(dt);
        }

	}

	@Override
	protected void render(SpriteBatch sb) {
		Gdx.gl.glClearColor(207, 178, 235, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b2dr.render(world, camera.combined);
        camera.update();
        
        

        
	}

	@Override
	public void dispose() {
		world.dispose();
        b2dr.dispose();
	}

}
