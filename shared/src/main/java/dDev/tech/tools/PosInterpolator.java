package dDev.tech.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import dDev.tech.constants.Constants;

public class PosInterpolator {
    private Vector2 interPrev = new Vector2(0,0);
    private Vector2 interPos = new Vector2(0,0);

    private Vector2 position = new Vector2();

    private float timeLerp = Constants.TIME_SENDS;
    private float currentTime = 0f;
    private float timeSends = timeLerp;
    private float newTime=timeLerp;
    private float beforeTime=0;
    public void setInitial(float x,float y){
        interPrev = new Vector2(x,y);
        interPos = new Vector2(x,y);
        currentTime = 0f;
    }
    public void newPoint(float x,float y){
        beforeTime = newTime;
        newTime = System.currentTimeMillis()/1000f;
        timeSends = newTime-beforeTime;

        if(timeSends ==0f) timeSends = timeLerp;

        interPrev = new Vector2(interPos.x,interPos.y);
        interPos = new Vector2(x,y);
        currentTime = 0f;


    }
    public void updatePos(float deltaTime){
        float alpha =currentTime/timeSends;


        position = new Vector2(interPrev.x,interPrev.y).lerp(interPos,alpha);
        currentTime+=deltaTime;

    }

    public Vector2 getInterPrev() {
        return interPrev;
    }

    public Vector2 getInterPos() {
        return interPos;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getCurrentTime() {
        return currentTime;
    }
}
