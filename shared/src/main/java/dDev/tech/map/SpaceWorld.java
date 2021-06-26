package dDev.tech.map;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import dDev.tech.constants.Constants;
import dDev.tech.entities.FilterLayer;
import dDev.tech.tools.PhysicStepper;

import javax.swing.text.View;

public class SpaceWorld{
    public  World world;
    public Box2DDebugRenderer debugRenderer;
    public RayHandler rayHandler;
    public   PointLight p2;
    public float deltaTime=0;
    private PhysicStepper stepper;
    public SpaceWorld(){
        world = new World(new Vector2(0,0),true);
        stepper = new PhysicStepper();
    }
    public void createViewWorld(Camera camera,Viewport viewport){
        debugRenderer = new Box2DDebugRenderer();


        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0f);


        rayHandler.setShadows(true);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(3);

        updateWorldViewport(viewport);


        ///float distance = 8;
        //int rays = 256;
       /* PointLight p1 =new PointLight(rayHandler, rays, Color.SKY.mul(1f,1f,1f,0.8f), distance
                , (camera.viewportWidth / 4)
                , (camera.viewportHeight / 4) * 3 );

        p2 =new PointLight(rayHandler, rays,  Color.PINK.mul(1f,1f,1f,0.8f), distance
                , (camera.viewportWidth  / 4) * 3
                , (camera.viewportHeight  / 4 ) * 3);


        p2.setSoft(true);

        // p2.setSoftnessLength(100f);
        p1.setSoft(true);
        // p1.setSoftnessLength(100f);*/



    }

    public void attach(Body body){
        p2.setStaticLight(false);
        p2.attachToBody(body);
    }
    public void renderLights(OrthographicCamera camera){
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }
    /*
    Used for debug renderer of world and ray handler to correctly view the viewing are of the player (FitViewport)
     */
    public void updateWorldViewport(Viewport viewport){
        rayHandler.useCustomViewport(viewport.getRightGutterWidth(),viewport.getBottomGutterHeight(),viewport.getScreenWidth(),viewport.getScreenHeight());
    }
    private float accumulator;
    public void updatePhysics(float deltaTime){
        stepper.doPhysicsStep(deltaTime, world);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;

    }
    public void dispose(){
        rayHandler.dispose();
    }
}
