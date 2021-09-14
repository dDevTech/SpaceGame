package dDev.tech.serialized;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class EntityPacket implements Transferable {
    public int ID;
    public Transferable packetData;
    public String className;
    public EntityPacket(){

    }
    public EntityPacket(int ID,Transferable packet){
        this.className = packet.getClass().getName();
        this.ID = ID;
        this.packetData = packet;
    }
    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeInt(ID).serializeString(className).serializeTransferable(packetData);

    }

    @Override
    public Transferable deserialize(Deserializer deserializer) throws SerializationException {
        int id = deserializer.deserializeInt();
        String className = deserializer.deserializeString();

        try {
            Class<Transferable> classTransferable = ClassReflection.forName(className);
            return new EntityPacket(id, deserializer.deserializeTransferable(ClassReflection.newInstance(classTransferable)));
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        Gdx.app.log("ERROR","Error deserializing ClassName: "+className);
        return null;
    }

}
