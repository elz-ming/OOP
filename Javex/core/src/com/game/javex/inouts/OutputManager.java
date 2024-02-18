package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class OutputManager implements Disposable {
    private Music currMusic;

    public void playMusic(String musicPath, boolean isLooping) {
        if (currMusic != null) {
            currMusic.stop();
            currMusic.dispose(); // Free memory resources
        }
        
        currMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        currMusic.setLooping(isLooping);
        currMusic.play();
    }

    public void setVolume(float volume) {
        if (currMusic != null) {
            currMusic.setVolume(volume);
        }
    }

    public float getCurrentVolume() {
        return (currMusic != null) ? currMusic.getVolume() : 0;
    }

    @Override
    public void dispose() {
        if (currMusic != null) {
            currMusic.dispose();
        }
    }
}
