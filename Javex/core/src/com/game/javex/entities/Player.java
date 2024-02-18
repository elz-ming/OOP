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
	private boolean canJump;
	
	public Player(World world, Vector2 position) {
		super(world, position);
		this.health = 3;
		createBody(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
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
		fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
		fixtureDef.filter.maskBits = Constants.ENEMY_BIT | Constants.TERRAIN_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		fixtureDef for the head for jumping on enemies
		shape.setAsBox((width /2 -2) /Constants.PPM, 4 /Constants.PPM, new Vector2(0, height/2 /Constants.PPM), 0);
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;
		fixtureDef.filter.categoryBits = Constants.PLAYER_HEAD_BIT;
		fixtureDef.filter.maskBits = Constants.ENEMY_BIT;
		this.body.createFixture(fixtureDef).setUserData("PlayerHead");
		
//		resource management
		shape.dispose();
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
		body.setLinearVelocity(-1, body.getLinearVelocity().y);
	}
	
//	TODO VARSHA
	public void moveRight() {
		body.setLinearVelocity(1, body.getLinearVelocity().y);
	}
	
//	TODO VARSHA
	public void jump() {
        if (canJump) {
            body.applyLinearImpulse(new Vector2(0, 1), body.getWorldCenter(), true); // Adjust impulse as needed
            canJump = false; // Reset jump ability until player touches the ground again
        }
    }
	
//	TODO VARSHA
	public void duck() {
		
	}
	
	public Body getBody() {
		return body;
	}

	public void hit(Enemy enemy) {
		// TODO Auto-generated method stub
		
	}

	public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
}
