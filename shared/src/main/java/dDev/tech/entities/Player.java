package dDev.tech.entities;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import dDev.tech.map.SpaceWorld;
import dDev.tech.tools.Shaper;

public class Player extends  Entity {
    private Shaper shaper;
    private float size =0.5f;
    private Camera camera;
    private boolean mainPlayer=false;
    Body body;
    public Player(SpaceWorld world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body  = world.world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(size/2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        Fixture fixture = body.createFixture(fixtureDef);
        fixtureDef.filter.categoryBits = (short)FilterLayer.PLAYER.ordinal();
        circle.dispose();

        PointLight light = new PointLight(world.rayHandler,256);
        light.attachToBody(body);
        light.setDistance(15f);
        light.setSoftnessLength(2f);
        light.setColor(new Color(1f,1f,1f,0.7f));
        light.setSoft(true);

    }
    public void setPhysicalPosition(float x,float y){
        super.setX(x);
        super.setY(y);
        body.setTransform(x,y,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(shaper==null)shaper= new Shaper(batch);
        if(mainPlayer){
            body.setTransform(camera.position.x,camera.position.y,0);
            body.setAwake(true);
            body.setActive(true);
            setX(camera.position.x);
            setY(camera.position.y);
        }
        shaper.getShaper().setColor(new Color(0,109/255f,209/255f,1f));
        
        shaper.getShaper().filledPolygon(getX(),getY(),100,size/2,0);
    }
    public void setMainPlayer(Camera camera){
        mainPlayer=true;
        this.camera = camera;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {

    }
}
