package com.game.javex.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.game.javex.tools.Utils;

public class Object {
    private boolean isCoin;
    private boolean isTerrain;
    private Body body;

    public Object(World world, Vector2 position, int width, int height, boolean isCoin, boolean isTerrain) {
    	this.isCoin = isCoin;
        this.isTerrain = isTerrain;
        this.body = createBox(world, position, width, height);
    }

    private Body createBox(World world, Vector2 position, int width, int height) {
    	Body pBody;
    	BodyDef bodyDef = new BodyDef();
    	FixtureDef fixtureDef = new FixtureDef();
    	
    	bodyDef.type = BodyDef.BodyType.StaticBody;
    	bodyDef.position.set((position.x + width /2) /Utils.PPM, (position.y + height /2) /Utils.PPM);
    	pBody = world.createBody(bodyDef);
    	
    	
    	
		
    	if (isCoin) {
    		CircleShape shape = new CircleShape();
    		shape.setRadius(width /2 /Utils.PPM);
    		fixtureDef.density = 0.0f;
    		fixtureDef.shape = shape;
        	pBody.createFixture(fixtureDef);
        	shape.dispose();
    	}
    	
    	if (isTerrain) {
    		PolygonShape shape = new PolygonShape();
    		shape.setAsBox(width /2 /Utils.PPM, height /2 /Utils.PPM);
    		fixtureDef.density = 1.0f;
    		fixtureDef.shape = shape;
        	pBody.createFixture(fixtureDef);
        	shape.dispose();
    	}	
    	
    	return pBody;
    }
    
    public void update(float delta) {
        // Static objects may not need to update, but method is here for consistency
    }
}