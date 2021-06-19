package dDev.tech.map;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

public class SpaceWorld{
    public  World world;
    public Box2DDebugRenderer debugRenderer;
    public RayHandler rayHandler;
    public   PointLight p2;
    public SpaceWorld(Camera camera,Viewport viewport){
        world = new World(new Vector2(0,0),true);

        debugRenderer = new Box2DDebugRenderer();

        float distance = 8;
        int rays = 32;
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0f);
        updateWorldViewport(viewport);

        rayHandler.setShadows(true);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(3);
        PointLight p1 =new PointLight(rayHandler, rays, Color.SKY, distance
                , (camera.viewportWidth / 4)
                , (camera.viewportHeight / 4) * 3 );

        p2 =new PointLight(rayHandler, rays, Color.PINK, distance
                , (camera.viewportWidth  / 4) * 3
                , (camera.viewportHeight  / 4 ) * 3);


        p2.setSoft(true);

       // p2.setSoftnessLength(100f);
        p1.setSoft(true);
       // p1.setSoftnessLength(100f);

    }
    public void attach(Body body){
        p2.setStaticLight(false);
        p2.attachToBody(body);
    }
    public void renderLight(OrthographicCamera camera){


        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();

    }
    public void updateWorldViewport(Viewport viewport){
        rayHandler.useCustomViewport(viewport.getRightGutterWidth(),viewport.getBottomGutterHeight(),viewport.getScreenWidth(),viewport.getScreenHeight());
    }
    public void updatePhysics(){
        world.step(1/60f,6,2);

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
