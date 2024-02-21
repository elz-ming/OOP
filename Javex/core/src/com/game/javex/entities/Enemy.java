package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public class Enemy extends Entity {
	private boolean isBoss;
	private int health;
	private boolean killed = false;
	
	public Enemy(World world, Vector2 position, boolean isBoss) {
		super(world, position);
		this.isBoss = isBoss;
		if (isBoss) {
			this.health = 3;
			this.width = Constants.BOSS_WIDTH;
			this.height = Constants.BOSS_HEIGHT;
			this.imgPath = Constants.BOSS_IMG_PATH;
			
		} else {
			this.health = 1;
			this.width = Constants.ENEMY_WIDTH;
			this.height = Constants.ENEMY_HEIGHT;
			this.imgPath = Constants.ENEMY_IMG_PATH;
		}
		
		createBody();
		this.body.setLinearVelocity(1, 0);
		createSprite();
	}
	
	@Override
	protected void createBody() {		
//		initialize bodyDef and fixtureDef
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		PolygonShape head = new PolygonShape();
		
//		bodyDef for the entire body
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
		bodyDef.fixedRotation = true;
		this.body = world.createBody(bodyDef);
		
//		fixtureDef for the body
		shape.setAsBox(width /2 /Constants.PPM, height /2 /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.0f;
		fixtureDef.filter.categoryBits = Constants.ENEMY_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.TERRAIN_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		fixtureDef for the head for jumping on enemies
		head.setAsBox(width /2 /2 /Constants.PPM, 4 /Constants.PPM, new Vector2(0, height/2 /Constants.PPM), 0);
		fixtureDef.shape = head;
		fixtureDef.restitution = 1.0f;
		fixtureDef.filter.categoryBits = Constants.ENEMY_HEAD_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		resource management
		shape.dispose();
		head.dispose();
	}
	
	@Override
	public void update(float dt) {
		if (!isBoss) {
			float veloX = body.getLinearVelocity().x;
			
			if (veloX > -0.5 && veloX < 0.5) {
				this.body.setLinearVelocity((veloX*1.1f), 0);
			} else if (veloX == 0) {
				this.body.setLinearVelocity(1, 0);
			}
		}
		
		Vector2 position = body.getPosition();
		sprite.setPosition(position.x *Constants.PPM - width /2, position.y *Constants.PPM - height/2);
	}
	
	public void reduceHealth() {
		health -= 1;
	}

	public void hitOnHead() {
		health -= 1;
		if (health <= 0) {
			killed = true;
		}
	}
	
	public boolean getKilled() {
		return killed;
	}
	
	public boolean getIsBoss() {
		return isBoss;
	}
	
	public void reverseVelocity() {
		Vector2 currentVelocity = body.getLinearVelocity();
        body.setLinearVelocity(-currentVelocity.x, currentVelocity.y);
	}
}
