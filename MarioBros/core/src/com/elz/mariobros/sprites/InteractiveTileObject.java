package com.elz.mariobros.sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.elz.mariobros.MarioBros;
import com.elz.mariobros.screens.PlayScreen;

public abstract class InteractiveTileObject {
	protected PlayScreen screen;
	protected World world;
	protected TiledMap map;
	
	protected MapObject object;
	protected Rectangle bounds;
	protected Body body;
	
	public  InteractiveTileObject(PlayScreen screen, MapObject object)
	{
		this.screen = screen;
		world = screen.getWorld();
		map = screen.getMap();
		
		this.object = object;
		bounds = ((RectangleMapObject) object).getRectangle();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBros.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioBros.PPM);		
		body = world.createBody(bdef);
		
		shape.setAsBox(bounds.getWidth() / 2 / MarioBros.PPM, bounds.getHeight() / 2 / MarioBros.PPM);
		fdef.shape = shape;
		body.createFixture(fdef);
	}
}
