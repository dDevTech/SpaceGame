package dDev.tech.tools;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import dDev.tech.constants.Constants;

import java.util.ArrayDeque;

public class PosInterpolatorV2 {
    private Vector2 interPrev = new Vector2(0,0);
    private Vector2 interPos = new Vector2(0,0);
    private ArrayDeque<Vector2> nextPoints =  new ArrayDeque<>();
    private Vector2 position = new Vector2();

    private float timeLerp = Constants.TIME_SENDS;

    private float timePassed = 0f;
    private float pastTime = -1;
    private float nextTime = -1;
    private long newTime=(long)(Constants.TIME_SENDS*1000f);
    private long beforeTime=(long)(Constants.TIME_SENDS*1000f);
    private float timeSends = timeLerp;



    public void setInitial(float x,float y){
        interPrev = new Vector2(x,y);
        interPos = new Vector2(x,y);

    }
    public void newPoint(float x,float y){

        beforeTime = newTime;


        System.out.println(x+" "+y);
        newTime = System.currentTimeMillis();
        if(pastTime == -1){
            beforeTime= newTime;
        }
        timeSends = (newTime-beforeTime)/1000f;
        nextTime = timeSends;
        if(pastTime == -1){
            pastTime = nextTime;

        }


        if(timeSends ==0f) timeSends = timeLerp;
        System.out.println(timeSends);
        nextPoints.add(new Vector2(x,y));
    }
    private void goToNextPoint(){

        timePassed = 0;
        pastTime = nextTime;
        interPrev = new Vector2(interPos.x,interPos.y);

        Vector2 vector = null;
        if(nextPoints.size()==0){
            vector = new Vector2(interPos.x,interPos.y);

        }else{
            vector = nextPoints.pop();
        }
        interPos = new Vector2(vector.x,vector.y);



    }
    public void updatePos(float deltaTime){
        if(pastTime!=-1) {
            double alpha = timePassed / pastTime;

            position = new Vector2(interPrev.x, interPrev.y).lerp(interPos, (float) alpha);

            if (timePassed >= pastTime) {
                timePassed = pastTime;
                goToNextPoint();
            } else {
                timePassed += deltaTime;
            }
        }

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


}

