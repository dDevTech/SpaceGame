package dDev.tech.server;


import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.czyzby.websocket.serialization.Serializer;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import dDev.tech.serialized.SpaceLoader;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/** Launches the server application. */
public class ServerLauncher {
	private static Game game;

	public static void main(String[] args) {

		Console.logInfo("Server starting");
		Server server = new Server();
		server.start();
		Console.logInfo("Server started");

		Console console = new Console();
		Console.addCommand("s",(String[]arg)->{

			server.startGame();
		});
		console.start();





	}
}