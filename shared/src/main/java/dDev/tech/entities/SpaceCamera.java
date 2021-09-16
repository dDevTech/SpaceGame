package dDev.tech.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import dDev.tech.tools.PosInterpolator;


public class SpaceCamera extends OrthographicCamera{
    public PosInterpolator interpolator = new PosInterpolator();

    public SpaceCamera(){


    }
    public void update(float deltaTime){
        interpolator.updatePos(deltaTime);
    }
    public void move(float x,float y){
        interpolator.newPoint(x,y);
    }




}
