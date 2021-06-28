package dDev.tech.server.ServerEntity;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;
import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;

import com.github.czyzby.websocket.serialization.impl.Serializer;
import dDev.tech.entities.Player;
import dDev.tech.map.SpaceWorld;
import dDev.tech.tools.Shaper;
import org.java_websocket.WebSocket;

public class ServerPlayer extends Player{
    private WebSocket socket;
    private SpaceWorld world;
    private JsonSerializer serialiazer;

    public boolean up ;
    public boolean down;
    public boolean left;
    public boolean right;
    public static int idIncrement = 0;



    public float resistantForce = 0.4f;
    public float motorForce = 4f;
    public float maxSpeed = 50f;

    public ServerPlayer(WebSocket socket, SpaceWorld world){
        super(world);
        this.socket = socket;
        this.world = world;
        serialiazer = new JsonSerializer();

        id = idIncrement;
        idIncrement++;

    }
    public void selectPlayerMovement(Array<Boolean>movement){
        int xdir = 0;
        int ydir=0;
        System.out.println(movement);
        if(movement.get(0)==true&&movement.get(2)==false){
            ydir=1;
        }else if(movement.get(0)==false&&movement.get(2)==true){
            ydir=-1;
        }
        if(movement.get(1)==true&&movement.get(3)==false){
            xdir=-1;
        }else if(movement.get(1)==false&&movement.get(3)==true){
            xdir=1;
        }
        updateMovement(xdir,ydir);

    }
    public ServerPlayer(){

    }
    private ServerPlayer(float x,float y,int id){
        setX(x);
        setY(y);
        this.id = id;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(shaper==null)shaper= new Shaper(batch);

        setX(body.getPosition().x);
        setY(body.getPosition().y);
        shaper.getShaper().setColor(new Color(0,109/255f,209/255f,1f));

        shaper.getShaper().filledPolygon(getX(),getY(),100,size/2,0);

       //updateMotorMovement();


    }
    public void updateMovement(int xdir,int ydir){
        this.xdir = xdir;
        this.ydir = ydir;

    }

    public void updateMotorMovement(){
       //System.out.println(accumulatedTime+" "+body.getLinearVelocity());
        if (Math.abs(body.getLinearVelocity().x) >= maxSpeed) {

            if (body.getLinearVelocity().x >= 0 && xdir < 0) {
                body.applyForceToCenter(new Vector2(motorForce * xdir, 0), true);
            } else if (body.getLinearVelocity().x <= 0 && xdir > 0) {
                body.applyForceToCenter(new Vector2(motorForce * xdir, 0), true);
            }
        } else {
            body.applyForceToCenter(new Vector2(motorForce * xdir, 0), true);
        }
        if (Math.abs(body.getLinearVelocity().y) >= maxSpeed) {
            if (body.getLinearVelocity().y >= 0 && ydir < 0) {
                body.applyForceToCenter(new Vector2(0, motorForce * ydir), true);
            } else if (body.getLinearVelocity().y <= 0 && ydir > 0) {
                body.applyForceToCenter(new Vector2(0, motorForce * ydir), true);
            }
        } else {
            body.applyForceToCenter(new Vector2(0, motorForce * ydir), true);
        }

        if (body.getLinearVelocity().x > 0 && xdir == 0) {
            body.applyForceToCenter(new Vector2(-resistantForce, 0), true);
        } else if (body.getLinearVelocity().x < 0 && xdir == 0) {
            body.applyForceToCenter(new Vector2(resistantForce, 0), true);
        }
        if (body.getLinearVelocity().y > 0 && ydir == 0) {
            body.applyForceToCenter(new Vector2(0, -resistantForce), true);
        } else if (body.getLinearVelocity().y < 0 && ydir == 0) {
            body.applyForceToCenter(new Vector2(0, resistantForce), true);
        }


    }
}
