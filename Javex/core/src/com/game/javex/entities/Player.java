package com.game.javex.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public class Player extends Entity{
	private int health;
	private boolean canJump;
	
	public Player(World world, Vector2 position) {
		super(world, position);
		this.health = 3;
		this.width = Constants.PLAYER_WIDTH;
		this.height = Constants.PLAYER_HEIGHT;
		this.imgPath = Constants.PLAYER_IMG_PATH;
		System.out.println(imgPath);
				
		createBody();
		createSprite();
	}
	
	@Override
	protected void createBody() {
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
		fixtureDef.filter.maskBits = Constants.ENEMY_BIT | Constants.ENEMY_HEAD_BIT | Constants.TERRAIN_BIT | Constants.REWARD_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		resource management
		shape.dispose();
	}
	
	public void reduceHealth() {
		health -= 1;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void moveLeft() {
		body.setLinearVelocity(-2, body.getLinearVelocity().y);
	}
	
	public void moveRight() {
		body.setLinearVelocity(2, body.getLinearVelocity().y);
	}
	
	public void jump() {
        if (canJump) {
            body.applyLinearImpulse(new Vector2(0, 1.5f), body.getWorldCenter(), true); // Adjust impulse as needed
            canJump = false; // Reset jump ability until player touches the ground again
        }
    }
	
	public void hit(Enemy enemy) {
		health -= 1;
	}

	public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
	
	public boolean getCanJump() {
		return this.canJump;
	}
}
