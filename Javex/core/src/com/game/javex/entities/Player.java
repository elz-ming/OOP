package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.game.javex.tools.Constants;

public class Player{
	private World world;
	private Body body;
	private int health;
	
	public Player(World world) {
		this.world = world;
		defineEntity();
		this.health = 3;
	}
	
	protected void defineEntity() {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.position.set((Constants.TILE_WIDTH /2) /Constants.PPM, (Constants.TILE_HEIGHT /2) /Constants.PPM);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		this.body = world.createBody(bodyDef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(Constants.PLAYER_RADIUS /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
		fixtureDef.filter.maskBits = Constants.ENEMY_BIT | Constants.ENEMY_HEAD_BIT | Constants.REWARD_BIT | Constants.TERRAIN_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2 /Constants.PPM, Constants.PLAYER_RADIUS /Constants.PPM), new Vector2(2 /Constants.PPM, Constants.PLAYER_RADIUS / Constants.PPM));
		fixtureDef.shape = head;
		fixtureDef.filter.categoryBits = Constants.PLAYER_HEAD_BIT;
		fixtureDef.isSensor = true;
		this.body.createFixture(fixtureDef).setUserData(this);
		
		shape.dispose();
		head.dispose();
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
}
