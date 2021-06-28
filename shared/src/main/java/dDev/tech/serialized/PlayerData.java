package dDev.tech.serialized;

import com.badlogic.gdx.math.Vector2;
import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class PlayerData implements Transferable<PlayerData> {
    private Vector2 position;
    private int id = -1;

    public PlayerData(Vector2 position,int id){
        this.id = id;
        this.position = position;
    }
    public PlayerData(){}


    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeInt(id).serializeFloat(position.x).serializeFloat(position.y);
    }

    @Override
    public PlayerData deserialize(Deserializer deserializer) throws SerializationException {
        int id = deserializer.deserializeInt();
        return new PlayerData(new Vector2(deserializer.deserializeFloat(),deserializer.deserializeFloat()),id);
    }
}
