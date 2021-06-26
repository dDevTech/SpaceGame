package dDev.tech.server.ServerUtils;


import dDev.tech.server.ServerEntity.ServerPlayer;
import org.java_websocket.WebSocket;

public abstract class Callback {
    public Callback(){

    }
    public abstract void onAddPlayer(ServerPlayer player, WebSocket conn);
}
