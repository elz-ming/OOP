package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class OutputManager implements Disposable {
    private Music music;
    private boolean isMuted;

    public OutputManager() {
        isMuted = false;
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
        if (music != null) {
            music.setVolume(volume);
        }
    }

    public float getCurrentVolume() {
        return (music != null) ? music.getVolume() : 0;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
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
