package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.game.javex.tools.Constants;

public class Player extends Entity{
	private int health;
	
	public Player(World world, Vector2 position, int width, int height) {
		this.body = createBox(world, position, width, height, BodyDef.BodyType.DynamicBody, false);
		
	}
	
	private Body createBox(World world, Vector2 position, int width, int height, BodyDef.BodyType bodyType, boolean isSensor) {
		Body pBody;
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
		bodyDef.fixedRotation = true;
		pBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width /2 /Constants.PPM, height /2 /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
//		fixtureDef.filter.maskBits = Constants.ENEMY_BIT | Constants.COIN_BIT | Constants.TERRAIN_BIT;
		pBody.createFixture(fixtureDef).setUserData(this);
		
		shape.dispose();
		return pBody;
	}
	
	public void update(float dt) {
		
	}
	
	public void reduceHealth() {
		health -= 1;
	}
	
	public int getHealth() {
		return health;
	}
	
//	TODO VARSHA
	public void moveLeft() {
		body.setLinearVelocity(-5, body.getLinearVelocity().y);
	}
	
//	TODO VARSHA
	public void moveRight() {
		body.setLinearVelocity(5, body.getLinearVelocity().y);
	}
	
//	TODO VARSHA
	public void jump() {
		body.applyForceToCenter(0, 900, true);
	}
	
//	TODO VARSHA
	public void duck() {
		
	}
	
	public void stop() {
		body.setLinearVelocity(0, body.getLinearVelocity().y);
	}
	
	public Body getBody() {
		return body;
	}
	
	public void update(float dt) {
	    
	  }
}
