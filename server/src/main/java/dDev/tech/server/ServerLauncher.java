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
import java.util.Iterator;
import java.util.Properties;

/** Launches the server application. */
public class ServerLauncher {
	public static void main(String[] args) {
		// TODO Implement server application.

		/*Connection conn =null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", "root");
		connectionProps.put("password", "dieguin01");
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/spacegame",connectionProps.getProperty("user"),connectionProps.getProperty("password"));


		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		if(conn!=null){
			System.out.println("Connected to database");
		}*/
		System.out.println("Server running");
		WebSocketServer server = new WebSocketServer(new InetSocketAddress("localhost",25001)) {

			@Override
			public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
				System.out.println("Status: "+conn.getReadyState());
				ServerHandshakeBuilder builder = super
						.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
				return builder;

			}

			@Override
			public void onOpen(WebSocket conn, ClientHandshake handshake) {
				System.out.println("Openned connection");

				System.out.println(handshake.getResourceDescriptor());
				for (Iterator<String> it = handshake.iterateHttpFields(); it.hasNext(); ) {
					String s = it.next();
					System.out.println(s+"-> "+handshake.getFieldValue(s));

				}

			}

			@Override
			public void onClose(WebSocket conn, int code, String reason, boolean remote) {

			}

			@Override
			public void onMessage(WebSocket conn, String message) {
				System.out.println("new messsage");
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

	}
}