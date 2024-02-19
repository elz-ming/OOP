package com.game.javex.scenes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.inouts.*;



public class EndScene extends Scene {
    private SpriteBatch sb;
    private Stage stage;
    private List<TextButton> buttons;
    private int currentButtonIndex = 0;
    
    
    private Image backgroundImage;
    
    

    public EndScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        sb = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        
        buttons = new ArrayList<>();
        
        Texture backgroundTexture = new Texture(Gdx.files.internal("endbackground.png"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        addButton("Back To Menu", 0.5f, 0.5f);
        updateButtonStyles();
    }

    @Override
    public void update(float dt) {
        stage.act(Gdx.graphics.getDeltaTime());
        handleInput();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        backgroundImage.draw(batch, 1);
        batch.end();
        
        stage.draw();
    }

    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();
    }

    private void addButton(String label, float customX, float customY) {
        Skin skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        TextButton button = new TextButton(label, skin);
        float buttonX = Gdx.graphics.getWidth() * customX - button.getWidth() * 0.5f;
        float buttonY = Gdx.graphics.getHeight() * customY - button.getHeight() * 0.5f - buttons.size() * button.getHeight() * 0.4f;
        button.setPosition(buttonX, buttonY);
        buttons.add(button);
        stage.addActor(button);
    }

    private void handleInput() {
        if (inputManager.getPrevKey() == com.badlogic.gdx.Input.Keys.UNKNOWN && inputManager.getCurrKey() == com.badlogic.gdx.Input.Keys.ENTER) {
            if (currentButtonIndex == 0) {
                sceneManager.set(new MenuScene(sceneManager, inputManager, outputManager));
            }
            inputManager.resetKeys();
        }
    }

    private void updateButtonStyles() {
        for (int i = 0; i < buttons.size(); i++) {
            if (i == currentButtonIndex) {
                buttons.get(i).getLabel().setColor(com.badlogic.gdx.graphics.Color.YELLOW);
            } else {
                buttons.get(i).getLabel().setColor(com.badlogic.gdx.graphics.Color.WHITE);
            }
        }
    }
}
