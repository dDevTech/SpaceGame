package dDev.tech.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextFont extends Actor {
    private String text;
    private BitmapFont font;
    private int x=50;
    private int y=50;
    private float propX=1;
    private float propY=1;
    private boolean absolutePosition = true;
    public TextFont(BitmapFont font,String text,int x,int y){
        this.font = font;

        this.x=x;
        this.y=y;

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.text = text;
    }
    public TextFont(BitmapFont font,String text,float propX,float propY){
        this.font = font;
        this.propX = propX;
        this.propY = propY;
        absolutePosition = false;
        this.x=x;
        this.y=y;

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.text = text;
    }
    public void updateText(String text){
        this.text= text;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.setColor(Color.WHITE);
        font.getData().setScale(getScaleX());
        font.draw(batch,text,getX(),getY());
    }

    //On resize
    @Override
    public void act(float delta) {
        if(absolutePosition){
            setPosition(-Gdx.graphics.getWidth()/2f+x,-Gdx.graphics.getHeight()/2f+y);
        }else{
            setPosition(-Gdx.graphics.getWidth()/2f+propX*Gdx.graphics.getWidth(),-Gdx.graphics.getHeight()/2f+propY*Gdx.graphics.getHeight());
        }

    }
}
