package dDev.tech.entities;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import dDev.tech.map.SpaceWorld;
import dDev.tech.serialized.PlayerPhysicData;

import dDev.tech.tools.PhysicFilters;
import dDev.tech.tools.PosInterpolator;
import dDev.tech.tools.Shaper;


import java.util.Map;

public class Player extends Entity{
    //------------------------------COMMON--------------------------------------
    public Shaper shaper;
    public float size =5f;

    public void createBody(SpaceWorld world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body  = world.world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(size/2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        Fixture fixture = body.createFixture(fixtureDef);
        Filter f = new Filter();
        f.categoryBits = PhysicFilters.CATEGORY_PLAYER;
        f.maskBits = (short) (PhysicFilters.CATEGORY_MAP|PhysicFilters.CATEGORY_PLAYER);
        fixture.setFilterData(f);
        circle.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(shaper==null)shaper= new Shaper(batch);
        if(isServer()){
            setX(body.getPosition().x);
            setY(body.getPosition().y);
        }else{
            if(mainPlayer){
                body.setAwake(true);
                body.setActive(true);
                world.camera.position.x = getX();
                world.camera.position.y = getY();
            }
            interpolator.updatePos(Gdx.graphics.getDeltaTime());
            Vector2 pos = interpolator.getPosition();
            setPhysicalPosition(pos.x,pos.y);
        }
        shaper.getShaper().setColor(new Color(0,109/255f,209/255f,1f));
        shaper.getShaper().filledPolygon(getX(),getY(),100,size/2,0);


    }
    public Player(){

    }
    @Override
    public void onDispose() {

    }
    //---------------------------------CLIENT---------------------------------------

    private boolean mainPlayer=true;
    public PosInterpolator interpolator;
    public int id = 0;


    @Override
    public void onCreateEntityInClient(SpaceWorld world) {
        this.world = world;
        interpolator = new PosInterpolator();
        createBody(world);

        PointLight light = new PointLight(world.rayHandler,256);
        light.attachToBody(body);
        light.setDistance(120f);
        light.setSoftnessLength(0f);
        light.setColor(new Color(0.2f,0.2f,0.2f,1f));
        light.setSoft(false);
        Filter f = new Filter();
        f.categoryBits = PhysicFilters.CATEGORY_LIGHT_PLAYER;
        f.maskBits = (short) (PhysicFilters.CATEGORY_MAP);
        light.setContactFilter(f);

    }

    @Override
    public void onPacketReceivedInClient(Transferable packet) {

    }

    //----------------------------------SERVER-----------------------------------------

    private SpaceWorld world;
    private JsonSerializer serialiazer;

    public boolean up ;
    public boolean down;
    public boolean left;
    public boolean right;


    public float resistantForce = 4f;
    public float motorForce = 40f;
    public float maxSpeed = 500f;
    public int xdir;
    public int ydir;


    @Override
    public void onCreateEntityInServer(Map<Integer,Entity> entities,SpaceWorld world) {
        createBody(world);
    }






    @Override
    public void onPacketReceivedInServer(Transferable packet) {

    }

    @Override
    public void onUpdateInServer(float deltaTime) {
        if(isCreated()){
            updateMotorMovement();
        }

    }

    @Override
    public Object[] onSendPacketToClients() {
        if(isCreated()){
            PlayerPhysicData location = new PlayerPhysicData();
            location.setId(id);
            location.setX(body.getPosition().x);
            location.setY(body.getPosition().y);
            return new Object[]{location};
        }
        return null;

    }

    public void selectPlayerMovement(Array<Boolean> movement){
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
