package dDev.tech.tools;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import dDev.tech.constants.Constants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class PosInterpolatorV2 {

    private ArrayList<KeyPoint> points =  new ArrayList<>();
    private KeyPoint prevKeyPoint;
    private KeyPoint nextKeyPoint;
    private Vector2 position = new Vector2(0,0);
    private float timePassed =0f;
    double alpha = 0f;


    public KeyPoint getPrevKeyPoint() {
        return prevKeyPoint;
    }

    public KeyPoint getNextKeyPoint() {
        return nextKeyPoint;
    }

    public void newPoint(float x, float y,int id){
        System.out.println("aa"+id);
        points.add(new KeyPoint(new Vector2(x,y)));
      //  System.out.println("New point: "+x+" "+y);
        if(points.size()>=2) {
            long delay = points.get(points.size() - 1).getTimeReceived() - points.get(points.size() - 2).getTimeReceived();
            float secondsDelay = delay/1000f;
            if (secondsDelay != 0f) {
                if(secondsDelay>2* Constants.TIME_SENDS){
                    System.err.println(">>>>OVERDUE: " + secondsDelay);
                }else if(secondsDelay< Constants.TIME_SENDS/2){
                    System.err.println("CONGESTION: " + secondsDelay);
                }else {
                    System.out.println("Delay: " + secondsDelay);
                }
                    points.get(points.size() - 2).setDelayWithNextKeyPoint(secondsDelay);

                    if (points.size() == 2) {
                        goToNextPoint();
                    }


            }else{
                System.err.println("CONGESTION 0 DELAY: " + secondsDelay);
            }


        }
        if(points.size()>2) {
            System.err.println("CONGESTION PACKETS: " + points.size());
        }

    }
    private void goToNextPoint(){
        if(points.size()>=2){
            prevKeyPoint = points.get(0);
            nextKeyPoint = points.get(1);
            points.remove(0);
            timePassed = 0;
            if(points.size()>2){
                System.err.println("TOO MANY PENDING PACKETS: " + points.size());
            }


        }

    }
    public void updatePos(float deltaTime){

        if(prevKeyPoint!=null){
            //if(points.size()>2){
            //    alpha =(timePassed / (prevKeyPoint.getDelayWithNextKeyPoint()*0.8f));
            //}else{
            //    alpha =timePassed / prevKeyPoint.getDelayWithNextKeyPoint();
            //}
            alpha =timePassed / prevKeyPoint.getDelayWithNextKeyPoint();

            if (timePassed >= prevKeyPoint.getDelayWithNextKeyPoint()) {
                alpha = 1f;
                goToNextPoint();
            } else {
                timePassed += deltaTime;
            }
            position = new Vector2(prevKeyPoint.getPosition().x, prevKeyPoint.getPosition().y).lerp(nextKeyPoint.getPosition(), (float) alpha);

        }

    }





    public Vector2 getPosition() {
        return position;
    }


}

