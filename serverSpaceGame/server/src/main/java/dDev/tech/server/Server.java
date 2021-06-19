package dDev.tech.server;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server extends WebSocketServer{
    private Game game;
    public Server(){
        super(new InetSocketAddress("localhost",25001));

        game= new Game();


    }
    public void startGame(){
        Console.logInfo("Starting game");
        String json=loadSettings("serverSpaceGame/server/map/settingsMap.json");
        System.out.println(json);
        broadcast(json);
        game.start();
        Console.logInfo("Game started");
    }
    public String loadSettings(String path){
        System.out.println("Loading settings");
        JsonReader reader = new JsonReader();
        File f = new File(path);
        FileReader fReader= null;
        try {
            fReader = new FileReader(f);

            JsonValue value = reader.parse(fReader);
            fReader.close();

            return value.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error!";

    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        startGame();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }
}
