package com.game.javex.tools;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.game.javex.entities.Enemy;
import com.game.javex.entities.Player;



public class AiControlManager implements Steerable<Vector2>{
	private Enemy enemy;
	private Body body;
	private float boundingRadius;
	
	private Player player;
	
	private float maxLinearSpeed, maxLinearAcceleration;
	private float maxAngularSpeed, maxAngularAcceleration;
	private boolean tagged;
	
	private SteeringBehavior<Vector2> behavior;
	private SteeringAcceleration<Vector2> steeringOutput;

	
	public AiControlManager(Enemy enemy, Player player) {
		this.enemy = enemy;
		this.body = enemy.getBody();
		this.boundingRadius = enemy.getBoundingRadius();
		
		this.player = player;
		
		initSteeringBehaviors();
		this.body.setUserData(this);
//		this.maxLinearSpeed = 500;
//		this.maxLinearAcceleration = 5000;
//		this.maxAngularSpeed = 30;
//		this.maxAngularAcceleration = 5;
//
//		this.tagged = false;
//
//		this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
	}
	
	private void initSteeringBehaviors() {
		// Initialize the Arrive behavior to follow the player
//		Arrive<Vector2> arrive = new Arrive<>(this, player) // 'this' refers to the AiControlManager, which is Steerable
//	            .setTimeToTarget(0.1f)
//	            .setArrivalTolerance(2f)
//	            .setDecelerationRadius(10);
//	        setBehavior(arrive);
    }
	
	public void update(float dt) {
		if(behavior != null) {
			behavior.calculateSteering(steeringOutput);
			applySteering(dt);
		}
		
	}
	
	private void applySteering(float dt) {
		boolean anyAccceleration = false;
		
		if (!steeringOutput.linear.isZero()) {
			Vector2 force = steeringOutput.linear.scl(dt);
			body.applyForceToCenter(force, true);
			anyAccceleration = true;
		}
		
		if(steeringOutput.angular != 0) {
			body.applyTorque(steeringOutput.angular * dt, true);
			anyAccceleration = true;
		} else {
			Vector2 linVel = getLinearVelocity();
			if(!linVel.isZero()) {
				float newOrientation = vectorToAngle(linVel);
				body.setAngularVelocity((newOrientation = getAngularVelocity()) * dt);
				body.setTransform(body.getPosition(), newOrientation);
			}
		}
		
		if(anyAccceleration) {
			Vector2 velocity = body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			if(currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
				body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
			}
			
			if(body.getAngularVelocity() > maxAngularSpeed) {
				body.setAngularVelocity(maxAngularSpeed);
			}
		}
	}
	
	@Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    // Self-created methods
    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }
    
    public SteeringBehavior<Vector2> getBehavior() {
        return behavior;
    }


	
	// Unused or Placeholder Methods
    @Override public void setOrientation(float orientation) {}
    @Override public float vectorToAngle(Vector2 vector) {return SteeringUtils.vectorToAngle(vector);}
    @Override public Vector2 angleToVector(Vector2 outVector, float angle) {return SteeringUtils.angleToVector(outVector, angle);}
    @Override public Location<Vector2> newLocation() {return null;}
    @Override public float getZeroLinearSpeedThreshold() {return 0;}
    @Override public void setZeroLinearSpeedThreshold(float value) {}
    @Override public float getMaxLinearSpeed() {return maxLinearSpeed;}
    @Override public void setMaxLinearSpeed(float maxLinearSpeed) {this.maxLinearSpeed = maxLinearSpeed;}
    @Override public float getMaxLinearAcceleration() {return maxLinearAcceleration;}
    @Override public void setMaxLinearAcceleration(float maxLinearAcceleration) {this.maxLinearAcceleration = maxLinearAcceleration;}
    @Override public float getMaxAngularSpeed() {return maxAngularSpeed;}
    @Override public void setMaxAngularSpeed(float maxAngularSpeed) {this.maxAngularSpeed = maxAngularSpeed;}
    @Override public float getMaxAngularAcceleration() {return maxAngularAcceleration;}
    @Override public void setMaxAngularAcceleration(float maxAngularAcceleration) {this.maxAngularAcceleration = maxAngularAcceleration;}
}
