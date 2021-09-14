package dDev.tech.net;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.constants.Constants;

import dDev.tech.entities.Entity;
import dDev.tech.entities.Packets;
import dDev.tech.screens.SpaceGame;
import dDev.tech.serialized.*;

public class ServerConnection {

    private SpaceGame core;

    public WebSocket getSocket() {
        return socket;
    }

    public int getIdClient() {
        return idClient;
    }

    private int idClient = -1;
    public JsonSerializer getSerializer() {
        return serializer;
    }

    private WebSocket socket = null;
    private JsonSerializer serializer = new JsonSerializer();
    private ManualSerializer manual;
    public ServerConnection(SpaceGame core){
        manual= new ManualSerializer();
        Packets.register(manual);
        this.core = core;
        Gdx.app.log("NET","Creating connection");

        if(Gdx.app.getType()!= Application.ApplicationType.Android){
            socket= WebSockets.newSocket(WebSockets.toWebSocketUrl("localhost", Constants.PORT));
        }else{
            socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("10.0.2.2",Constants.PORT));
        }
        socket.addListener(new WebSocketListener() {
            @Override
            public boolean onOpen(WebSocket webSocket) {

                Gdx.app.log("NET","Connection created");
                return false;
            }

            @Override
            public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, String packet) {

                JsonReader read = new JsonReader();
                JsonValue base=read.parse(packet);
                String settings=base.get(0).name;

                System.out.println(settings);
                if(settings.equals("settings")){

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            core.setGameScreen();
                        }
                    });
                }




                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, byte[] packet) {
                //System.out.println("New message");

                Object o =manual.deserialize(packet);

                if(o instanceof EntityCreate){
                    EntityCreate entityCreate = (EntityCreate) o;
                    try {

                        Class<Entity> entityClass = ClassReflection.forName(entityCreate.getClassName());
                        Entity entity = ClassReflection.newInstance(entityClass);
                        entity.setID(entityCreate.getID());
                        entity.setOnClient();
                        Gdx.app.log("ENTITY","Received new instance request create: "+entityCreate.getClassName());

                        core.entities.put(entityCreate.getID(),entity);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                core.getEntityLayer().addActor(entity);
                            }
                        });
                        entity.onCreateEntityInClient(core.getSpaceWorld());

                    } catch (ReflectionException e) {
                        e.printStackTrace();
                    }
                }
                if(o instanceof EntityPacket){
                    Gdx.app.log("ENTITY","New object received");
                    EntityPacket entityPacket  = (EntityPacket) o;
                    core.onEntityMessage(entityPacket);

                }
               // System.out.println(o.getClass());
                if(o instanceof Locations){

                    core.updatePositions((Locations)o);

                }
                /*if(o instanceof PlayerData){
                    PlayerData data = (PlayerData) o;

                    PlayerTT player =new PlayerTT(core.getSpaceWorld(),data.getPosition().x,data.getPosition().y, data.getId(),false);
                    core.players.put(player.id,player);
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            core.getEntityLayer().addActor(player);
                        }
                    });
                }
                if(o instanceof PlayerID){
                    System.out.println("New player id registered");
                    PlayerID id = (PlayerID) o;
                    idClient = id.getId();
                }
                if(o instanceof PlayersData){
                    PlayerData[] data = ((PlayersData) o).getArray();
                    for(PlayerData playerData:data){
                        boolean lighting = false;
                        if(playerData.getId()==idClient){
                            lighting=true;
                        }
                        PlayerTT player =new PlayerTT(core.getSpaceWorld(),playerData.getPosition().x,playerData.getPosition().y, playerData.getId(),lighting);
                        core.players.put(player.id,player);
                        if(playerData.getId()==idClient){
                            player.setMainPlayer(core.cam);
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    core.getMainPlayerLayer().addActor(player);
                                }
                            });
                        }else{
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    core.getEntityLayer().addActor(player);
                                }
                            });
                        }



                    }
                }*/
                return true;

            }

            @Override
            public boolean onError(WebSocket webSocket, Throwable error) {
                Gdx.app.log("ERROR",error.getMessage());
                return false;
            }
        });

        socket.connect();


    }

    public void sendJson(boolean[] movement) {

        socket.send(serializer.serialize(movement));

    }
}
