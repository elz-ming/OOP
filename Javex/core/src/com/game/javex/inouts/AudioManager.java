package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private Music menuMusic;
    private Music gameplayMusic;
    private Music gameoverMusic;

    public AudioManager() {
        // Load your music files
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("TitleScreen_Audio.mp3"));
        gameplayMusic = Gdx.audio.newMusic(Gdx.files.internal("Gameplay_Audio.mp3"));
        gameoverMusic = Gdx.audio.newMusic(Gdx.files.internal("Gameover_Audio.mp3"));
    }

    public void playMenuMusic() {
        stopAllMusic(); // Ensure no other music is playing
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    public void playGameplayMusic() {
        stopAllMusic();
        gameplayMusic.setLooping(true);
        gameplayMusic.play();
    }

    public void playGameoverMusic() {
        stopAllMusic();
        gameoverMusic.setLooping(false); // Typically, you don't loop game over music
        gameoverMusic.play();
    }

    public void pauseMusic() {
        // Pause all music, the current state will determine what was playing
        if(menuMusic.isPlaying()) menuMusic.pause();
        if(gameplayMusic.isPlaying()) gameplayMusic.pause();
        if(gameoverMusic.isPlaying()) gameoverMusic.pause();
    }

    public void stopAllMusic() {
        // Stop all music
        menuMusic.stop();
        gameplayMusic.stop();
        gameoverMusic.stop();
    }

    public void dispose() {
        // Dispose of music assets when no longer needed
        menuMusic.dispose();
        gameplayMusic.dispose();
        gameoverMusic.dispose();
    }
}
