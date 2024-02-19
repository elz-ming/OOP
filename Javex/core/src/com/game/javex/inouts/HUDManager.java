package com.game.javex.inouts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUDManager implements Disposable {
    private Stage stage;
    private SpriteBatch batch;
    private Table table;
    private BitmapFont font;
    private Label enemiesKilledLabel;
    private Label coinsCollectedLabel;
    private Label timeLabel;

    private int enemiesKilled;
    private int coinsCollected;
    private long startTime;

    public HUDManager() {
        stage = new Stage(new ScreenViewport(), new SpriteBatch()); // The HUD will have its own SpriteBatch
        font = new BitmapFont(); // Use your preferred font
        
        table = new Table();
        table.top(); // Align the table to the top of the screen
        table.setFillParent(true); // Make the table fill the entire screen

        enemiesKilledLabel = new Label("Enemies Killed: 0", new Label.LabelStyle(font, Color.WHITE));
        coinsCollectedLabel = new Label("Coins Collected: 0", new Label.LabelStyle(font, Color.WHITE));
        timeLabel = new Label("Time: 0", new Label.LabelStyle(font, Color.WHITE));

        table.add(enemiesKilledLabel).expandX().padTop(10);
        table.add(coinsCollectedLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        stage.addActor(table); // Add the table to the stage
        startTime = TimeUtils.millis();
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
        font.dispose();
        // Make sure to dispose of the stage's own SpriteBatch
        ((SpriteBatch)stage.getBatch()).dispose();
    }
}
