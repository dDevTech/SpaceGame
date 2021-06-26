package dDev.tech.server.Game;

import com.badlogic.gdx.math.Vector2;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import dDev.tech.constants.Constants;
import dDev.tech.server.ServerEntity.ServerPlayer;
import org.java_websocket.WebSocket;

import java.util.Map;

public class Sender extends Thread{
    private int delay =  (int)(Constants.TIME_SENDS*1000f);
    private Game game;

    public Sender(Game game){

        this.game = game;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){

            Vector2[]playerLocal = new Vector2[game.getPlayers().size()*2];
            for(Map.Entry<WebSocket, ServerPlayer> entry:game.getPlayers().entrySet()){
                int id = entry.getValue().id;
                playerLocal[id*2] = entry.getValue().body.getPosition();
                playerLocal[id*2+1] = entry.getValue().body.getLinearVelocity();
            }
            for(Map.Entry<WebSocket, ServerPlayer> entry:game.getPlayers().entrySet()){
                entry.getKey().send(new JsonSerializer().serializeAsString(playerLocal));
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
