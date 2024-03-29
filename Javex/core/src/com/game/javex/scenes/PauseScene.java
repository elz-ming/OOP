	package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class PauseScene extends Scene {
    private TextButton resumeButton, menuButton;

    public PauseScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
    	
    	super(sceneManager, inputManager, outputManager);
    	 outputManager.pauseMusic();
    	
    	width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	
        // Load the skin
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        
        stage = new Stage(new ScreenViewport());
        backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); 
        stage.addActor(backgroundImage); 

       
        backgroundImage.setZIndex(0);

        // Create the buttons
        resumeButton = new TextButton("Resume Game", skin);
        menuButton = new TextButton("Return to Main Menu", skin);
        float xOffset = 50; 

        resumeButton = new TextButton("Resume Game", skin);
        resumeButton.setSize(300, 80); 
        resumeButton.setPosition((Gdx.graphics.getWidth() - resumeButton.getWidth()) / 2 - xOffset, Gdx.graphics.getHeight() / 2 + resumeButton.getHeight() / 2 + 10);
        resumeButton.getLabel().setFontScale(0.41f); 
        stage.addActor(resumeButton); 

        menuButton = new TextButton("Return to Main Menu", skin);
        menuButton.setSize(300, 80); 
        menuButton.setPosition((Gdx.graphics.getWidth() - menuButton.getWidth()) / 2 - xOffset, Gdx.graphics.getHeight() / 2 - menuButton.getHeight() / 2 - 60);
        menuButton.getLabel().setFontScale(0.41f); 
        
        
        
        
        
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
        });
        
        stage.addActor(menuButton); 
        
       
        menuButtons = new TextButton[]{resumeButton, menuButton};
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
        float spaceBetweenButtons = height * 0.06f; 
        float startY = height / 2 - buttonHeight / 2;

        resumeButton.setSize(buttonWidth, buttonHeight);
        menuButton.setSize(buttonWidth, buttonHeight);

       
        float fontScale = buttonHeight / 140f; 
        resumeButton.getLabel().setFontScale(fontScale);
        menuButton.getLabel().setFontScale(fontScale);

       
        resumeButton.setPosition((width - resumeButton.getWidth()) / 2, startY + spaceBetweenButtons / 2 + buttonHeight);
        menuButton.setPosition((width - menuButton.getWidth()) / 2, startY - spaceBetweenButtons / 2 - buttonHeight);
    }



    @Override
    protected void handleInput() {
        if (inputManager.isUpPressed()) {
            currentButtonIndex = (currentButtonIndex - 1 + menuButtons.length) % menuButtons.length;
            updateButtonStyles();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        } else if (inputManager.isDownPressed()) {
            currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
            updateButtonStyles();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        } else if (inputManager.isEnterPressed()) {
            if (currentButtonIndex == 0) {
                sceneManager.pop();
                outputManager.resumeMusic();
            } else if (currentButtonIndex == 1) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
                
                
            }
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        } 
    }
}
