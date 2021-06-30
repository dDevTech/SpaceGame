package dDev.tech.server.Game;

import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.constants.Constants;
import dDev.tech.constants.Packets;
import dDev.tech.serialized.Locations;
import dDev.tech.serialized.PlayerPhysicData;
import dDev.tech.server.ServerEntity.ServerPlayer;
import dDev.tech.server.ServerNet.Server;
import org.java_websocket.WebSocket;

import java.util.Map;

public class Sender extends Thread{
    private long delay ;


    private Server server;
    private ManualSerializer serializer = new ManualSerializer();
    public Sender(Server server){
        Packets.register(serializer);

        this.server = server;
        delay=  (long)(Constants.TIME_SENDS*1000f);
    }

    @Override
    public void run() {

        while(true){

            PlayerPhysicData[]playerLocations = new PlayerPhysicData[server.game.getPlayers().size()];
            int count =0;
            for(Map.Entry<WebSocket, ServerPlayer> entry:server.game.getPlayers().entrySet()){
                int id = entry.getValue().id;
                PlayerPhysicData location = new PlayerPhysicData();
                location.setId(id);
                location.setX(entry.getValue().body.getPosition().x);
                location.setY(entry.getValue().body.getPosition().y);
                playerLocations[count] = location;
                count++;
            }
            byte[]bytes= serializer.serialize(new Locations(playerLocations));

            for(Map.Entry<WebSocket, ServerPlayer> entry:server.game.getPlayers().entrySet()){
                entry.getKey().send(bytes);
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
