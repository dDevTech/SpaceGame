package dDev.tech.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dDev.tech.entities.Player;
import dDev.tech.inputs.InputHandler;
import dDev.tech.map.Map;

public class GameScreen implements Screen {

    private Map map;
    private SpaceGame main;

    private Player player;
    private InputHandler inputs;

    private float speed=3f;


    public GameScreen(SpaceGame main){
        inputs = new InputHandler() {
            @Override
            public void onUpdate() {


            }
        };
        Gdx.input.setInputProcessor(inputs);
        this.main = main;
    }

    @Override
    public void show() {






        main.UIText.setViewport(new ScreenViewport());

        main.fps.setPosition(-Gdx.graphics.getWidth()/4f,-Gdx.graphics.getHeight()/4f);

        main.fps.setScale(2f);

        main.UIText.addActor( main.fps);


        map = new Map("Map2.png", main.cam,main.spaceWorld);
        main.game.addActor(map);

        player= new Player();
        player.setX(150);
        player.setY(150);
        player.setMainPlayer(main.cam);
        main.game.addActor(player);






       // main.cam.position.x=0;
       // main.cam.position.y=0;

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0.2f, 0.2f, 0.2f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        if(inputs.isDown()&& !inputs.isUp())  main.cam.position.y-=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isUp()&& !inputs.isDown())  main.cam.position.y+=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isRight()&& !inputs.isLeft())  main.cam.position.x+=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isLeft()&& !inputs.isRight())  main.cam.position.x-=speed*Gdx.graphics.getDeltaTime();


        main.game.getViewport().apply();

        main.game.draw();
        main.spaceWorld.renderLight(main.cam);
        main.spaceWorld.debugRenderer.render(main.spaceWorld.world, main.game.getViewport().getCamera().combined);


        //Gdx.app.log("RENDER",main.cam.position.toString());
        main.fps.updateText("FPS: "+Gdx.graphics.getFramesPerSecond());



        main.UIText.getViewport().apply();
        main.UIText.draw();
        main.spaceWorld.updatePhysics();


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
