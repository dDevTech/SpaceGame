package dDev.tech.entities;

import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.serialized.*;

public class Packets {
    public static void register(ManualSerializer serializer){
        serializer.register(new EntityCreate());
        serializer.register(new EntityPacket());
        serializer.register(new PlayerPhysicData());
        serializer.register(new Locations());
        serializer.register(new PlayerData());
        serializer.register(new PlayerID());
        serializer.register(new PlayersData());
        serializer.register(new PlayerInput());

        System.out.println("Registered classes serialized");

    }
    public  static <T extends Transferable<T>> EntityPacket buildPackage(Entity entity, T packet){
        EntityPacket fullPacket = new EntityPacket(entity.getID(), packet);
        return fullPacket;

    }
}
