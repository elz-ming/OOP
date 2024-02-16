package com.game.javex;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.game.javex.scenes.SceneManager;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        
        config.setTitle("Javex"); // Set the title
        
        DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        config.setWindowedMode(800, 400); // Set width and height
        
        new Lwjgl3Application(new Javex(), config);
    }
}
