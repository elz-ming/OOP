package com.elz.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.elz.mariobros.MarioBros;
import com.elz.mariobros.screens.*;

public class Mario extends Sprite{
	public World world;
	public Body b2body;
	
	private PlayScreen screen;
	
	public Mario(PlayScreen screen)
	{
		this.screen = screen;
		world = screen.getWorld();
		defineMario();
	}
	
	public void defineMario()
	{
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / MarioBros.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}
}
