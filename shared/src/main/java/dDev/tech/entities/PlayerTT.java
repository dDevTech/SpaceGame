package dDev.tech.entities;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dDev.tech.map.SpaceWorld;
import dDev.tech.tools.PhysicFilters;
import dDev.tech.tools.PosInterpolator;
import dDev.tech.tools.Shaper;

public abstract class PlayerTT extends  Entity{
    public Shaper shaper;
    public float size =5f;
    private Camera camera;
    private boolean mainPlayer=false;

    public int xdir;
    public int ydir;
    public int id = 0;


    public PosInterpolator interpolator;
    public PlayerTT(SpaceWorld world, boolean lighting){

        this(world);
        if(lighting){
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




    }
    public PlayerTT(SpaceWorld world){
        interpolator = new PosInterpolator();
        createBody(world);
    }
    public PlayerTT(){

    }
    public PlayerTT(SpaceWorld world, float x, float y, int id, boolean lighting){
        this(world,lighting);
        setPhysicalPosition(x,y);
        this.id = id;
    }
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
        if(shaper==null) shaper= new Shaper(batch);
        if(mainPlayer){
            body.setAwake(true);
            body.setActive(true);
            camera.position.x = getX();
            camera.position.y = getY();
        }


        interpolator.updatePos(Gdx.graphics.getDeltaTime());

        Vector2 pos = interpolator.getPosition();

        setPhysicalPosition(pos.x,pos.y);
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
    public void onDispose() {

    }
}
