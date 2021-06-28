package dDev.tech.serialized;


import com.github.czyzby.websocket.serialization.ArrayProvider;
import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class Locations implements Transferable {

    private PlayerPhysicData[]locations ;
    public Locations(PlayerPhysicData[]locations){
        this.locations = locations;
    }

    public Locations() {
    }

    public PlayerPhysicData[] getLocations() {
        return locations;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeTransferableArray(locations);

    }

    @Override
    public Transferable deserialize(Deserializer deserializer) throws SerializationException {
        return new Locations(deserializer.deserializeTransferableArray(new PlayerPhysicData(), new ArrayProvider<PlayerPhysicData>() {
            @Override
            public PlayerPhysicData[] getArray(int size) {
                return new PlayerPhysicData[size];
            }
        }));
    }
}
