package dDev.tech.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import dDev.tech.tools.Shaper;

public class Player extends  Entity {
    private Shaper shaper;
    private float size =0.5f;
    private Camera camera;
    private boolean mainPlayer=false;
    public Player(){


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(shaper==null)shaper= new Shaper(batch);
        if(mainPlayer){
            setX(camera.position.x);
            setY(camera.position.y);
        }
        shaper.getShaper().setColor(new Color(0,109/255f,209/255f,1f));
        
        shaper.getShaper().filledPolygon(getX(),getY(),100,size/2,0);
    }
    public void setMainPlayer(Camera camera){
        mainPlayer=true;
        this.camera = camera;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {

    }
}
