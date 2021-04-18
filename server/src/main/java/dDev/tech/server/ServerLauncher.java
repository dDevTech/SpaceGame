package dDev.tech.server;


import com.badlogic.gdx.utils.Json;
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

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/** Launches the server application. */
public class ServerLauncher {
	public static Map<WebSocket, Player> players;
	public static void main(String[] args) {

		players = Collections.synchronizedMap(new HashMap<>());
		Game game = new Game();
		game.start();
		System.out.println("Server starting");
		WebSocketServer server = new WebSocketServer(new InetSocketAddress("localhost",25001)) {

			@Override
			public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
				System.out.println("New attempting connection");
				ServerHandshakeBuilder builder = super
						.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
				return builder;

			}

			@Override
			public void onOpen(WebSocket conn, ClientHandshake handshake) {
				System.out.println("Openned connection");

				Player player = new Player(conn,game.world);
				players.put(conn,player);
				System.out.println(players);

			}

			@Override
			public void onClose(WebSocket conn, int code, String reason, boolean remote) {

			}

			@Override
			public void onMessage(WebSocket conn, String message) {
				if(players.containsKey(conn)){
					Player player =players.get(conn);
					player.showMessage(message);
				}
			}

			@Override
			public void onMessage(WebSocket conn, ByteBuffer message) {
				System.out.println("New object");
				Serializer s=new JsonSerializer();
				System.out.println("New object 2");
				System.out.println(message);
				Object o = null;
				try {
					String str=new String(message.array(), "UTF-8");
					System.out.println(str);
					System.out.println("New object kk");
					o = new Json().fromJson(null, str);
					System.out.println("New object kk2");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				System.out.println("New object 3");
				System.out.println(o.getClass());
				if(o instanceof SpaceLoader){
					System.out.println(((SpaceLoader)o).x);
				}

				Json json = new Json();
				String str=json.prettyPrint(o);
				System.out.println(str);

			}

			@Override
			public void onError(WebSocket conn, Exception ex) {

			}

			@Override
			public void onStart() {

			}
		};

		server.run();
		System.out.println("Server started");
	}
}