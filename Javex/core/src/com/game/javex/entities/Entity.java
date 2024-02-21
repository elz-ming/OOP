package com.game.javex.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public abstract class Entity {
	protected Body body;
	protected Vector2 position;
	protected World world;
	protected Sprite sprite;
	
	protected int width;
	protected int height;
	protected String imgPath;

	public Entity(World world, Vector2 position) {
		this.world = world;
		this.position = position;
	}
	
	protected void createSprite() {
        position = body.getPosition();
        this.sprite = new Sprite(new Texture(Gdx.files.internal(imgPath)));
        this.sprite.setSize(width, height);
        this.sprite.setPosition(position.x *Constants.PPM - width /2, position.y *Constants.PPM - height/2);
	}
	
	public void update(float delta) {
		position = body.getPosition();
		sprite.setPosition(position.x *Constants.PPM - width /2, position.y *Constants.PPM - height/2);
	}
	
	public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }
	
	public void dispose() {
		sprite.getTexture().dispose();
	}
	
	public Body getBody() {
		return body;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	protected abstract void createBody();
}