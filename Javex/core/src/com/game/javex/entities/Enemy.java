package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.game.javex.tools.Utils;

public class Enemy {
	private Body body;
	
	public Enemy(World world, Vector2 position, int width, int height) {
		this.body = createBox(world, position, width, height);
	}
	
	private Body createBox(World world, Vector2 position, int width, int height) {
		Body pBody;
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(position.x /Utils.PPM, position.y /Utils.PPM);
		bodyDef.fixedRotation = true;
		pBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width /2 /Utils.PPM, height /2 /Utils.PPM);
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		pBody.createFixture(fixtureDef);
		
		shape.dispose();
		return pBody;
	}
	
	public void update(float dt) {
		
	}
}
