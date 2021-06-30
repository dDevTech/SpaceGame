package dDev.tech.serialized;


import com.github.czyzby.websocket.serialization.ArrayProvider;
import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

import java.util.Arrays;

public class Locations implements Transferable {

    private PlayerPhysicData[]locations ;

    public Locations() {
    }

    public Locations(PlayerPhysicData[]locations){
        this.locations = locations;
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

    @Override
    public String toString() {
        return "Locations{" +
                "locations=" + Arrays.toString(locations) +
                '}';
    }
}
