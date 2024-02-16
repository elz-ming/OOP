package com.game.javex.entities.enemies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.game.javex.entities.enemies.Constants.PPM;

public class Enemy{
	
	private Sprite sprite;
	private Texture texture;
	private Body enemy;
	
	//public Enemy(playScreen screen, float x, float y)

	

    public void create(World world) {
        // Replace "path/to/your/enemy/texture.png" with the actual path
    	texture = new Texture("bucket.png");
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Set up the enemy body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(80 / PPM, 1000 / PPM); // Set initial position
        enemy = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 , 32); // Set enemy size

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

        enemy.createFixture(fixtureDef);
        shape.dispose();
        
        sprite = new Sprite(texture);
        sprite.setPosition(enemy.getPosition().x * PPM, enemy.getPosition().y * PPM);
        sprite.setSize(32 / PPM, 32 / PPM);
        

    }
    
    public Sprite getSprite() {
    	return sprite;
    }
    
    

    public void update(float delta) {
        // Update enemy logic here
    	
    	int horizontalForce = 0;
    	
    	//movement of player liaise with ains
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			horizontalForce += 1;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			enemy.applyForceToCenter(0,300, false);
		}
		
		enemy.setLinearVelocity(horizontalForce * 5, enemy.getLinearVelocity().y);
		

    }


	

}
