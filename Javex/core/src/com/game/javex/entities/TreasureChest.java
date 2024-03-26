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
    private int identifier;
    private String question;
    private String[] answers;
    private int correctAnswerIndex;
    
    private boolean solving = false;
    private boolean resetSolving = true;
    private boolean solved = false;
    
    private Texture[] chestTextures;
    private int currentTextureIndex;

    public TreasureChest(World world, Vector2 position, int width, int height, String selectedWorld, int identifier) {
        super(world, position);
        this.width = width;
        this.height = height;
        this.chestTextures = new Texture[3]; // Assuming you have 3 textures
        loadTextures();
        this.currentTextureIndex = 0; // Start with the first texture
        this.stateTime = 0;
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
        stateTime += delta;
        if (stateTime >= Constants.ANIMATION_DURATION) {
            currentTextureIndex = (currentTextureIndex + 1) % 3; // Cycle through textures
            stateTime = 0;
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
	                case 1: // Earth Treasure Chest
	                    question = "What supports Earth's diverse life forms?";
	                    answers = new String[]{"Oceans", "Deserts", "Ecosystems", "Clouds"};
	                    correctAnswerIndex = 2; // Index of the correct answer in the 'answers' array
	                    break;
	                case 2:
	                    question = "What is unique to Earth's atmosphere for life?";
	                    answers = new String[]{"Methane", "Oxygen", "Helium", "Argon"};
	                    correctAnswerIndex = 1;
	                    break;
	                case 3:
	                    question = "What geological feature is active on Earth?";
	                    answers = new String[]{"Erosion", "Photosynthesis", "Tectonics", "Radiation"};
	                    correctAnswerIndex = 2;
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
	                case 1: // Mars Treasure Chest
	                    question = "What is Mars's largest volcano called?";
	                    answers = new String[]{"Etna", "Kilimanjaro", "Olympus Mons", "Fuji"};
	                    correctAnswerIndex = 2;
	                    break;
	                case 2:
	                    question = "How long is a day on Mars?";
	                    answers = new String[]{"12 hours", "24.6 hours", "30 hours", "48 hours"};
	                    correctAnswerIndex = 1;
	                    break;
	                case 3:
	                    question = "Why is Mars red?";
	                    answers = new String[]{"Water", "Trees", "Iron oxide", "Snow"};
	                    correctAnswerIndex = 2;
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
	                case 1: // Venus Treasure Chest
	                    question = "What is Venus's atmosphere mostly made of?";
	                    answers = new String[]{"Nitrogen", "Oxygen", "Hydrogen", "Carbon dioxide"};
	                    correctAnswerIndex = 3;
	                    break;
	                case 2:
	                    question = "How hot can Venus's surface get?";
	                    answers = new String[]{"182°C", "382°C", "482°C", "682°C"};
	                    correctAnswerIndex = 2;
	                    break;
	                case 3:
	                    question = "Which direction does Venus rotate?";
	                    answers = new String[]{"Clockwise", "Upward", "Downward", "Counterclockwise"};
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
