package com.game.javex.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public class Signboard extends Entity{
	private int identifier;
	private boolean visible = false;
	private String content;
	
	public Signboard(World world, Vector2 position, int width, int height, String selectedWorld, int identifier) {
		super(world, position);
		this.width = width;
    	this.height = height;
    	this.imgPath = Constants.SIGNBOARD_IMG_PATH;
    	this.identifier = identifier;  
    	createBody();
    	createSprite();
    	setContent(selectedWorld);
	}

	@Override
	protected void createBody() {
//		initialize bodyDef and fixtureDef
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
    	
//		bodyDef for the entire body
    	bodyDef.type = BodyDef.BodyType.StaticBody;
    	bodyDef.position.set((position.x + width /2) /Constants.PPM, (position.y + height /2) /Constants.PPM);
    	bodyDef.fixedRotation = true;
    	this.body = world.createBody(bodyDef);
    	
//		fixtureDef for the body
		shape.setAsBox(width /2 /Constants.PPM, height /2 /Constants.PPM);
		fixtureDef.shape = shape;
		fixtureDef.density = 0;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.filter.categoryBits = Constants.SIGNBOARD_BIT;
		fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
		this.body.createFixture(fixtureDef).setUserData(this);	
	}
	
	private void setContent(String selectedWorld) {
		if (identifier == 1) {
			content = "Text for the first signboard.";
		}
		
		else {
			switch (selectedWorld) {
	        case "Earth":
	        	switch (identifier) {
		        case 2:
		        	content = "Active plate tectonics shape the planet's surface, forming mountains,\n"
		            		+ "causing earthquakes, and renewing the crust.";
		        	break;
		        case 3:
		        	content = "The atmosphere contains a unique mix of gases crucial for life, \n"
		            		+ "including oxygen and nitrogen.";
		        	break;
		            
		        case 4:
		        	content = "Earth supports diverse ecosystems, from rainforests to deserts, \n"
		        			+ "harboring millions of different life forms.";
		        	break;
		            
		        default:
		        	content = "Text for the first signboard.";
		        	break;
		    	}
	        	break;
	            
	        case "Mars":
	        	switch (identifier) {
		        case 2:
		        	content = "Text for the second signboard.";
		        	break;
		            
		        case 3:
		        	content = "Text for the third signboard.";
		        	break;
		            
		        case 4:
		        	content = "Text for the fourth signboard.";
		        	break;
		            
		        default:
		        	content = "Text for the first signboard.";
		        	break;
		    	}
	        	break;
	        	
	        case "Venus":
	        	switch (identifier) {
		        case 2:
		        	content = "Text for the second signboard.";
		        	break;
		            
		        case 3:
		        	content = "Text for the third signboard.";
		        	break;
		            
		        case 4:
		        	content = "Text for the fourth signboard.";
		        	break;
		            	
		        default:
		        	content = "Text for the first signboard.";
		        	break;
		    	}
	        	break;
	        	
	        default:
	        	content = "Text for the first signboard.";
	        	break;
	    	}
		}
	}
	
	public String getContent() {
		return content;
	}
	
	public int getIdentifier() {
        return identifier;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean getVisible() {
		return visible;
	}
}


