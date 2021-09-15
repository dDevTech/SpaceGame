package dDev.tech.serialized;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class PlayerInput implements Transferable {
    public boolean W = false;
    public boolean A = false;
    public boolean S = false;
    public boolean D = false;

    public PlayerInput(boolean w, boolean a, boolean s, boolean d) {
        W = w;
        A = a;
        S = s;
        D = d;
    }
    public PlayerInput(){

    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeBoolean(W).serializeBoolean(A).serializeBoolean(S).serializeBoolean(D);
    }

    @Override
    public Transferable deserialize(Deserializer deserializer) throws SerializationException {
        return new PlayerInput(deserializer.deserializeBoolean(),deserializer.deserializeBoolean(),deserializer.deserializeBoolean(),deserializer.deserializeBoolean());
    }
}
