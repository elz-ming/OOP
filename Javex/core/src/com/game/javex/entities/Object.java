package com.game.javex.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Object {
    private Body body;

    public Object(World world) {
        // Create object body as static body using Box2D
    }

    public void update(float delta) {
        // Static objects may not need to update, but method is here for consistency
    }
}