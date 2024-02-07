package com.game.javex;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.game.javex.scenes.SceneManager;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 480); // Set width and height
        config.setTitle("Javex"); // Set the title

        new Lwjgl3Application(new Javex(), config);
    }
}
