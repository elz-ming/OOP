package com.game.javex.entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public abstract class Entity {
	protected Body body;
	protected Vector2 position;
	protected World world;
	protected Sprite sprite;
	
	protected int width;
	protected int height;
	protected String imgPath;
	
	protected Animation<TextureRegion> animation;
	protected float stateTime = 0;
	protected boolean isAnimated = false;

	public Entity(World world, Vector2 position) {
		this.world = world;
		this.position = position;
	}
	
	protected void initAnimation(String spriteSheetPath, int frameCols, int frameRows, float frameDuration) {
	    Texture spriteSheet = new Texture(Gdx.files.internal(spriteSheetPath)); // Load the sprite sheet as a Texture
	    int frameWidth = spriteSheet.getWidth() / frameCols;
	    int frameHeight = spriteSheet.getHeight() / frameRows;

	    // Split the sprite sheet into individual frames
	    TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

	    // Flatten the array
	    TextureRegion[] animationFrames = new TextureRegion[frameCols * frameRows];
	    int index = 0;
	    for (int i = 0; i < frameRows; i++) {
	        for (int j = 0; j < frameCols; j++) {
	            animationFrames[index++] = tmpFrames[i][j];
	        }
	    }

	    // Create the animation
	    animation = new Animation<>(frameDuration, animationFrames);
	    isAnimated = true;

	    // Set entity size based on frame size if not set
	    if (width == 0 && height == 0) {
	        width = frameWidth;
	        height = frameHeight;
	    }
	}
	
	protected void createSprite() {
        position = body.getPosition();
        this.sprite = new Sprite(new Texture(Gdx.files.internal(imgPath)));
        this.sprite.setSize(width, height);
        this.sprite.setPosition(position.x *Constants.PPM - width /2, position.y *Constants.PPM - height/2);
	}

    protected void createAnimatedSprite(Texture[] textures, int currentTextureIndex) {
        position = body.getPosition();
        this.sprite = new Sprite(textures[currentTextureIndex]);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(position.x * Constants.PPM - width / 2, position.y * Constants.PPM - height / 2);
    }
	
	public void update(float delta) {
	    stateTime += delta; // Update animation state time
	    position = body.getPosition(); // Update position based on the physics body

	    
	    if (this.sprite != null) {
	        this.sprite.setPosition(position.x * Constants.PPM - width / 2, position.y * Constants.PPM - height / 2);
	    }
	}
	
	public void render(SpriteBatch spriteBatch) {
		if (isAnimated) {
	        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
	        spriteBatch.draw(currentFrame, position.x * Constants.PPM - width / 2, position.y * Constants.PPM - height / 2, width, height);
	    } else if (sprite != null) {
	        sprite.draw(spriteBatch);
	    }
	}
	
	public void dispose() {
		sprite.getTexture().dispose();
	}
	
	public Body getBody() {
		return body;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	protected abstract void createBody();
}