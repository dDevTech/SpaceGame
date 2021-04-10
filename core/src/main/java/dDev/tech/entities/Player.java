package dDev.tech.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import dDev.tech.tools.Shaper;

public class Player extends  Entity {
    private Shaper shaper;

    public Player(){


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(shaper==null)shaper= new Shaper(batch);
        shaper.getShaper().filledPolygon(getX(),getY(),400,20,0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {

    }
}
