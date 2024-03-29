package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public class Terrain extends Entity{
    public Terrain(World world, Vector2 position, int width, int height) {
    	super(world, position);
    	this.width = width;
    	this.height = height;
    	createBody();
    }
    
    @Override
	protected void createBody() {
//		initialize bodyDef and fixtureDef
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		EdgeShape top = new EdgeShape();
    	
//		bodyDef for the entire body
    	bodyDef.type = BodyDef.BodyType.StaticBody;
    	bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
    	bodyDef.fixedRotation = true;
    	this.body = world.createBody(bodyDef);
    	
//		fixtureDef for the body
		shape.setAsBox(width /2 /Constants.PPM, height /2 /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.density = 0;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0;
		fixtureDef.filter.categoryBits = Constants.TERRAIN_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT |Constants.ENEMY_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
		
//		fixtureDef for the bottom for jumping on terrain
		top.set(new Vector2((-width /2) /Constants.PPM, (height /2 +4) /Constants.PPM), 
				   new Vector2(((width) /2) /Constants.PPM, (height /2 +4) /Constants.PPM)
		);
		fixtureDef.shape = top;
		fixtureDef.filter.categoryBits = Constants.TERRAIN_TOP_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
		fixtureDef.restitution = 0;
		this.body.createFixture(fixtureDef).setUserData(this);
    	
//		resource management
    	shape.dispose();
    	top.dispose();
    }
}