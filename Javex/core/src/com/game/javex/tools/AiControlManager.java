package com.game.javex.tools;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.javex.entities.Entity;
import com.game.javex.entities.SteerableEntityAdapter;

public class AiControlManager {
	private Entity enemy;
	private Entity target;
	private Steerable<Vector2> enemySteerable;
	private Steerable<Vector2> targetSteerable;
	private Seek<Vector2> seekBehavior;
    private SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
    
    float maxLinearSpeed = 100;
	float maxLinearAcceleration = 5000;
	float maxAngularSpeed = 30;
	float maxAngularAcceleration = 5;
    
	public AiControlManager(Entity enemy, Entity target) {
		this.enemy = enemy;
		this.target = target;
		
		this.enemySteerable = new SteerableEntityAdapter(enemy);
		this.targetSteerable = new SteerableEntityAdapter(target);
		
		seekBehavior = new Seek<>(enemySteerable, targetSteerable);
	}
	
	public void update(float dt) {
		if (seekBehavior != null) {
			seekBehavior.setTarget(new SteerableAdapter<Vector2>() {
	            @Override
	            public Vector2 getPosition() {
	            	System.out.println(target.getBody().getPosition());
	                return target.getBody().getPosition(); // Get the player's current position
	            }
	        });
            seekBehavior.calculateSteering(steeringOutput);
            Vector2 newVelocity = steeringOutput.linear.scl(dt);
            
            enemy.getBody().setLinearVelocity(newVelocity.x, enemy.getBody().getLinearVelocity().y);
        }
    }
}
