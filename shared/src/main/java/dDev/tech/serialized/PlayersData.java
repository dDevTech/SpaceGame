package dDev.tech.serialized;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.websocket.serialization.ArrayProvider;
import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class PlayersData implements Transferable<PlayersData> {
    public PlayerData[] getArray() {
        return array;
    }

    PlayerData[] array ;
    public PlayersData(PlayerData[]data){
        array=data;
    }
    public PlayersData(){}

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeTransferableArray(array);
    }

    @Override
    public PlayersData deserialize(Deserializer deserializer) throws SerializationException {
        return new PlayersData(deserializer.deserializeTransferableArray(new PlayerData(), new ArrayProvider<PlayerData>() {
            @Override
            public PlayerData[] getArray(int size) {
                return new PlayerData[size];
            }
        }));
    }
}
