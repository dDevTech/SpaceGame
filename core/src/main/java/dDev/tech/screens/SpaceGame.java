package dDev.tech.screens;

import com.badlogic.gdx.*;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dDev.tech.entities.Player;
import dDev.tech.entities.SpaceCamera;
import dDev.tech.map.SpaceWorld;
import dDev.tech.net.ServerConnection;
import dDev.tech.serialized.PlayerPhysicData;
import dDev.tech.serialized.Locations;
import dDev.tech.ui.TextFont;

import java.util.*;


public class SpaceGame extends Game {

     GameScreen gameScreen;
     Menu menuScreen;
     TextFont fps;
    public Map<Integer,Player> players = Collections.synchronizedMap(new HashMap<>());
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


        setMenuScreen();
        cam = new SpaceCamera();
        viewport=new FitViewport(16,9,cam);
        mapLayer = new Stage(viewport);
        entityLayer = new Stage(viewport);
        mainPlayerLayer = new Stage(viewport);
        UIText = new Stage();

        batch = new SpriteBatch();
        font = new BitmapFont();
        fps=new TextFont(font,"FPS",0.05f,0.05f);

        spaceWorld = new SpaceWorld();
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

    public void updatePositions(Locations locs) {
        for(PlayerPhysicData loc:locs.getLocations()){
            players.get(loc.id).interpolator.newPoint(loc.x,loc.y);
        }


    }
}