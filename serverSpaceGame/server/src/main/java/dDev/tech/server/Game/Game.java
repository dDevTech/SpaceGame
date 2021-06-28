package dDev.tech.server.Game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import dDev.tech.constants.Constants;
import dDev.tech.map.SpaceWorld;
import dDev.tech.serialized.PlayerData;
import dDev.tech.serialized.PlayerID;
import dDev.tech.serialized.PlayersData;
import dDev.tech.server.ServerNet.Server;
import dDev.tech.server.ServerUtils.Console;
import dDev.tech.server.ServerNet.ServerLauncher;
import dDev.tech.server.ServerEntity.ServerPlayer;
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
    public Map<WebSocket, ServerPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(Map<WebSocket, ServerPlayer> players) {
        this.players = players;
    }
    private Server server;
    private  Map<WebSocket, ServerPlayer> players;
    public dDev.tech.map.Map map;
    public PhysicStepper physicSteps;
    SpaceWorld world;
    int counter = 0;
    public Game(Server server){
        this.server= server;
        players = Collections.synchronizedMap(new HashMap<>());
        world = new SpaceWorld();
        //TODO create map with file buffered image only created with graphics
        if(!ServerLauncher.USING_GRAPHICS){
            map = new dDev.tech.map.Map("serverSpaceGame/server/Map2.png",world,true);
        }
        physicSteps = new PhysicStepper();


    }
    public void addPlayerToGame(WebSocket conn){
        ServerPlayer player = new ServerPlayer(conn,world);
        conn.send(server.manual.serialize(new PlayerID(player.id)));
        player.setPhysicalPosition(2+counter,2);
        players.put(conn,player);
        counter++;

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
    }
    private float accumulator;
    public void updateWorld(){
        accumulator += world.deltaTime;
        while (accumulator >= Constants.TIME_PLAYER_FORCES) {
            for(Map.Entry<WebSocket, ServerPlayer> entry:players.entrySet()){
                entry.getValue().updateMotorMovement();

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
}
