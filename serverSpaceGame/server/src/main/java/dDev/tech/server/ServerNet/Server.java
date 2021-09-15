package dDev.tech.server.ServerNet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.constants.Constants;

import dDev.tech.entities.Entity;
import dDev.tech.entities.Packets;
import dDev.tech.entities.Player;
import dDev.tech.serialized.EntityCreate;
import dDev.tech.serialized.EntityPacket;

import dDev.tech.serialized.PlayerID;
import dDev.tech.serialized.PlayerInput;
import dDev.tech.server.ServerUtils.Console;
import dDev.tech.server.Game.Game;
import dDev.tech.server.Game.Sender;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

public class Server extends WebSocketServer{
    public Game game;
    private JsonSerializer serializer;
    int playersToStart = 2;
    public Sender sender;
    public ManualSerializer manual ;
    public Server(){
        super(new InetSocketAddress(Constants.PORT));
        serializer = new JsonSerializer();
        manual = new ManualSerializer();
        Packets.register(manual);
        game= new Game(this);


    }
    public void startGame(){
        Console.logInfo("Starting game");
        String json=loadSettings("serverSpaceGame/server/map/settingsMap.json");
        System.out.println(json);
        System.out.println(game.getPlayers());
        broadcast(json);

        if(!ServerLauncher.USING_GRAPHICS) {
            game.start();
        }
        sender = new Sender(this);
        sender.start();
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
    int x=25;
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        x=x+10;
        System.out.println("New client");
        Player player = new Player(new Vector2(x,25));
        int idPlayer  = game.addPlayerToGame(conn,player);
        conn.send(manual.serialize(new PlayerID(idPlayer)));
        for(Map.Entry<Integer,WebSocket>entry: game.getPlayers().entrySet()){
            conn.send(manual.serialize(new EntityCreate(entry.getKey(),player.getClass().getName())));
        }
        sendAllExcept(new EntityCreate(player.getID(),player.getClass().getName()),player);


        if(game.getPlayers().size()>=playersToStart){
            startGame();
        }

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {

        Object o = manual.deserialize(message.array());
        if(o instanceof EntityPacket){
            EntityPacket entityPacket  = (EntityPacket) o;
            game.onEntityMessage(entityPacket);

        }
        if(o instanceof PlayerInput){
            for(Map.Entry<Integer,WebSocket>entry:game.getPlayers().entrySet()){
                if(entry.getValue()==conn){
                    Entity entity = game.entities.get(entry.getKey());
                    if(entity instanceof Player){
                        ((Player)entity).onPacketReceivedInServer((Transferable) o);
                    }
                }

            }
        }
        /*Object o=serializer.deserialize(
                message.array());
        System.out.println(o.getClass());
        if(o instanceof Array){

            game.getPlayers().get(conn).selectPlayerMovement((Array<Boolean>)o);
        }*/
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }

    public void sendAllExcept(Object o, Player player){

        byte[]data=manual.serialize(o);
        System.out.println(o.getClass());
        for(Map.Entry<Integer,WebSocket>entry:game.getPlayers().entrySet()){
            if(player.getID() != entry.getKey()){
                entry.getValue().send(data);
            }

        }
        System.out.println("finished");

       // broadcast(manualSerializer.serialize(o));

    }
    public void sendData(Object o){
        byte[]data=manual.serialize(o);
        for(WebSocket socket:game.getPlayers().values()){
            socket.send(data);
        }
    }

}
