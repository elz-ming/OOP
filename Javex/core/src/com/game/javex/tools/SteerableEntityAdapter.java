package com.game.javex.tools;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.javex.entities.Entity;
import com.game.javex.tools.SteeringUtils;

public class SteerableEntityAdapter implements Steerable<Vector2>{
	private Entity entity;
	private Body body;
	private int width;
	private int height;
	private float boundingRadius;
    private float maxLinearSpeed, maxLinearAcceleration, maxAngularSpeed, maxAngularAcceleration;
    private boolean tagged;
    
    private SteeringBehavior<Vector2> behavior;
    private SteeringAcceleration<Vector2> steeringOutput;
    
    public SteerableEntityAdapter(Entity entity) {
        this.entity = entity;
        this.body = entity.getBody();
        this.width = entity.getWidth();
        this.height = entity.getHeight();
        this.boundingRadius = (float) Math.sqrt((width / 2) * (width / 2) + (height / 2) * (height / 2));
        
        this.maxLinearSpeed = 100; // Adjust as necessary
        this.maxLinearAcceleration = 5000; // Adjust as necessary
        this.maxAngularSpeed = 30; // Adjust as necessary
        this.maxAngularAcceleration = 5; // Adjust as necessary
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

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }
    
    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }
    
    public void setBehavior(SteeringBehavior<Vector2> behavior) {
    	this.behavior = behavior;
    }
    
    public SteeringBehavior<Vector2> getBehavior() {
    	return behavior;
    }
    
//	Implemented from Steerable<Vector2>
	@Override public void setOrientation(float orientation) {}
	@Override public Location<Vector2> newLocation() {return null;}
	@Override public float getZeroLinearSpeedThreshold() {return 0;}
	@Override public void setZeroLinearSpeedThreshold(float value) {}
}
