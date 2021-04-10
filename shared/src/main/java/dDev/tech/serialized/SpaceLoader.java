package dDev.tech.serialized;


import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class SpaceLoader {
    public  int x=0;
    private String hi="tete";
    private Array array;
    public SpaceLoader(){

    }
    public SpaceLoader(int x){
        this.x=x;
        array = new Array();
        array.add(x);
        array.add(x);
        array.add(x);
        array.add(x);
    }


}
