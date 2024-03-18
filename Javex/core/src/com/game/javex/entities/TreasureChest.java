package com.game.javex.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.javex.Constants;

public class TreasureChest extends Entity {
	
    private Texture[] chestTextures;
    private int currentTextureIndex;
    private float animationTimer;
    private final float ANIMATION_DURATION = 0.5f; // Adjust as needed
    private int identifier;
    private String question;
    private String[] answers;
    private int correctAnswerIndex;
    private boolean solving = false;
    private boolean resetSolving = true;
    private boolean solved = false;

    public TreasureChest(World world, Vector2 position, int width, int height, String selectedWorld, int identifier) {
        super(world, position);
        this.width = width;
        this.height = height;
        this.chestTextures = new Texture[3]; // Assuming you have 3 textures
        loadTextures();
        this.currentTextureIndex = 0; // Start with the first texture
        this.animationTimer = 0;
        this.identifier = identifier;
        createBody();
        createAnimatedSprite();
        setContent(selectedWorld);
    }
    
    private void loadTextures() {
        for (int i = 0; i < 3; i++) {
            chestTextures[i] = new Texture(Gdx.files.internal("chest_" + i + ".png"));
        }
    }
    
    @Override
    public void update(float delta) {
    	super.update(delta);
        animationTimer += delta;
        if (animationTimer >= ANIMATION_DURATION) {
            currentTextureIndex = (currentTextureIndex + 1) % 3; // Cycle through textures
            animationTimer = 0;
            updateAnimatedSpriteTexture();
        }
        // Update sprite position based on body position
        if (this.sprite != null) {
            position = body.getPosition();
            this.sprite.setPosition(position.x * Constants.PPM - width / 2, position.y * Constants.PPM - height / 2);
        }
    }
    
    // Update the animated sprite with the current texture
    private void updateAnimatedSpriteTexture() {
        this.sprite.setTexture(chestTextures[currentTextureIndex]);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    	super.render(spriteBatch);
        if (this.sprite != null) {
            this.sprite.draw(spriteBatch);
        }
    }

    @Override
    public void dispose() {
        super.dispose(); // Dispose of textures and Box2D body
    }

    @Override
    protected void createBody() {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((position.x + width / 2) / Constants.PPM, (position.y + height / 2) / Constants.PPM);
        bodyDef.fixedRotation = true;
        this.body = world.createBody(bodyDef);

        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        fixtureDef.filter.categoryBits = Constants.TREASURE_CHEST_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT;
        this.body.createFixture(fixtureDef).setUserData(this);
    }
	
    private void createAnimatedSprite() {
        position = body.getPosition();
        this.sprite = new Sprite(chestTextures[currentTextureIndex]);
        this.sprite.setSize(width, height);
        // Calculate the position relative to the center of the sprite
        this.sprite.setOriginCenter();
        this.sprite.setPosition(position.x * Constants.PPM - this.sprite.getWidth() / 2, position.y * Constants.PPM - this.sprite.getHeight() / 2);
    }
	
	private void setContent(String selectedWorld) {
		switch (selectedWorld) {
        case "Earth":
        	switch (identifier) {
	        case 1:
	        	question = "What is the capital of France?";
    			answers = new String[]{"Paris", "London", "Berlin", "Madrid"};
    			correctAnswerIndex = 0;
	        	break;
	        case 2:
	        	question = "What is the largest planet in our solar system?";
                answers = new String[]{"Jupiter", "Saturn", "Earth", "Mars"};
                correctAnswerIndex = 0;
	        	break;
	            
	        case 3:
	        	question = "What is the main ingredient in sushi?";
                answers = new String[]{"Rice", "Noodles", "Bread", "Pasta"};
                correctAnswerIndex = 0;
	        	break;
	            
	        default:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	    	}
        	break;
            
        case "Mars":
        	switch (identifier) {
	        case 1:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	            
	        case 2:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	            
	        case 3:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	            
	        default:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	    	}
        	break;
        	
        case "Venus":
        	switch (identifier) {
	        case 1:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	            
	        case 2:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	            
	        case 3:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	            	
	        default:
	        	question = "Default question";
                answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
                correctAnswerIndex = 0;
	        	break;
	    	}
        	break;
        	
        default:
        	question = "Default question";
            answers = new String[]{"Default answer 1", "Default answer 2", "Default answer 3", "Default answer 4"};
            correctAnswerIndex = 0;
        	break;
    	}
	}
	
    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
    
    public int getIdentifier() {
		return identifier;
	}
    
    public void setSolving(boolean solving) {
    	this.solving = solving;
    }
    
    public boolean getSolving() {
		return solving;
	}
    
    public void setResetSolving(boolean resetSolving) {
    	this.resetSolving = resetSolving;
    }
    
    public boolean getResetSolving() {
		return resetSolving;
	}
    
    public void setSolved(boolean solved) {
    	this.solved = solved;
    }
    
    public boolean getSolved() {
		return solved;
	}
}
