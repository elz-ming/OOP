package com.game.javex.inouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class OutputManager implements Disposable{
    private Music music;

    public void play(String musicPath, boolean isLooping) {
    	if (music != null) {
    		music.stop();
    		music.dispose(); // Free memory resources
        }
    	
    	music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
    	music.setLooping(isLooping);
    	music.play();
    }

    @Override
    public void dispose() {
    	if (music != null) {
            music.dispose();
        }
    }
}
