package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.game.javex.tools.Constants;

public class Reward extends Entity{
	private boolean collected = false;

	public Reward(World world, Vector2 position) {
		super(world, position);
		
		boolean collected = false;
		
		createBody(Constants.COIN_WIDTH, Constants.COIN_HEIGHT);
    }
	
	protected void createBody(int width, int height) {
//		initialize bodyDef and fixtureDef
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
    	
//		bodyDef for the entire body
    	bodyDef.type = BodyDef.BodyType.StaticBody;
    	bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
    	bodyDef.fixedRotation = true;
    	this.body = world.createBody(bodyDef);
    	
//		fixtureDef for the body
    	shape.setAsBox(width /2 /Constants.PPM, height /2 /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constants.REWARD_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		resource management
    	shape.dispose();	
    }
	
	public void update(float delta) {
        // Static objects may not need to update, but method is here for consistency
    }
	
	public void collect() {
        collected = true;
    }
	
	public boolean isCollected() {
		return collected;
	}
}