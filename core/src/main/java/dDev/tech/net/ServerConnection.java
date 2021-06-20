package dDev.tech.net;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import dDev.tech.screens.SpaceGame;

public class ServerConnection {

    private SpaceGame core;

    public ServerConnection(SpaceGame core){
        this.core = core;
        Gdx.app.log("NET","Creating connection");
        WebSocket socket = null;
        if(Gdx.app.getType()!= Application.ApplicationType.Android){
            socket= WebSockets.newSocket(WebSockets.toWebSocketUrl("localhost",25001));
        }else{
            socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("10.0.2.2",25001));
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
}
