package dDev.tech;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.websocket.AbstractWebSocketListener;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.data.WebSocketException;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import dDev.tech.entities.Player;
import dDev.tech.entities.SpaceCamera;
import dDev.tech.map.Map;
import dDev.tech.serialized.SpaceLoader;
import dDev.tech.tools.Shaper;
import sun.jvm.hotspot.gc.shared.Space;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SpaceGame extends ApplicationAdapter {
    private Stage game;
    private Map map;
    private SpaceCamera cam;

    private Texture texture;
    private Shaper shaper;
    private Player player;

    public SpaceGame(){

    }

    @Override
    public void create() {

        texture=  new Texture(Gdx.files.internal("Map2.png"));
        cam = new SpaceCamera();

        game= new Stage(new FitViewport(800,600,cam));


        map = new Map("Map2.png");
        game.addActor(map);

        player= new Player();
        player.setX(150);
        player.setY(150);
        game.addActor(player);

    }
    @Override
    public void render() {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.draw();
    }
    @Override
    public void resize(int width, int height) {
        game.getViewport().update(width, height);
    }



    @Override
    public void dispose() {
        game.dispose();


    }
}