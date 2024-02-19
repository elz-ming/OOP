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
    
	public AiControlManager(Entity enemy, Entity target) {
		this.enemy = enemy;
		this.target = target;
		
		this.enemySteerable = new SteerableEntityAdapter(enemy);
		this.targetSteerable = new SteerableEntityAdapter(target);
		
		seekBehavior = new Seek<>(enemySteerable, targetSteerable);
	}
	
	public void update(float dt) {

    }
}
