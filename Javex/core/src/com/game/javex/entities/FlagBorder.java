package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public class FlagBorder extends Entity{

	public FlagBorder(World world, Vector2 position, int width, int height) {
		super(world, position);
    	this.width = width;
    	this.height = height;
    	this.imgPath = Constants.FLAG_BORDER_IMG_PATH;
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
    	bodyDef.type = BodyDef.BodyType.StaticBody;
    	bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
    	bodyDef.fixedRotation = true;
    	this.body = world.createBody(bodyDef);
    	
//		fixtureDef for the body
		shape.setAsBox(width /2 /Constants.PPM, height /2 /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constants.FLAG_BORDER_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);
	}

}
