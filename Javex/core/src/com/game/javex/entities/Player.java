package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public class Player extends Entity{
	private boolean canJump;
	private boolean killed = false;
	private boolean won = false;
	private boolean reading = false;
	private boolean solving = false;
	private boolean resetSolving = true;
	private boolean engagedWithChest = false;
	
	public Player(World world, Vector2 position) {
		super(world, position);
		this.width = Constants.PLAYER_WIDTH;
		this.height = Constants.PLAYER_HEIGHT;
		this.imgPath = Constants.PLAYER_IMG_PATH;
				
		createBody();
		//createSprite();
		initAnimation("MC.png", 2, 1, 0.3f); // Adjust the parameters as needed
	}
	
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
		fixtureDef.density = 2.0f;
		fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
		fixtureDef.filter.maskBits = Constants.ENEMY_BIT | Constants.ENEMY_HEAD_BIT | Constants.TERRAIN_BIT | Constants.TERRAIN_TOP_BIT | Constants.BOUNDARY_BIT | Constants.BOUNDARY_TOP_BIT | Constants.COIN_BIT | Constants.TREASURE_CHEST_BIT | Constants.SIGNBOARD_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
	
//		resource management
		shape.dispose();
	}
	
	public void moveLeft(float delta) {
		body.setLinearDamping(0f);
		if (body.getLinearVelocity().x >= -2) {
			body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);
		}
	}
	
	public void moveRight(float delta) {
		body.setLinearDamping(0f);
		if (body.getLinearVelocity().x <= 2) {
			body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
		}
	}
	
	public void slowDown(float delta) {
		body.setLinearDamping(1.0f);
	}
	
	public void jump(float delta) {
		body.setLinearDamping(2f);
        body.applyLinearImpulse(new Vector2(0, 1.2f), body.getWorldCenter(), true); // Adjust impulse as needed
        canJump = false; // Reset jump ability until player touches the ground again
    }
	
	public void setKilled() {
		killed = true;
	}
	
	public boolean getKilled() {
		return killed;
	}
	
	public void setWon() {
		won = true;
	}
	
	public boolean getWon() {
		return won;
	}
	
	public void setReading(boolean reading) {
		this.reading = reading;
	}
	
	public boolean getReading() {
		return reading;
	}
	
	public void setSolving(boolean solving) {
		this.solving = solving;
	}
	
	public boolean getSolving() {
		return solving;
	}
	
	public void setResetSolving(boolean resetSolving) {
		this.resetSolving = resetSolving;
	}
	
	public boolean getResetSolving() {
		return resetSolving;
	}
	
	
	 public void setEngagedWithChest(boolean engaged) {
	        engagedWithChest = engaged;
	    }

	    public boolean isEngagedWithChest() {
	        return engagedWithChest;
	    }

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
    }
	
	public boolean getCanJump() {
		return canJump;
    }
}
