package com.lucentus.denkimon;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.lucentus.denkimon.Denkimon;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument

/**
 * Main Class for Desktop Launcher for Denkimon
 * Passes arguments for Desktop Window Configuration
 */
public class DesktopLauncher {

	/*
	 * Static Class Members
	 */
	public static final int WINDOW_HEIGHT = 1080;
	public static final int WINDOW_WIDTH = 1920;


	/*
	 * Methods
	 */

	/**
	 * Start of Program Logic for Desktop Launcher
	 * @param arg unused
	 */
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Denkimon");
		config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new Denkimon(), config);
	}
}
