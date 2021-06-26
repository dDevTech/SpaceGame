package dDev.tech.entities;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dDev.tech.map.SpaceWorld;
import dDev.tech.tools.PosInterpolator;
import dDev.tech.tools.Shaper;

public class Player extends  Entity {
    public Shaper shaper;
    public float size =0.5f;
    private Camera camera;
    private boolean mainPlayer=false;
    public Body body;
    public int xdir;
    public int ydir;



    public PosInterpolator interpolator;
    public Player(SpaceWorld world,boolean lighting){

        this(world);
        PointLight light = new PointLight(world.rayHandler,256);
        light.attachToBody(body);
        light.setDistance(15f);
        light.setSoftnessLength(2f);
        light.setColor(new Color(1f,1f,1f,0.7f));
        light.setSoft(true);
        interpolator = new PosInterpolator();

    }
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
        // fixtureDef.filter.categoryBits = (short)FilterLayer.PLAYER.ordinal();
        circle.dispose();
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
            body.setAwake(true);
            body.setActive(true);
        }
        camera.position.x = getX();
        camera.position.y = getY();

        interpolator.updatePos(Gdx.graphics.getDeltaTime());

        Vector2 pos = interpolator.getPosition();

        setPhysicalPosition(pos.x,pos.y);
        shaper.getShaper().setColor(new Color(0,109/255f,209/255f,1f));
        shaper.getShaper().filledPolygon(getX(),getY(),100,size/2,0);
        shaper.getShaper().setColor(new Color(1f,0f,0f,1f));
        shaper.getShaper().filledPolygon(interpolator.getInterPos().x,interpolator.getInterPos().y,100,size/5,0);
        shaper.getShaper().setColor(new Color(0f,1f,0f,1f));
        shaper.getShaper().filledPolygon(interpolator.getInterPrev().x,interpolator.getInterPrev().y,100,size/5,0);
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
