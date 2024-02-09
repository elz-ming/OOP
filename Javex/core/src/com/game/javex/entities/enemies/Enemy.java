package com.game.javex.entities.enemies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends ApplicationAdapter{
	
	private OrthographicCamera camera;
	private float health;
	private float x;
	private float y;
	private float speed;
	
	private Box2DDebugRenderer b2dr;
	private World world;
	private Body enemy;
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w/2,h/2);
		
		//set up world
		world = new World(new Vector2(0,-9.8f), false);
		b2dr = new Box2DDebugRenderer();
		
		enemy = createPlayer();
	}
	
	@Override 
	public void render() {
		update(Gdx.graphics.getDeltaTime());
		//Render
//		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		b2dr.render(world, camera.combined);
	}
	
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width /2, height/2);
	}
	
	@Override
	public void dispose() {
		world.dispose();
		b2dr.dispose();
		
	}
	
	public void update(float delta) {
		world.step(1/60f, 6, 2);
		
	}
	
	public Body createPlayer() {
		Body pBody;
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(200,200);
		def.fixedRotation = true;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(32/2, 32/2);
		
		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		
		return pBody;
	}
}
