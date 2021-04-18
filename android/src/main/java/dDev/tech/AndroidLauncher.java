package dDev.tech;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import dDev.tech.SpaceGame;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		CommonWebSockets.initiate();
		AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
		initialize(new SpaceGame(), configuration);
	}
}