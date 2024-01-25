package com.elz.mariobros.scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.elz.mariobros.*;

public class Hud implements Disposable{
	public Stage stage;
	private Viewport viewPort;
	
	private Integer worldTimer;
	private float timeCount;
	private Integer score;
	
	Label countdownLabel;
	Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label worldLabel;
	Label marioLabel;
	
	public Hud(SpriteBatch sb)
	{
		this.worldTimer = 300;
		this.timeCount = 0;
		this.score = 0;
		
		this.viewPort = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
		this.stage = new Stage(this.viewPort, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		this.countdownLabel = new Label(String.format("%03d", this.worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		this.scoreLabel = new Label(String.format("%06d", this.score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		this.timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		this.levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		this.worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		this.marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
	
		table.add(marioLabel).expandX().padTop(10);
		table.add(worldLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();

		stage.addActor(table);
	}

	@Override
	public void dispose() 
	{
		stage.dispose();
	}
	
}
