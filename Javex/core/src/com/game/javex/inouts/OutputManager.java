package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class OutputManager implements Disposable {
    private Music music;    private float volume;
    private boolean muted;

    public OutputManager() {
        muted = false;
    }

    public void play(String musicPath, boolean isLooping) {
        if (music != null) {
            music.stop();
            music.dispose();
        }

        music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        music.setLooping(isLooping);
        music.play();
    }

    public void setVolume(float volume) {
    	this.volume = volume;
        if (music != null) {
            music.setVolume(volume);
        }
    }

    public float getVolume() {
        return volume;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
        if (music != null) {
            music.setVolume(muted ? 0 : 1); // Set volume to 0 if muted, 1 otherwise
        }
    }

    @Override
    public void dispose() {
        if (music != null) {
            music.dispose();
        }
    }
}
