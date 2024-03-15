package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;
import com.game.javex.inouts.InputManager;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity{
	private int health;
	private boolean canJump;
	
	// Store textures for different animations
	private List<Texture> moveRightTextures = new ArrayList<>();
    private List<Texture> moveLeftTextures = new ArrayList<>();
    private Texture jumpTexture;
    
    // Reference to InputManager
    private InputManager inputManager;
    
 // Animation state
    private int currentFrameIndex = 0;
    
    
    
    private float animationTimer = 0;
    private final float FRAME_DURATION = 0.1f; // Adjust this value as needed
	
    public Player(World world, Vector2 position, InputManager inputManager) {
		super(world, position);
		this.inputManager = inputManager;
		this.health = 3;
		this.width = Constants.PLAYER_WIDTH;
		this.height = Constants.PLAYER_HEIGHT;
		this.imgPath = Constants.PLAYER_IMG_PATH;
				
		createBody();

		createSprite();
		
		// Load images
        for (int i = 1; i <= 4; i++) {
            moveRightTextures.add(new Texture(Gdx.files.internal("R" + i + ".png")));
            moveLeftTextures.add(new Texture(Gdx.files.internal("L" + i + ".png")));
        }
        jumpTexture = new Texture(Gdx.files.internal("J.png"));
        
        // Initial sprite setup
        this.sprite = new Sprite(moveRightTextures.get(0)); // Start facing right
        this.sprite.setSize(width, height);

	}
    
    @Override
    public void update(float delta) {
        super.update(delta);
        handleInput(delta);
    }
    
    private void handleInput(float delta) {
        animationTimer += delta;
        
        if (inputManager.isUpPressed()) {
            sprite.setTexture(jumpTexture);
        } else if (inputManager.isRightPressed()) {
            updateAnimation(moveRightTextures, delta);
        } else if (inputManager.isLeftPressed()) {
            updateAnimation(moveLeftTextures, delta);
        }
        
        // Reset animation timer if needed
        if (animationTimer >= FRAME_DURATION) {
            animationTimer = 0;
        }
    }
    
    private void updateAnimation(List<Texture> textures, float delta) {
        if (animationTimer >= FRAME_DURATION) {
            currentFrameIndex = (currentFrameIndex + 1) % textures.size();
            sprite.setTexture(textures.get(currentFrameIndex));
        }
    }
    
    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }
	
	protected void createBody() {
//		initialize bodyDef and fixtureDef
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
//		bodyDef for the entire body
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
		bodyDef.fixedRotation = true;
		this.body = world.createBody(bodyDef);
		
//		fixtureDef for the body
		shape.setAsBox(width /2 /Constants.PPM, height /2 /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
		fixtureDef.filter.maskBits = Constants.ENEMY_BIT | Constants.ENEMY_HEAD_BIT | Constants.TERRAIN_BIT | Constants.REWARD_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		resource management
		shape.dispose();
	}
	
	public void reduceHealth() {
		health -= 1;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void moveLeft(float delta) {
		body.setLinearVelocity(-2, body.getLinearVelocity().y);
	}
	
	public void moveRight(float delta) {
		body.setLinearVelocity(2, body.getLinearVelocity().y);
	}
	
	public void jump(float delta) {
        if (canJump) {
            body.applyLinearImpulse(new Vector2(0, 1.0f), body.getWorldCenter(), true); // Adjust impulse as needed
            canJump = false; // Reset jump ability until player touches the ground again
        }
    }
	
	public void hit(Enemy enemy) {
		health -= 1;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
    }
}
