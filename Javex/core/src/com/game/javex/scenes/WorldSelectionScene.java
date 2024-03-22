package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class WorldSelectionScene extends Scene {
    private TextButton world1Button, world2Button, world3Button;
    private Table informationTable;
    private Label informationLabel;
    private String[] worldInformation;
    private String selectedWorld;
    private int currentInfoIndex = 0;
    private boolean worldSelected = false;

    public WorldSelectionScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.WORLDSELECTION_IMG_PATH)));
        backgroundImage.setSize(width, height);
        backgroundImage.setZIndex(0);

        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));

        world1Button = new TextButton("Earth", skin);
        world2Button = new TextButton("Mars", skin);
        world3Button = new TextButton("Venus", skin);

        world1Button.setSize(200, 80);
        world2Button.setSize(200, 80);
        world3Button.setSize(200, 80);

        world1Button.getLabel().setFontScale(0.5f);
        world2Button.getLabel().setFontScale(0.5f);
        world3Button.getLabel().setFontScale(0.5f);

        float buttonSpacing = 20;
        float totalHeight = 3 * world1Button.getHeight() + 2 * buttonSpacing;
        float startY = (height - totalHeight) / 2;
        float xOffset = 30;

        world1Button.setPosition((width / 2 - world1Button.getWidth() / 2) - xOffset, startY + 2 * (world1Button.getHeight() + buttonSpacing));
        world2Button.setPosition((width / 2 - world2Button.getWidth() / 2) - xOffset, startY + world1Button.getHeight() + buttonSpacing);
        world3Button.setPosition((width / 2 - world3Button.getWidth() / 2) - xOffset, startY);

        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage);
        stage.addActor(world1Button);
        stage.addActor(world2Button);
        stage.addActor(world3Button);

        menuButtons = new TextButton[]{world1Button, world2Button, world3Button};

        informationTable = new Table();
        informationTable.setVisible(false);

        informationTable.center(); 
        informationTable.pad(20); 
        stage.addActor(informationTable); 

        
        Texture backgroundTexture = new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH));
        informationTable.setBackground(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));

        informationLabel = new Label("", skin);
        informationLabel.setAlignment(Align.center);
        informationLabel.setColor(Color.WHITE);
        informationLabel.setWrap(true); 
        
        float labelFontScale = 1.4f; 
        informationLabel.setFontScale(labelFontScale);

        informationTable.add(informationLabel).expand().fill().pad(20).width(Gdx.graphics.getWidth() * 0.5f); 

        updateButtonStyles();
    }

    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        this.width = width;
        this.height = height;

        
        float buttonWidth = width * 0.4f; 
        float buttonHeight = height * 0.15f; 
        float spaceBetweenButtons = height * 0.06f; 
        float totalHeight = 3 * buttonHeight + 2 * spaceBetweenButtons;
        float startY = (height - totalHeight) / 2;

        world1Button.setSize(buttonWidth, buttonHeight);
        world2Button.setSize(buttonWidth, buttonHeight);
        world3Button.setSize(buttonWidth, buttonHeight);

        
        float fontScale = buttonHeight / 120f; 
        world1Button.getLabel().setFontScale(fontScale);
        world2Button.getLabel().setFontScale(fontScale);
        world3Button.getLabel().setFontScale(fontScale);

        // Center the buttons
        world1Button.setPosition((width - world1Button.getWidth()) / 2, startY + 2 * (world1Button.getHeight() + spaceBetweenButtons));
        world2Button.setPosition((width - world2Button.getWidth()) / 2, startY + world1Button.getHeight() + spaceBetweenButtons);
        world3Button.setPosition((width - world3Button.getWidth()) / 2, startY);

        
        informationTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.CHATBOX_IMG_PATH)))));

        informationTable.pack();
        float maxWidth = width * 0.6f; 
        float maxHeight = height * 0.3f; 
        informationTable.setWidth(Math.min(informationTable.getWidth(), maxWidth));
        informationTable.setHeight(Math.min(informationTable.getHeight(), maxHeight));

        // Center the information table on the screen
        informationTable.setPosition((width - informationTable.getWidth()) / 2, (height - informationTable.getHeight()) / 2);
    }












    @Override
    protected void handleInput() {
        if (!worldSelected) {
            if (inputManager.isUpPressed()) {
                currentButtonIndex = (currentButtonIndex - 1 + menuButtons.length) % menuButtons.length;
                updateButtonStyles();
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            if (inputManager.isDownPressed()) {
                currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
                updateButtonStyles();
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            if (inputManager.isEnterPressed()) {
                worldSelected = true;
                world1Button.remove();
                world2Button.remove();
                world3Button.remove();
                informationTable.setVisible(true);
                switch (currentButtonIndex) {
                    case 0: // Earth
                        selectedWorld = "Earth";
                        worldInformation = new String[]{
                                "Earth is the third planet from the Sun.",
                                "It is the only known planet to support life.",
                                "Earth's gravity is approximately 9.8 m/s²."
                        };
                        break;
                    case 1: // Mars
                        selectedWorld = "Mars";
                        worldInformation = new String[]{
                                "Mars is known as the Red Planet.",
                                "It has the largest volcano in the solar system, Olympus Mons.",
                                "Mars' gravity is approximately 3.7 m/s²."
                        };
                        break;
                    case 2: // Venus
                        selectedWorld = "Venus";
                        worldInformation = new String[]{
                                "Venus is the second planet from the Sun.",
                                "It is the hottest planet in the solar system.",
                                "Venus' gravity at approximately 8.9 m/s²"
                        };
                        break;
                }
                informationLabel.setText(worldInformation[currentInfoIndex]);
                informationLabel.pack();
                informationLabel.setPosition((width - informationLabel.getWidth()) / 2, (height - informationLabel.getHeight()) / 2);
                currentInfoIndex++;
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        } else {
            if (inputManager.isEnterPressed()) {
                if (currentInfoIndex < worldInformation.length) {
                    informationLabel.setText(worldInformation[currentInfoIndex]);
                    informationLabel.pack();
                    informationLabel.setPosition((width - informationLabel.getWidth()) / 2, (height - informationLabel.getHeight()) / 2);
                    currentInfoIndex++;
                } else {
                    sceneManager.set(new PlayScene(sceneManager, inputManager, outputManager, selectedWorld));
                }
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }


}
