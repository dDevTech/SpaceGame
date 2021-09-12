package dDev.tech.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.github.czyzby.websocket.serialization.Serializer;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.map.SpaceWorld;


import java.util.Map;


public abstract class Entity extends Actor implements Disposable {

    public Body body;
    private boolean isInServer = true;
    private int ID = -1;
    private boolean created = false;




    public void createEntityInServer(Map<Integer,Entity> entities, SpaceWorld world){
        created=true;
        onCreateEntityInServer(entities,world);
    }
    public abstract void onDispose();
    public abstract void onCreateEntityInClient(SpaceWorld world);
    public abstract void onCreateEntityInServer(Map<Integer,Entity> entities, SpaceWorld world);
    public abstract void onPacketReceivedInClient(Transferable packet);
    public abstract void onPacketReceivedInServer(Transferable packet);
    public abstract void onUpdateInServer(float deltaTime);
    public abstract Object[] onSendPacketToClients();
    public void setPhysicalPosition(float x,float y){
        setX(x);
        setY(y);
        body.setTransform(x,y,0);
    }
    @Override
    public void dispose() {
        onDispose();
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public <T extends Transferable<T>> void buildPacket(T packetData){


    }
    public boolean isServer(){
        return isInServer;
    }
    public boolean isClient(){
        return !isInServer;
    }

    public void setOnServer() {
        isInServer = true;
    }
    public void setOnClient() {
        isInServer = false;
    }


    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}
