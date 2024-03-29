package com.game.javex.tools;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.javex.entities.Entity;

public class SteerableEntityAdapter implements Steerable<Vector2>{
	private Body body;
	private float boundingRadius;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private boolean tagged;
    
    public SteerableEntityAdapter(Entity entity) {
        this.body = entity.getBody();
        float width = entity.getWidth();
        float height = entity.getHeight();
        this.boundingRadius = (float) Math.sqrt((width / 2) * (width / 2) + (height / 2) * (height / 2));
        
        this.maxLinearSpeed = 100; 
        this.maxLinearAcceleration = 5000; 
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
    
//	Implemented from Steerable<Vector2>
	@Override public void setOrientation(float orientation) {}
	@Override public Location<Vector2> newLocation() {return null;}
	@Override public float getZeroLinearSpeedThreshold() {return 0;}
	@Override public void setZeroLinearSpeedThreshold(float value) {}
	@Override public float vectorToAngle(Vector2 vector) {return 0;}
	@Override public Vector2 angleToVector(Vector2 outVector, float angle) {return null;}
	@Override public float getMaxAngularSpeed() {return 0;}
	@Override public void setMaxAngularSpeed(float maxAngularSpeed) {}
	@Override public float getMaxAngularAcceleration() {return 0;}
	@Override public void setMaxAngularAcceleration(float maxAngularAcceleration) {}
}
