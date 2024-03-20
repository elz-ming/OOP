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

public class Flag extends Entity{
	private Animation<TextureRegion> animation;
	private float stateTime;
	
	public Flag(World world, Vector2 position, int width, int height) {
        super(world, position);
        this.width = width; 
        this.height = height;

        TextureRegion[] flagFrames = new TextureRegion[4]; // Assuming you have 4 frames for the flag animation
        for (int i = 0; i < 4; i++) { // Change the loop condition from 1 to 0
            flagFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("flag_" + (i + 1) + ".png"))); // Correct the index
        }
        animation = new Animation<>(0.25f, flagFrames); // Adjust frame duration as needed
        stateTime = 0f;

        createBody();
    }

	@Override
    protected void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((position.x + width / 2) / Constants.PPM, (position.y + height / 2) / Constants.PPM);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Constants.FLAG_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        this.body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }
	
	@Override
    public void update(float delta) {
		super.update(delta);
        stateTime += delta;
    }

	@Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch); // Render the sprite
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        if (currentFrame != null) {
            spriteBatch.draw(currentFrame, body.getPosition().x * Constants.PPM - (width / 2),
                    body.getPosition().y * Constants.PPM - (height / 2), width, height);
        }
}
}
