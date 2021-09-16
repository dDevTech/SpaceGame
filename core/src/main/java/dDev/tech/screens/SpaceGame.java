package dDev.tech.screens;

import com.badlogic.gdx.*;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.websocket.serialization.Transferable;
import com.kotcrab.vis.ui.VisUI;
import dDev.tech.entities.Entity;
import dDev.tech.entities.Player;
import dDev.tech.entities.SpaceCamera;
import dDev.tech.map.SpaceWorld;
import dDev.tech.net.ServerConnection;
import dDev.tech.serialized.EntityPacket;
import dDev.tech.serialized.PlayerPhysicData;
import dDev.tech.serialized.Locations;
import dDev.tech.ui.TextFont;

import java.util.*;


public class SpaceGame extends Game {

     GameScreen gameScreen;
     Menu menuScreen;
     TextFont fps;
    public Map<Integer, Player> players = new HashMap<>();
    public Player  mainPlayer;
    public Map<Integer, Entity> entities = new HashMap<>();
    public Stage getMapLayer() {
        return mapLayer;
    }

    Stage mapLayer;

    public Stage getEntityLayer() {
        return entityLayer;
    }
    Stage mainPlayerLayer;
    Stage entityLayer;
     Stage UIText;
     public SpaceCamera cam;
     SpriteBatch batch;

    public SpaceWorld getSpaceWorld() {
        return spaceWorld;
    }

    SpaceWorld spaceWorld;
     BitmapFont font;
     FitViewport viewport;
    public ServerConnection getConnection() {
        return connection;
    }

    ServerConnection connection;
    public void setGameScreen(){
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
    public void setMenuScreen(){
        menuScreen = new Menu(this);
        setScreen(menuScreen);
    }

    public Stage getMainPlayerLayer() {
        return mainPlayerLayer;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        VisUI.load(VisUI.SkinScale.X2);

        setMenuScreen();
        cam = new SpaceCamera();
        viewport=new FitViewport(160,90,cam);
        mapLayer = new Stage(viewport);
        entityLayer = new Stage(viewport);
        mainPlayerLayer = new Stage(viewport);
        UIText = new Stage();

        batch = new SpriteBatch();
        font = new BitmapFont();
        fps=new TextFont(font,"FPS",0.05f,0.05f);

        spaceWorld = new SpaceWorld(cam);
        spaceWorld.createViewWorld(cam,viewport);




        Gdx.app.log("CLIENT","Starting");
        connection = new ServerConnection(this);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

        spaceWorld.updateWorldViewport(viewport);
        UIText.getViewport().update(width, height);
        UIText.act();
    }

    @Override
    public void dispose() {
        mapLayer.dispose();

        font.dispose();
    }


    public void onEntityMessage(EntityPacket packet) {
        Entity entity = entities.get(packet.ID);
        if(entity!=null){
            Transferable data = packet.packetData;
            entity.onPacketReceivedInClient(data);
        }




    }
}