package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import com.game.javex.tools.Constants;

public class Reward {
	private Body body;
	
	public Reward(World world, Vector2 position, int width, int height) {
		super(world, position, width, height);
    }
	
	protected Body createBox(World world, Vector2 position, int width, int height) {
    	Body pBody;
    	BodyDef bodyDef = new BodyDef();
    	FixtureDef fixtureDef = new FixtureDef();
    	
    	bodyDef.type = BodyDef.BodyType.StaticBody;
    	bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
    	pBody = world.createBody(bodyDef);
    	
		CircleShape shape = new CircleShape();
		shape.setRadius(width /2 /Constants.PPM);
		fixtureDef.density = 0.0f;
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constants.REWARD_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
    	pBody.createFixture(fixtureDef);
    	shape.dispose();	
    	
    	return pBody;
    }
	
	public void update(float delta) {
        // Static objects may not need to update, but method is here for consistency
    }
	
	public boolean isCollected() {
		return false;
	}
}
