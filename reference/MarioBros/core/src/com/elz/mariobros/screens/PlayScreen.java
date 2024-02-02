package com.elz.mariobros.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import com.elz.mariobros.*;
import com.elz.mariobros.scenes.*;
import com.elz.mariobros.sprites.*;
import com.elz.mariobros.tools.B2WorldCreator;

public class PlayScreen implements Screen{
	private MarioBros game;
	Texture texture;
	private OrthographicCamera gameCam;
	private Viewport gamePort;
	private Hud hud;	
	
	//Tiled map variables
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;
	private B2WorldCreator creator;
	
	//sprites
	private Mario player;
	
	public PlayScreen(MarioBros game)
	{
		this.game = game;
		
		// create a camera used to follow mario through the game world
		gameCam = new OrthographicCamera();
		
		// create a FitViewPort to maintain virtual aspect ratio
		gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCam);
		
		// heads-up-display, create a HUD for scores/timers/level info
		hud = new Hud(game.batch);
		
		// load up map and setup map renderer
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
		
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);
	
		// create our Box2D world, setting no gravity in x, -10 in Y, and allow bodies to sleep
		world = new World(new Vector2(0, -10), true); // Gravity variable
		
		// allows for debug lines in our Box2D world
		b2dr = new Box2DDebugRenderer();
		
		creator = new B2WorldCreator(this);
		
		// create mario in our game world
		player = new Mario(this);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleInput(float dt)
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
		{
			player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
		{
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
		{
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
		}
	}
	
	public void update(float dt)
	{
		// handle user input first
		handleInput(dt);
		
		world.step(1/60f, 6, 2);
		
		gameCam.position.x = player.b2body.getPosition().x;
		
		// update our gameCam with correct coordinates after changes
		gameCam.update();
		
		// tell our renderer to draw only what our camera can see in our game world
		renderer.setView(gameCam);
	}

	@Override
	public void render(float delta) 
	{
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.renderer.render();
		
		this.b2dr.render(world, gameCam.combined);
		
		this.game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		this.hud.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.gamePort.update(width, height);
		
	}
	
	public TiledMap getMap()
	{
		return map;
	}
	
	public World getWorld()
	{
		return world;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
	}

}
