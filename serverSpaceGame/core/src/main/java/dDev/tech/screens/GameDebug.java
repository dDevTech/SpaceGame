package dDev.tech.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dDev.tech.inputs.InputHandler;
import dDev.tech.map.Map;
import dDev.tech.tools.PhysicStepper;
import dDev.tech.server.ServerNet.ServerLauncher;

public class GameDebug implements Screen {

    private Map map;
    private ServerView main;
    private InputHandler inputs;


    private float speed=3f;
    private PhysicStepper physicSteps ;

    public GameDebug(ServerView main){


        this.main = main;
        inputs= new InputHandler() {
            @Override
            public void onUpdate() {

            }
        };
        physicSteps = new PhysicStepper();
        Gdx.input.setInputProcessor(inputs);
    }

    @Override
    public void show() {

        main.UIText.setViewport(new ScreenViewport());
        main.fps.setPosition(-Gdx.graphics.getWidth()/4f,-Gdx.graphics.getHeight()/4f);
        main.fps.setScale(2f);
        main.UIText.addActor( main.fps);

        map = new Map("Map2.png", main.cam,main.spaceWorld);
        main.mapLayer.addActor(map);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0.2f, 0.2f, 0.2f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        if(inputs.isDown()&& !inputs.isUp())  main.cam.position.y-=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isUp()&& !inputs.isDown())  main.cam.position.y+=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isRight()&& !inputs.isLeft())  main.cam.position.x+=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isLeft()&& !inputs.isRight())  main.cam.position.x-=speed*Gdx.graphics.getDeltaTime();

        main.mapLayer.getViewport().apply();

        main.spaceWorld.renderLights(main.cam);
        main.entityLayer.draw();
        main.mapLayer.draw();
        main.spaceWorld.debugRenderer.render(main.spaceWorld.world, main.mapLayer.getViewport().getCamera().combined);


        main.fps.updateText("FPS: "+Gdx.graphics.getFramesPerSecond());

        main.UIText.getViewport().apply();
        main.UIText.draw();

        ServerLauncher.game.updateWorld();
        main.spaceWorld.updatePhysics(Gdx.graphics.getDeltaTime());
        ServerLauncher.game.getWorld().deltaTime = Gdx.graphics.getDeltaTime();

    }
    @Override
    public void resize(int width, int height) {




    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        main.spaceWorld.dispose();

    }
}
