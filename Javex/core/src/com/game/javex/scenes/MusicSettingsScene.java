package com.game.javex.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.javex.inouts.InputManager;
import com.game.javex.inouts.OutputManager;

public class MusicSettingsScene extends AbstractScene {
    private Stage stage;
    private Skin skin;
    private Slider volumeSlider;
    private TextButton muteButton;
    private boolean isMuted = false;

    public MusicSettingsScene(SceneManager sceneManager, InputManager inputManager, OutputManager outputManager) {
        super(sceneManager, inputManager, outputManager);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("rainbow-ui.json")); // Use your own skin file

        // Volume Slider
        volumeSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeSlider.setValue(outputManager.getCurrentVolume()); // Set to current volume
        volumeSlider.setPosition(100, 200); // Adjust position as needed
        stage.addActor(volumeSlider);

        // Mute Button
        muteButton = new TextButton("Mute", skin);
        muteButton.setPosition(100, 150); // Adjust position as needed
        muteButton.addListener(event -> {
            if (isMuted) {
                outputManager.setVolume(volumeSlider.getValue());
                muteButton.setText("Mute");
            } else {
                outputManager.setVolume(0);
                muteButton.setText("Unmute");
            }
            isMuted = !isMuted;
            return true;
        });
        stage.addActor(muteButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!isMuted) {
            outputManager.setVolume(volumeSlider.getValue());
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    // Empty methods
    @Override public void show() {}
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

	@Override
	protected void update(float dt) {
		// TODO Auto-generated method stub
		
	}
}
