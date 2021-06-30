package dDev.tech.serialized;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;
import com.github.czyzby.websocket.serialization.impl.Size;

public class PlayerPhysicData implements Transferable<PlayerPhysicData> {
    public float x = 0f;
    public float y = 0f;
    public int id = 0;
    public PlayerPhysicData(){

    }
    public PlayerPhysicData(int id, float x, float y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PlayerPhysicData{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeInt(id, Size.SHORT).serializeFloat(x).serializeFloat(y);
    }

    @Override
    public PlayerPhysicData deserialize(Deserializer deserializer) throws SerializationException {
        return new PlayerPhysicData(deserializer.deserializeInt(Size.SHORT),deserializer.deserializeFloat(),deserializer.deserializeFloat());
    }
}
