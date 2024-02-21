package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUDManager implements Disposable {
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Label enemiesKilledLabel;
    private Label coinsCollectedLabel;
    private Label timeLabel;
    private Skin skin;

    private int enemiesKilled;
    private int coinsCollected;
    private long startTime;

    public HUDManager() {
    	 stage = new Stage(new ScreenViewport(), new SpriteBatch());
         skin = new Skin(Gdx.files.internal("rainbow-ui.json")); // Load the skin

         // Create custom label styles with your desired font color
         Label.LabelStyle labelStyleWhite = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);
         Label.LabelStyle labelStyleRed = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);
         Label.LabelStyle labelStyleGreen = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);

         table = new Table();
         table.top();
         table.setFillParent(true);

         // Create labels with the custom styles
         enemiesKilledLabel = new Label("Enemies Killed: 0", labelStyleRed);
         coinsCollectedLabel = new Label("Coins Collected: 0", labelStyleGreen);
         timeLabel = new Label("Time: 0", labelStyleWhite);

         table.add(enemiesKilledLabel).expandX().padTop(10).padBottom(10);
         table.add(coinsCollectedLabel).expandX().padTop(10).padBottom(10);
         table.add(timeLabel).expandX().padTop(10).padBottom(10);

         stage.addActor(table);
         startTime = TimeUtils.millis();
    }

    
    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public long getElapsedTime() {
        return TimeUtils.timeSinceMillis(startTime) / 1000;
    }
    
    public Label.LabelStyle getEnemiesKilledLabelStyle() {
        return enemiesKilledLabel.getStyle();
    }

    public Label.LabelStyle getCoinsCollectedLabelStyle() {
        return coinsCollectedLabel.getStyle();
    }

    public Label.LabelStyle getTimeLabelStyle() {
        return timeLabel.getStyle();
    }

    
    
    
    
    public void update(int enemiesKilled, int coinsCollected) {
        this.enemiesKilled = enemiesKilled;
        this.coinsCollected = coinsCollected;
        enemiesKilledLabel.setText("Enemies Killed: " + this.enemiesKilled);
        coinsCollectedLabel.setText("Coins Collected: " + this.coinsCollected);

        long elapsedTime = TimeUtils.timeSinceMillis(startTime) / 1000;
        timeLabel.setText("Time: " + elapsedTime);
    }

    public void draw() {
        stage.act(); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose(); // Dispose of the skin
        // Make sure to dispose of the stage's own SpriteBatch
        ((SpriteBatch)stage.getBatch()).dispose();
    }
    
    
  
}
