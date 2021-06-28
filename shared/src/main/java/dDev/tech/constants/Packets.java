package dDev.tech.constants;

import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.serialized.*;

public class Packets {
    public static void register(ManualSerializer serializer){
        serializer.register(new PlayerPhysicData());
        serializer.register(new Locations());
        serializer.register(new PlayerData());
        serializer.register(new PlayerID());
        serializer.register(new PlayersData());
        System.out.println("Registered classes serialized");

    }
}
