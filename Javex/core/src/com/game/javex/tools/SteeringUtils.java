package com.game.javex.tools;

import com.badlogic.gdx.math.Vector2;

public class SteeringUtils {
	public static float vectorToAngle (Vector2 vector) {
		return (float)Math.atan2(vector.y, vector.x);
	}
	
	public static Vector2 angleToVector (Vector2 outVector, float angle) {
		outVector.x = (float)Math.cos(angle);
        outVector.y = (float)Math.sin(angle);
        return outVector;
	}
}