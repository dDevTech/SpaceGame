package dDev.tech.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.java_websocket.WebSocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Game extends Thread{
    private long step=20;
    private float deltaTime=0;
    private boolean running = true;
    public  Map<WebSocket, Player> players;

    World world;
    public Game(){
        players = Collections.synchronizedMap(new HashMap<>());
        world = new World(new Vector2(0, 0), true);

    }

    @Override
    public void run() {

        while(running){
            long initial=System.nanoTime();
            for(Map.Entry<WebSocket, Player> entry:players.entrySet()){
               // entry.getValue().getPlayerPhysics().updatePlayerPhysics(deltaTime);
            }
            try {
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            world.step(1/60f, 6, 2);
            deltaTime = System.nanoTime()-initial;
        }

    }

}
