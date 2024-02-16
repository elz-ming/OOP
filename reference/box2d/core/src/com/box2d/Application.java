package com.box2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.box2d.tools.Constants.PPM;;

public class Application extends ApplicationAdapter {
	public static final String TITLE = "BoxBox";
	
//	Screen Width and Height
	public static final int screenWIDTHpixels = 800;
	public static final int screenHEIGHTpixels = 600;
	
	public static final float worldWIDTHmeters = 600;
	public static final float worldHEIGHTmeters = 1000;
	
	public static final float viewportWIDTHmeters = screenWIDTHpixels / PPM;
	public static final float viewportHEIGHTmeters = screenHEIGHTpixels / PPM;
	
	private boolean DEBUG = false;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player;
	private Body platform;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		viewport = new FitViewport(viewportWIDTHmeters, viewportHEIGHTmeters, camera);
		
		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();
		
		player = createBox((int) (worldWIDTHmeters/2), 500, 32, 32, false);
		platform = createBox((int) (worldWIDTHmeters/2), 0, (int)worldWIDTHmeters, 32, true);
	}
	
	public void update(float dt) {
		world.step(1/60f, 6, 2);
		
		inputUpdate(dt);
		cameraUpdate(dt);
	}
	
	public void cameraUpdate(float dt) {
		float cameraHalfWidth = viewportWIDTHmeters * 0.5f;
		float cameraHalfHeight = viewportHEIGHTmeters *0.5f;
		
		float minX = cameraHalfWidth;
		float maxX = worldWIDTHmeters - cameraHalfWidth;
		float minY = cameraHalfHeight;
		float maxY = worldHEIGHTmeters - cameraHalfHeight;
		
		Vector3 position = camera.position;
		
		position.x = MathUtils.clamp(player.getPosition().x *PPM, minX, maxX);
		position.y = MathUtils.clamp(player.getPosition().y *PPM, minY, maxY);
		
		camera.position.set(position);
		
		camera.update();
	}
	
	public void inputUpdate(float dt) {
		int horizontalForce = 0;
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce +=1;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.applyForceToCenter(0, 100, false);
		}
		
		player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
		
		// After movement logic, clamp player's velocity if near world boundaries
	    Vector2 position = player.getPosition();
	    Vector2 velocity = player.getLinearVelocity();
	    
	    // Check X boundaries
	    if (position.x <= (0 + 16) /PPM && velocity.x < 0) {
	    	player.setLinearVelocity(0, velocity.y); // Stop moving left
	    	position.x = (0 + 16) /PPM;
	    	player.setTransform(position, 0);
	    } 
	    else if (position.x >= (worldWIDTHmeters - 16)/PPM && velocity.x > 0) {
	    	player.setLinearVelocity(0, velocity.y); // Stop moving right
	    	position.x = (worldWIDTHmeters - 16) /PPM;
	    	player.setTransform(position, 0);
	    }
	    
	    if (position.y <= 0 && velocity.y < 0) {
	        player.setLinearVelocity(velocity.x, 0); // Stop moving left
	    } else if (position.x >= worldHEIGHTmeters && velocity.x > 0) {
	        player.setLinearVelocity(velocity.x, 0); // Stop moving right
	    }
	}
	
	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		b2dr.render(world, camera.combined.scl(PPM));
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.setToOrtho(false, width/2, height/2);
	}
	
	@Override
	public void dispose () {
		world.dispose();
		b2dr.dispose();
	}
	
	public Body createBox(int x, int y, int width, int height, boolean isStatic) {
		Body pBody;
		BodyDef defBody = new BodyDef();
		
		if (isStatic) {
			defBody.type = BodyDef.BodyType.StaticBody;
		} else {
			defBody.type = BodyDef.BodyType.DynamicBody;
		}
		
		defBody.position.set(x /PPM, y/PPM);
		defBody.fixedRotation = true;
		pBody = world.createBody(defBody);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2 /PPM, height/2 /PPM);
		
//		1.0f is the density, 1 is enough
		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		
		return pBody;
	}
}
