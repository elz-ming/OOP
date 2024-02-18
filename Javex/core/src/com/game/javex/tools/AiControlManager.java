package com.game.javex.tools;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;



public class AiControlManager implements Steerable<Vector2>{
	
	Body body;
	boolean tagged;
	float boundingRadius;
	float maxLinearSpeed, maxLinearAcceleration;
	float maxAngularSpeed, maxAngularAcceleration;
	
	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steeringOutput;

	
	public AiControlManager(Body body, float boundingRadius) {
		this.body = body;
		this.boundingRadius = boundingRadius;
		
		this.maxLinearSpeed = 500;
		this.maxLinearAcceleration = 5000;
		this.maxAngularSpeed = 30;
		this.maxAngularAcceleration = 5;

		this. tagged = false;

		this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
		this.body. setUserData(this);
	}
	
	public void update(float delta) {
		if(behavior != null) {
			behavior.calculateSteering(steeringOutput);
			applySteering(delta);
		}
		
	}
	
	private void applySteering(float delta) {
		boolean anyAccceleration = false;
		
		if (!steeringOutput.linear.isZero()) {
			Vector2 force = steeringOutput.linear.scl(delta);
			body.applyForceToCenter(force, true);
			anyAccceleration = true;
		}
		
		if(steeringOutput.angular != 0) {
			body.applyTorque(steeringOutput.angular * delta, true);
			anyAccceleration = true;
		} else {
			Vector2 linVel = getLinearVelocity();
			if(!linVel.isZero()) {
				float newOrientation = vectorToAngle(linVel);
				body.setAngularVelocity((newOrientation = getAngularVelocity()) * delta);
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
		// TODO Auto-generated method stub
		return body.getPosition();
	}

	@Override
	public float getOrientation() {
		// TODO Auto-generated method stub
		return body.getAngle();
	}

	@Override
	public void setOrientation(float orientation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float vectorToAngle(Vector2 vector) {
		// TODO Auto-generated method stub
		return SteeringUtilis.vectorToAngle(vector);
	}

	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle) {
		// TODO Auto-generated method stub
		return SteeringUtilis.angleToVector(outVector, angle);
	}

	@Override
	public Location<Vector2> newLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getZeroLinearSpeedThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZeroLinearSpeedThreshold(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMaxLinearSpeed() {
		// TODO Auto-generated method stub
		return maxLinearSpeed;
	}

	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		// TODO Auto-generated method stub
		this.maxLinearSpeed = maxLinearSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		// TODO Auto-generated method stub
		return maxLinearAcceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		// TODO Auto-generated method stub
		this.maxLinearAcceleration = maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		// TODO Auto-generated method stub
		return maxAngularSpeed;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		// TODO Auto-generated method stub
		this.maxAngularSpeed = maxAngularSpeed;
	}

	@Override
	public float getMaxAngularAcceleration() {
		// TODO Auto-generated method stub
		return maxAngularAcceleration;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		// TODO Auto-generated method stub
		this.maxAngularAcceleration = maxAngularAcceleration;
	}

	@Override
	public Vector2 getLinearVelocity() {
		// TODO Auto-generated method stub
		return body.getLinearVelocity();
	}

	@Override
	public float getAngularVelocity() {
		// TODO Auto-generated method stub
		return body.getAngularVelocity();
	}

	@Override
	public float getBoundingRadius() {
		// TODO Auto-generated method stub
		return boundingRadius;
	}

	@Override
	public boolean isTagged() {
		// TODO Auto-generated method stub
		return tagged;
	}

	@Override
	public void setTagged(boolean tagged) {
		// TODO Auto-generated method stub
		this.tagged = tagged;
	}
	
	public Body getBody() {
		return body;
	}
	
	public void setBehavior(SteeringBehavior<Vector2> behavior) {
		this.behavior = behavior;
	}
	
	public SteeringBehavior<Vector2> getBehavior(){
		return behavior;
	}

}
