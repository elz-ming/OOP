package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.game.javex.tools.Constants;

public class Enemy extends Entity {
	private boolean isBoss;
	private int health;
	private boolean toRemove = false;
	
	public Enemy(World world, Vector2 position, boolean isBoss) {
		super(world, position);
		this.isBoss = isBoss;
		if (isBoss) {
			this.health = 3;
			createBody(Constants.BOSS_WIDTH, Constants.BOSS_HEIGHT);
		} else {
			this.health = 1;
			createBody(Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
		}
	}
	
	@Override
	protected void createBody(int width, int height) {		
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
		fixtureDef.filter.categoryBits = Constants.ENEMY_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.TERRAIN_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		resource management
		shape.dispose();
	}
	
	public void update(float dt) {
	}
	
	public void reduceHealth() {
		health -= 1;
	}
	
	public Body getBody() {
		return body;
	}

	public void hitOnHead() {
		health -= 1;
		if (health <= 0) {
			toRemove = true;
		}
	}
	
	public boolean getToRemove() {
		return toRemove;
	}

	public void reverseVelocity() {
		Vector2 currentVelocity = body.getLinearVelocity();
        body.setLinearVelocity(-currentVelocity.x, currentVelocity.y);
	}
}
