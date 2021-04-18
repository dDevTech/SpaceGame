package dDev.tech.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.java_websocket.WebSocket;

import java.util.Map;

public class Game extends Thread{
    private long step=20;
    private float deltaTime=0;
    private boolean running = true;
    World world;
    public Game(){

        world = new World(new Vector2(0, 0), true);

    }

    @Override
    public void run() {
        System.out.println("Server started");
        while(running){
            long initial=System.nanoTime();
            for(Map.Entry<WebSocket, Player> entry:ServerLauncher.players.entrySet()){
               // entry.getValue().getPlayerPhysics().updatePlayerPhysics(deltaTime);
            }
            try {
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

           // world.step(1/60f, 6, 2);
            deltaTime = System.nanoTime()-initial;
        }

    }

}
