package dDev.tech.server.Game;

import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.constants.Constants;
import dDev.tech.entities.Packets;
import dDev.tech.entities.Entity;

import dDev.tech.server.ServerNet.Server;

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

            for(Map.Entry<Integer, Entity> entry:server.game.entities.entrySet()){
                Object[]items = entry.getValue().onSendPacketToClients();
                if(items!=null){
                    for(Object o :items){
                        server.sendData(o);
                    }
                }

            }


            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
