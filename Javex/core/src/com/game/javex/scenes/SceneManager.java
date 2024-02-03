package com.game.javex.scenes;

import com.badlogic.gdx.Game;

public class SceneManager extends Game {
    public enum SceneType {
        START_MENU,
        PLAYING,
        PAUSE,
        END_LEADERBOARD
    }

    @Override
    public void create() {
        setScreen(new StartMenuScene(this));
    }
 // Start with the Start Menu Scene
    

    public void changeScene(SceneType sceneType) {
        switch (sceneType) {
            case START_MENU:
                this.setScreen(new StartMenuScene(this));
                break;
            case PLAYING:
                this.setScreen(new PlayingScene(this));
                break;
            case PAUSE:
                this.setScreen(new PauseScene(this));
                break;
            case END_LEADERBOARD:
                this.setScreen(new EndLeaderboardScene(this));
                break;
        }
    }
}
