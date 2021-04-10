package dDev.tech.map;

import com.badlogic.gdx.graphics.Color;

public class Tile {
    private  float x;
    private  float y;
    private  float size;
    private Color color;


    public Tile(float x, float y, float size, Color color){

        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public float getSize() {
        return size;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
