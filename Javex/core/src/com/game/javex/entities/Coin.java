package com.game.javex.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Coin extends Entity {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private boolean collected = false;
    private Texture[] coinTextures = new Texture[5];
    
    public Coin(World world, Vector2 position) {
        super(world, position);
        this.width = Constants.COIN_WIDTH;
        this.height = Constants.COIN_HEIGHT;
        
        TextureRegion[] coinFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            coinTextures[i] = new Texture(Gdx.files.internal("C" + (i + 1) + ".png"));
            coinFrames[i] = new TextureRegion(coinTextures[i]);
        }
        animation = new Animation<>(0.1f, coinFrames); // Assuming you want to switch frames every 0.25 seconds
        stateTime = 0f;
        createBody(); 
    }
	
	protected void createBody() {
		BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.StaticBody;
	    bodyDef.position.set((position.x + width / 2) / Constants.PPM, (position.y + height / 2) / Constants.PPM);

	    this.body = world.createBody(bodyDef);

	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
    	
//		fixtureDef for the body
		fixtureDef.filter.categoryBits = Constants.COIN_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		resource management
	    shape.dispose();	
    }
	
    @Override
    public void update(float delta) {
    	super.update(delta);
        stateTime += delta;
        
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (!collected && body != null) {
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            spriteBatch.draw(currentFrame, body.getPosition().x * Constants.PPM - (width / 2),
                    body.getPosition().y * Constants.PPM - (height / 2), width, height);
        }
    }
    
	public void collect() {
        collected = true;
    }
	
	public boolean isCollected() {
		return collected;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	    for (Texture tex : coinTextures) {
	        tex.dispose();
	    }
	}
	
}