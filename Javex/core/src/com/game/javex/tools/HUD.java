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
    private Label scoreLabel;
    private Label treasureChestsSolvedLabel;
    private Label timeLabel;
    private Skin skin;

    private long startTime;
	private long elapsedTime;

    public HUD() {
        skin = new Skin(Gdx.files.internal("rainbow-ui.json")); 

        
        Label.LabelStyle labelStyleWhite = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);
        Label.LabelStyle labelStyleRed = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);
        Label.LabelStyle labelStyleGreen = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);

        table = new Table();
        table.top();
        table.setFillParent(true);

       
        scoreLabel = new Label("Score: 0", labelStyleRed);
        treasureChestsSolvedLabel = new Label("Chests Solved: 0", labelStyleGreen);
        timeLabel = new Label("Time: 0", labelStyleWhite);

        table.add(scoreLabel).expandX().padTop(10).padBottom(10);
        table.add(treasureChestsSolvedLabel).expandX().padTop(10).padBottom(10);
        table.add(timeLabel).expandX().padTop(10).padBottom(10);
        
        startTime = TimeUtils.millis() + 300000;
    }

    public void update(int getScore, int treasureChestsSolved) {
        scoreLabel.setText("Score: " + getScore);
        treasureChestsSolvedLabel.setText("Chests Solved: " + treasureChestsSolved + "/3");
        long currentTime = TimeUtils.millis();
        elapsedTime = (startTime - currentTime) / 1000;
        timeLabel.setText("Time: " + elapsedTime);
    }


    public Table getTable() {
        return table;
    }

    public void resize(int width, int height) {
        table.invalidateHierarchy();
        table.setSize(width, height);
    }

    @Override
    public void dispose() {
        if (skin != null) {
            skin.dispose();
        }
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
