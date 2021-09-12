package dDev.tech.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.kotcrab.vis.ui.VisUI;
import dDev.tech.server.ServerUtils.Callback;
import dDev.tech.entities.Player;
import dDev.tech.entities.SpaceCamera;
import dDev.tech.map.SpaceWorld;
import dDev.tech.server.ServerNet.ServerLauncher;

import dDev.tech.ui.TextFont;
import org.java_websocket.WebSocket;


public class ServerView extends Game {

     GameDebug gameScreen;
     MenuDebug menuScreen;
     TextFont fps;

    public Stage getMapLayer() {
        return mapLayer;
    }

    Stage mapLayer;

    public Stage getEntityLayer() {
        return entityLayer;
    }

    Stage entityLayer;
     Stage UIText;
     SpaceCamera cam;
     SpriteBatch batch;
     SpaceWorld spaceWorld;
     BitmapFont font;
     FitViewport viewport;



    public void setGameScreen(){
        gameScreen = new GameDebug(this);
        setScreen(gameScreen);
    }
    public void setMenuScreen(){
        menuScreen = new MenuDebug(this);
        setScreen(menuScreen);
    }
    @Override
    public void create() {
        VisUI.load(VisUI.SkinScale.X2);
        ServerLauncher.USING_GRAPHICS = true;
        ServerLauncher.callback = new Callback() {
            @Override
            public void onAddPlayer(Player player, WebSocket conn) {
                entityLayer.addActor(player);
                System.out.println( ServerLauncher.game.getPlayers());
            }
        };

        ServerLauncher.start();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);


        cam = new SpaceCamera();
        viewport=new FitViewport(160,90,cam);
        mapLayer = new Stage(viewport);
        entityLayer = new Stage(viewport);
        UIText = new Stage();

        batch = new SpriteBatch();
        font = new BitmapFont();
        fps=new TextFont(font,"FPS",0.05f,0.05f);

        spaceWorld = ServerLauncher.game.getWorld();
        spaceWorld.createViewWorld(cam,viewport);
        setGameScreen();


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
}