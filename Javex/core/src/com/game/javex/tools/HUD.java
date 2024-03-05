package com.game.javex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

public class HUD implements Disposable {
    private Table table;
    private Label enemiesKilledLabel;
    private Label coinsCollectedLabel;
    private Label timeLabel;
    private Skin skin;

    private long startTime;

    public HUD() {
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

        startTime = TimeUtils.millis();
    }

    public void update(int enemiesKilled, int coinsCollected) {
        enemiesKilledLabel.setText("Enemies Killed: " + enemiesKilled);
        coinsCollectedLabel.setText("Coins Collected: " + coinsCollected);

        long elapsedTime = TimeUtils.timeSinceMillis(startTime) / 1000;
        timeLabel.setText("Time: " + elapsedTime);
    }

    public Table getTable() {
        return table;
    }

    public void resize(int width, int height) {
        // Resize labels and table as needed
        table.invalidateHierarchy();
        table.setSize(width, height);
    }

    @Override
    public void dispose() {
        if (skin != null) {
            skin.dispose();
        }
    }
}
