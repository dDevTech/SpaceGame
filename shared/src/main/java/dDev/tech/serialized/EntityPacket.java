package dDev.tech.serialized;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class EntityPacket implements Transferable {
    public int ID;
    public Transferable packetData;
    public EntityPacket(){

    }
    public EntityPacket(int ID,Transferable packet){
        this.ID = ID;
        this.packetData = packet;
    }
    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeInt(ID).serializeTransferable(packetData);

    }

    @Override
    public Transferable deserialize(Deserializer deserializer) throws SerializationException {
        return new EntityPacket(deserializer.deserializeInt(), packetData.deserialize(deserializer));
    }

}
