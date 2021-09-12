package dDev.tech.serialized;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class EntityCreate implements Transferable {
    private int ID = -1;
    private String className;
    public EntityCreate(int id,String className){
        this.className = className;
        this.ID = id;
    }
    public EntityCreate(){}


    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeInt(ID).serializeString(className);
    }

    @Override
    public Transferable deserialize(Deserializer deserializer) throws SerializationException {
        return new EntityCreate(deserializer.deserializeInt(),deserializer.deserializeString());
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
