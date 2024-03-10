package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.Constants;
import com.game.javex.inouts.*;

public class WorldSelectionScene extends Scene {
    private TextButton world1Button, world2Button, world3Button;
    private Label informationLabel;
    private String[] worldInformation;
    private int currentInfoIndex = 0;
    private boolean worldSelected = false;

    public WorldSelectionScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        
        backgroundImage = new Image(new Texture(Gdx.files.internal(Constants.MENU_IMG_PATH)));
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

        informationLabel = new Label("", skin, "default");
        informationLabel.setAlignment(Align.center);
        informationLabel.getStyle().background = skin.newDrawable("white", Color.LIGHT_GRAY);
        informationLabel.setColor(Color.WHITE);

        
        informationLabel.getStyle().background.setLeftWidth(10);
        informationLabel.getStyle().background.setRightWidth(10);
        informationLabel.getStyle().background.setTopHeight(10);
        informationLabel.getStyle().background.setBottomHeight(10);
        
        
        stage.addActor(informationLabel);

        updateButtonStyles();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        this.width = width;
        this.height = height;
        
        backgroundImage.setSize(width, height);

        // Recalculate button sizes and positions based on the new resolution
        float buttonWidth = width * 0.4f; // Increase to 40% of the screen width
        float buttonHeight = height * 0.15f; // Increase to 15% of the screen height
        float spaceBetweenButtons = height * 0.06f; // 6% of the screen height
        float totalHeight = 3 * buttonHeight + 2 * spaceBetweenButtons;
        float startY = (height - totalHeight) / 2;

        world1Button.setSize(buttonWidth, buttonHeight);
        world2Button.setSize(buttonWidth, buttonHeight);
        world3Button.setSize(buttonWidth, buttonHeight);

        // Adjust the font scale based on the button size
        float fontScale = buttonHeight / 120f; // Adjust if needed
        world1Button.getLabel().setFontScale(fontScale);
        world2Button.getLabel().setFontScale(fontScale);
        world3Button.getLabel().setFontScale(fontScale);

        // Center the buttons
        world1Button.setPosition((width - world1Button.getWidth()) / 2, startY + 2 * (world1Button.getHeight() + spaceBetweenButtons));
        world2Button.setPosition((width - world2Button.getWidth()) / 2, startY + world1Button.getHeight() + spaceBetweenButtons);
        world3Button.setPosition((width - world3Button.getWidth()) / 2, startY);

        // Update the information label size and position
        if (worldSelected) {
            informationLabel.pack();
            informationLabel.setPosition((width - informationLabel.getWidth()) / 2, (height - informationLabel.getHeight()) / 2);
        }
    }


    @Override
    protected void handleInput() {
        if (!worldSelected) {
            if (inputManager.isUpPressed() || inputManager.isDownPressed()) {
                currentButtonIndex = (currentButtonIndex + 1) % menuButtons.length;
                updateButtonStyles();
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            if (inputManager.isEnterPressed()) {
                worldSelected = true;
                world1Button.remove();
                world2Button.remove();
                world3Button.remove();
                switch (currentButtonIndex) {
                    case 0: // Earth
                        worldInformation = new String[]{
                            "Earth is the third planet from the Sun.",
                            "It is the only known planet to support life.",
                            "Earth has a large ocean covering 71% of its surface."
                        };
                        break;
                    case 1: // Mars
                        worldInformation = new String[]{
                            "Mars is known as the Red Planet.",
                            "It has the largest volcano in the solar system, Olympus Mons.",
                            "Mars has two moons: Phobos and Deimos."
                        };
                        break;
                    case 2: // Venus
                        worldInformation = new String[]{
                            "Venus is the second planet from the Sun.",
                            "It is the hottest planet in the solar system.",
                            "A day on Venus is longer than a year on Venus."
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
                    sceneManager.set(new PlayScene(sceneManager, inputManager, outputManager));
                }
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
