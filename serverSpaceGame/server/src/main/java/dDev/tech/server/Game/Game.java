package dDev.tech.server.Game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import dDev.tech.constants.Constants;
import dDev.tech.map.SpaceWorld;
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

    private  Map<WebSocket, ServerPlayer> players;
    public dDev.tech.map.Map map;
    public PhysicStepper physicSteps;
    SpaceWorld world;
    public Game(){
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
        player.setPhysicalPosition(2,2);
        players.put(conn,player);
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
