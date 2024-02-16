package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class OutputManager implements Disposable{
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

    @Override
    public void dispose() {
    	if (currMusic != null) {
            currMusic.dispose();
        }
    }
}
