package dDev.tech.server;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import org.java_websocket.WebSocket;

public class Player {
    private WebSocket socket;
    private JsonSerializer serialiazer;
    BodyDef bodyDef = new BodyDef();
    Body body;
    CircleShape shape = new CircleShape();
    public Player(WebSocket socket, World world){
        this.socket = socket;
        serialiazer = new JsonSerializer();
        System.out.println("New player created");
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100, 300);
        body= world.createBody(bodyDef);

    }


    public void sendMessage(byte[]bytes){
        socket.send(bytes);
    }
    public void sendMessage(Object o){
        socket.send(serialiazer.serialize(o));
    }
    public void showMessage(String msg){
        System.out.println(msg);
    }


}
