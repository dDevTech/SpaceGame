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
    private SpaceGame spaceGame;

    public Player player;
    private InputHandler inputs;

    private float speed=3f;


    public GameScreen(SpaceGame spaceGame){
        inputs = new InputHandler() {
            @Override
            public void onUpdate() {
                spaceGame.getConnection().sendJson(inputs.getMovement());
            }
        };
        Gdx.input.setInputProcessor(inputs);
        this.spaceGame = spaceGame;
    }

    @Override
    public void show() {
        spaceGame.fps.setPosition(-Gdx.graphics.getWidth()/4f,-Gdx.graphics.getHeight()/4f);
        spaceGame.fps.setScale(2f);

        spaceGame.UIText.setViewport(new ScreenViewport());
        spaceGame.UIText.addActor( spaceGame.fps);

        map = new Map("Map2.png", spaceGame.cam, spaceGame.spaceWorld);
        spaceGame.mapLayer.addActor(map);


    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0.2f, 0.2f, 0.2f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );



        spaceGame.mapLayer.getViewport().apply();

        spaceGame.entityLayer.draw();
        spaceGame.spaceWorld.renderLights(spaceGame.cam);

        spaceGame.mapLayer.draw();
        spaceGame.mapLayer.act(delta);
        spaceGame.mainPlayerLayer.draw();

      //  spaceGame.spaceWorld.debugRenderer.render(spaceGame.spaceWorld.world, spaceGame.mapLayer.getViewport().getCamera().combined);

        spaceGame.fps.updateText("FPS: "+Gdx.graphics.getFramesPerSecond());

        spaceGame.UIText.getViewport().apply();
        spaceGame.UIText.draw();
        spaceGame.spaceWorld.updatePhysics(Gdx.graphics.getDeltaTime());


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
        spaceGame.spaceWorld.dispose();

    }
}
