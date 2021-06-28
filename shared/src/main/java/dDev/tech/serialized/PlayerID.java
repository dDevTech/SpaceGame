package dDev.tech.serialized;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class PlayerID implements Transferable<PlayerID> {
    private int id = 0;
    public PlayerID(int id){
        this.id=id;
    }
    public PlayerID(){}
    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeInt(id);
    }

    @Override
    public PlayerID deserialize(Deserializer deserializer) throws SerializationException {
        return new PlayerID(deserializer.deserializeInt());
    }

    public int getId() {
        return id;
    }
}
