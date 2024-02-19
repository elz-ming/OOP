package com.game.javex.tools;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.game.javex.entities.Entity;

public class AiControlManager {
	private Entity enemy;
	private Entity target;
	private Steerable<Vector2> enemySteerable;
	private Steerable<Vector2> targetSteerable;
	private Seek<Vector2> seekBehavior;
    private SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
    
	public AiControlManager(Entity enemy, Entity target) {
		this.enemy = enemy;
		this.target = target;
		
		this.enemySteerable = new SteerableEntityAdapter(enemy);
		this.targetSteerable = new SteerableEntityAdapter(target);
		
		seekBehavior = new Seek<>(enemySteerable, targetSteerable);
	}
	
	public void update(float dt) {
		if (seekBehavior != null) {
			steeringOutput = seekBehavior.calculateSteering(steeringOutput);
	        Vector2 newVelocity = steeringOutput.linear; // No need to scale by dt
	        if(newVelocity.x > 1) {
	            newVelocity.x = 1; // Cap speed at 1 to the right
	        } else if(newVelocity.x < -1) {
	            newVelocity.x = -1; // Cap speed at -1 to the left
	        }
	        enemy.getBody().setLinearVelocity(newVelocity.x, enemy.getBody().getLinearVelocity().y);
	    }
    }
}
