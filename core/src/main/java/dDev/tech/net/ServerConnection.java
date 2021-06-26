package dDev.tech.net;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import dDev.tech.constants.Constants;
import dDev.tech.screens.SpaceGame;

public class ServerConnection {

    private SpaceGame core;

    public WebSocket getSocket() {
        return socket;
    }

    public JsonSerializer getSerializer() {
        return serializer;
    }

    private WebSocket socket = null;
    private JsonSerializer serializer = new JsonSerializer();
    public ServerConnection(SpaceGame core){
        this.core = core;
        Gdx.app.log("NET","Creating connection");

        if(Gdx.app.getType()!= Application.ApplicationType.Android){
            socket= WebSockets.newSocket(WebSockets.toWebSocketUrl("localhost", Constants.PORT));
        }else{
            socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("10.0.2.2",Constants.PORT));
        }
        socket.addListener(new WebSocketListener() {
            @Override
            public boolean onOpen(WebSocket webSocket) {

                Gdx.app.log("NET","Connection created");
                return false;
            }

            @Override
            public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, String packet) {
                //  System.out.println("NEW MESSAGE");
                //  System.out.println(packet);

                Object o=new JsonSerializer().deserialize(packet);

                if(o instanceof Array){

                    Array<JsonValue>arr=(Array<JsonValue>)o;
                    core.updatePositions(arr);

                }else{
                    JsonReader read = new JsonReader();
                    JsonValue base=read.parse(packet);
                    String settings=base.get(0).name;

                    System.out.println(settings);
                    if(settings.equals("settings")){

                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                core.setGameScreen();
                            }
                        });
                    }
                }



                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, byte[] packet) {
                return false;
            }

            @Override
            public boolean onError(WebSocket webSocket, Throwable error) {
                Gdx.app.log("ERROR",error.getMessage());
                return false;
            }
        });
        socket.connect();


    }

    public void sendJson(boolean[] movement) {

        socket.send(serializer.serialize(movement));

    }
}
