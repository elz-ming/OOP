package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
	protected Body body;
	protected Vector2 position;
	protected World world;
	
	protected int width;
	protected int height;

	public Entity(World world, Vector2 position) {
		this.world = world;
		this.position = position;
	}
	
	protected abstract void createBody(int width, int height);
	
	public Body getBody() {
		return body;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}