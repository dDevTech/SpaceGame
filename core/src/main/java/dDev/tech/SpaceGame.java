package dDev.tech;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.AbstractWebSocketListener;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.data.WebSocketException;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import dDev.tech.serialized.SpaceLoader;
import sun.jvm.hotspot.gc.shared.Space;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SpaceGame extends ApplicationAdapter {
    public SpaceGame(){
        System.out.println("Creating connection");

    }

    @Override
    public void create() {
        super.create();
        WebSocket socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("localhost",25001));
        System.out.println("Created");
        Gdx.app.setLogLevel(Application.LOG_INFO);
        socket.addListener(new AbstractWebSocketListener() {
            @Override
            public boolean onOpen(WebSocket webSocket) {
                Gdx.app.log("INFO SERVER","Connected to server");
                webSocket.send("hola");
                Json json=new Json();
                json.setOutputType(JsonWriter.OutputType.json);
                System.out.println(json.toJson(new SpaceLoader(),Object.class));
                webSocket.send(new SpaceLoader(5));

                return false;
            }

            @Override
            protected boolean onMessage(WebSocket webSocket, Object packet) throws WebSocketException {
                return false;
            }
        });
        socket.setSerializer(new JsonSerializer());
        socket.connect();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }
}