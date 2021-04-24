package dDev.tech.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import dDev.tech.screens.SpaceGame;

/** Launches the desktop (LWJGL3) application. */
public class
Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		CommonWebSockets.initiate();
		return new Lwjgl3Application(new SpaceGame(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {

		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("SpaceGame");
		configuration.setWindowedMode(800, 450);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		configuration.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4);
		return configuration;
	}
}