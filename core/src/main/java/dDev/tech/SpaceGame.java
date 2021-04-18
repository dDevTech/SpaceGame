package dDev.tech;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.websocket.AbstractWebSocketListener;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.data.WebSocketException;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import dDev.tech.entities.Player;
import dDev.tech.entities.SpaceCamera;
import dDev.tech.inputs.InputHandler;
import dDev.tech.map.Map;
import dDev.tech.net.ServerConnection;
import dDev.tech.serialized.SpaceLoader;
import dDev.tech.tools.Shaper;
import dDev.tech.ui.TextFont;
import sun.jvm.hotspot.gc.shared.Space;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SpaceGame extends ApplicationAdapter {
    private Stage game;
    private Stage UIText;
    private Map map;
    private SpaceCamera cam;
    private SpriteBatch batch;
    private Texture texture;

    private Player player;
    private InputHandler inputs;
    private BitmapFont font;
    private float speed=150f;
    TextFont fps;
    ServerConnection connection;
    public SpaceGame(){

    }

    @Override
    public void create() {
        connection = new ServerConnection();

        texture=  new Texture(Gdx.files.internal("Map2.png"));
        cam = new SpaceCamera();
        FitViewport viewport=new FitViewport(800,450,cam);
        game= new Stage(viewport);



        font = new BitmapFont();
        fps=new TextFont(font,"FPS",0.05f,0.05f);

        UIText = new Stage();
        UIText.setViewport(new ScreenViewport());
        fps.setPosition(-Gdx.graphics.getWidth()/4f,-Gdx.graphics.getHeight()/4f);
        fps.setScale(2f);

        UIText.addActor(fps);


        map = new Map("Map2.png",cam);
        game.addActor(map);

        player= new Player();
        player.setX(150);
        player.setY(150);
        player.setMainPlayer(cam);
        game.addActor(player);

        batch = new SpriteBatch();


        inputs = new InputHandler() {
            @Override
            public void onUpdate() {


            }
        };
        cam.position.x=0;
        cam.position.y=0;
        Gdx.input.setInputProcessor(inputs);

    }
    @Override
    public void render() {
        Gdx.gl.glClearColor( 0.2f, 0.2f, 0.2f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        if(inputs.isDown()&& !inputs.isUp()) cam.position.y-=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isUp()&& !inputs.isDown()) cam.position.y+=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isRight()&& !inputs.isLeft()) cam.position.x+=speed*Gdx.graphics.getDeltaTime();
        if(inputs.isLeft()&& !inputs.isRight()) cam.position.x-=speed*Gdx.graphics.getDeltaTime();
        game.getViewport().apply();
        game.draw();


        fps.updateText("FPS: "+Gdx.graphics.getFramesPerSecond());

        UIText.getViewport().apply();
        UIText.draw();
    }
    @Override
    public void resize(int width, int height) {

        game.getViewport().update(width, height);

        UIText.getViewport().update(width, height);
        UIText.act();


    }



    @Override
    public void dispose() {
        game.dispose();
        texture.dispose();
        font.dispose();

    }
}