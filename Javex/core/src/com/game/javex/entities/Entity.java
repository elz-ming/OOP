package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
	protected Body body;
	protected int width;
	protected int height;

	public Entity(World world, Vector2 position, int width, int height) {
		this.width = width;
		this.height = height;
		this.body = createBox(world, position, width, height);
	}
	
	protected abstract Body createBox(World world, Vector2 position, int width, int height);
}