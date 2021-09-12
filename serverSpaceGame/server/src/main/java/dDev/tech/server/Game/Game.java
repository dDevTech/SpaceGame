package dDev.tech.server.Game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.websocket.serialization.Transferable;
import dDev.tech.constants.Constants;
import dDev.tech.entities.Entity;
import dDev.tech.entities.Player;
import dDev.tech.map.SpaceWorld;
import dDev.tech.serialized.*;
import dDev.tech.server.ServerEntity.ServerMap;
import dDev.tech.server.ServerNet.Server;
import dDev.tech.server.ServerNet.ServerLauncher;

import dDev.tech.tools.IDGenerator;
import dDev.tech.tools.PhysicStepper;
import org.java_websocket.WebSocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Game extends Thread{
    private long step=1;

    private boolean running = true;
    public Stage mapLayer;
    public Stage entityLayer;
    public Map<Integer, WebSocket> getPlayers() {
        return players;
    }
    public Map<Integer, Entity>entities;
    public void setPlayers(Map<Integer, WebSocket> players) {
        this.players = players;
    }
    private Server server;
    private  Map<Integer, WebSocket> players;
    public dDev.tech.map.Map map;
    public PhysicStepper physicSteps;
    SpaceWorld world;
    int counter = 0;
    public Game(Server server){
        this.server= server;
        players = Collections.synchronizedMap(new HashMap<>());
        entities = Collections.synchronizedMap(new HashMap<>());
        world = new SpaceWorld();

        if(!ServerLauncher.USING_GRAPHICS){
            map = new ServerMap("serverSpaceGame/server/Map2.png",world,true);
        }
        physicSteps = new PhysicStepper();


    }

    /*public void addPlayerToGame(WebSocket conn){
        Player player = new Player(world);
        conn.send(server.manual.serialize(new PlayerID(player.id)));
        player.setPhysicalPosition(20+counter,20);
        players.put(conn,player);
        dDev.tech.entities.put(IDGenerator.assignID(),player);
        counter+=10;

        PlayerData[]array = new PlayerData[players.size()];
        int c=0;
        for(ServerPlayer playerID:players.values()){
            array[c]=new PlayerData(new Vector2(playerID.getX(),playerID.getY()),playerID.id);
            c++;
        }

        conn.send(server.manual.serialize(new PlayersData(array)));

        server.sendAllExcept(new PlayerData(new Vector2(player.getX(),player.getY()),player.id),players.get(conn));
        if(ServerLauncher.USING_GRAPHICS)ServerLauncher.callback.onAddPlayer(player,conn);
        Console.logInfo("Client added to game");
    }*/
    private float accumulator;
    public void updateWorld(){
        accumulator += world.deltaTime;
        while (accumulator >= Constants.TIME_PLAYER_FORCES) {
            for(Map.Entry<Integer, Entity> entry:entities.entrySet()){
                entry.getValue().onUpdateInServer(world.deltaTime);

            }
            accumulator -= Constants.TIME_PLAYER_FORCES;
        }

    }
    @Override
    public void run() {
        while(running){
            long initial=System.currentTimeMillis();
            try {
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateWorld();
            physicSteps.doPhysicsStep(world.deltaTime,world.world);
            world.deltaTime = (System.currentTimeMillis() - initial)/1000f;


        }

    }

    public SpaceWorld getWorld() {
        return world;
    }

    public void onEntityMessage(EntityPacket packet) {
        Entity entity = entities.get(packet.ID);
        Transferable data = packet.packetData;
        entity.onPacketReceivedInServer(data);

    }
    public void addPlayerToGame(WebSocket conn,Player player){
        int idEntity = IDGenerator.assignID();
        player.setID(idEntity);

        player.setOnServer();
        entities.put(idEntity,player);
        players.put(idEntity,conn);

        server.sendData(new EntityCreate(player.getID(),player.getClass().getName()));
        player.onCreateEntityInServer(server.game.entities, server.game.getWorld());
        if(ServerLauncher.USING_GRAPHICS) ServerLauncher.callback.onAddPlayer(player,conn);
    }
    public void addEntityToGame(Entity entity){
        int idEntity = IDGenerator.assignID();
        entity.setID(idEntity);

        entity.setOnServer();
        entities.put(idEntity,entity);
        server.sendData(new EntityCreate(entity.getID(),entity.getClass().getName()));
        entity.createEntityInServer(server.game.entities, server.game.getWorld());

    }


}
