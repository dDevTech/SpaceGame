package dDev.tech.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class KeyPoint {
    private Vector2 position;
    private long timeReceived = -1;
    private float delayWithNextKeyPoint = -1f;
    public KeyPoint(Vector2 position){
        this.timeReceived = TimeUtils.millis();

        this.position = position;

    }

    public float getDelayWithNextKeyPoint() {
        return delayWithNextKeyPoint;
    }

    public void setDelayWithNextKeyPoint(float delayWithNextKeyPoint) {
        this.delayWithNextKeyPoint = delayWithNextKeyPoint;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public long getTimeReceived() {
        return timeReceived;
    }


}
