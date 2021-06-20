package dDev.tech.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dDev.tech.entities.SpaceCamera;
import dDev.tech.map.SpaceWorld;
import dDev.tech.server.Server;
import dDev.tech.server.ServerLauncher;
import dDev.tech.ui.TextFont;


public class ServerView extends Game {

     GameDebug gameScreen;
     MenuDebug menuScreen;
     TextFont fps;
     Stage mapLayer;
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
        Gdx.app.setLogLevel(Application.LOG_DEBUG);


        cam = new SpaceCamera();
        viewport=new FitViewport(16,9,cam);
        mapLayer = new Stage(viewport);
        entityLayer = new Stage(viewport);
        UIText = new Stage();

        batch = new SpriteBatch();
        font = new BitmapFont();
        fps=new TextFont(font,"FPS",0.05f,0.05f);

        spaceWorld = new SpaceWorld(cam,viewport);
        setGameScreen();
        ServerLauncher.start();

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