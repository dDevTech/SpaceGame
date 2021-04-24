package dDev.tech.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import dDev.tech.screens.SpaceGame;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
	public static void main(String[] args) {
		createApplication();
	}

	private static LwjglApplication createApplication() {
		return new LwjglApplication(new SpaceGame(), getDefaultConfiguration());
	}

	private static LwjglApplicationConfiguration getDefaultConfiguration() {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		CommonWebSockets.initiate();
		configuration.title = "SpaceGame";
		configuration.width = 800;
		configuration.height = 450;
		configuration.samples =4;
		configuration.foregroundFPS=0;
		configuration.backgroundFPS=0;
		configuration.vSyncEnabled=false;

		return configuration;
	}
}