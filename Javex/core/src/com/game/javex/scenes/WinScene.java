package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.javex.inouts.*;

public class WinScene extends Scene {
	private TextButton homeButton;
	private String audioPath;
    private float inputDelayTimer = 0f;
    private final float INPUT_DELAY = 0.2f; // 0.2 seconds
 


    public WinScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        audioPath = Constants.END_AUDIO_PATH;
        outputManager.play(audioPath, true);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // Set background
        backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.END_IMG_PATH)));
        backgroundImage.setSize(width, height); 
        backgroundImage.setZIndex(0); 

        // Set skin
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        
        
   

       

        // Create buttons
        homeButton = new TextButton("Home", skin);

       
        homeButton.getLabel().setFontScale(0.5f); 
       

        
        float buttonWidth = width * 0.4f; 
        float buttonHeight = height * 0.15f; 
        homeButton.setSize(buttonWidth, buttonHeight);
        

       
        homeButton.setPosition((width / 2 - homeButton.getWidth() / 2), 2 * (homeButton.getHeight()));
        
        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage); 
        stage.addActor(homeButton);
        
        
        menuButtons = new TextButton[]{homeButton};

        updateButtonStyles();
    }
    
    
    
    
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        this.width = width;
        this.height = height;

        backgroundImage.setSize(width, height);

        
        float buttonWidth = width * 0.4f; 
        float buttonHeight = height * 0.15f; 

        
        homeButton.setSize(buttonWidth, buttonHeight);

     
        float fontScale = buttonHeight / 120f; 
        
        homeButton.getLabel().setFontScale(fontScale);

        
        homeButton.setPosition((width - homeButton.getWidth()) / 2, (height - homeButton.getHeight()) / 2);
        
    }

    
    @Override
    protected void handleInput() {
      
        inputDelayTimer += Gdx.graphics.getDeltaTime();

        if (inputDelayTimer < INPUT_DELAY) {
            return; 
        }

        if (inputManager.isEnterPressed()) {
            currentButtonIndex = (currentButtonIndex - 1 + menuButtons.length) % menuButtons.length;
            sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            updateButtonStyles();
            inputDelayTimer = 0f; 
        
            
  
        }
    }

}
