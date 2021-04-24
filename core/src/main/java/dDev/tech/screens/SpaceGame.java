package dDev.tech.screens;

import com.badlogic.gdx.*;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dDev.tech.entities.SpaceCamera;
import dDev.tech.net.ServerConnection;
import dDev.tech.screens.GameScreen;
import dDev.tech.screens.Menu;
import dDev.tech.ui.TextFont;


public class SpaceGame extends Game {

     GameScreen gameScreen;
     Menu menuScreen;
    TextFont fps;
     Stage game;
     Stage UIText;
     SpaceCamera cam;
     SpriteBatch batch;

     BitmapFont font;

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
    @Override
    public void create() {
        connection = new ServerConnection(this);

        setMenuScreen();
        cam = new SpaceCamera();
        FitViewport viewport=new FitViewport(800,450,cam);
        game= new Stage(viewport);
        UIText = new Stage();

        batch = new SpriteBatch();
        font = new BitmapFont();
        fps=new TextFont(font,"FPS",0.05f,0.05f);
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

        font.dispose();
    }
}