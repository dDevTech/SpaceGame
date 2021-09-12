package dDev.tech.server.ServerUtils;



import dDev.tech.entities.Player;
import org.java_websocket.WebSocket;

public abstract class Callback {
    public Callback(){

    }
    public abstract void onAddPlayer(Player player, WebSocket conn);
}
