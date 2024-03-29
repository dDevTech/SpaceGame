package dDev.tech.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisProgressBar;
import dDev.tech.entities.FilterLayer;
import dDev.tech.tools.PhysicFilters;

public class Tile {
    private  float x;
    private  float y;
    private  float size;
    private float health = 0f;
    private float maxHealth = 0f;


    public Array<Vector2[]> getTriangles() {
        return triangles;
    }

    public void setTriangles(Array<Vector2[]> triangles) {
        this.triangles = triangles;
    }

    private Array<Vector2[]> triangles;

    public Array<Vector2[]> getLines() {
        return lines;
    }

    private Array<Vector2[]>lines;
    public TILE_TYPE getType() {
        return type;
    }

    public enum TILE_TYPE  {NONE, BLOCK};
    private TILE_TYPE type = TILE_TYPE.NONE;
    private Color color;

    private BodyDef groundBodyDef;


    public Tile(float x, float y, float size, Color color, TILE_TYPE type, SpaceWorld space, Camera camera){

        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.type = type;
        lines = new Array<>();
        triangles = new Array<>();
        if(type == TILE_TYPE.BLOCK){
            groundBodyDef = new BodyDef();
            groundBodyDef.position.set(new Vector2(x+size/2f, y+size/2f));

            Body groundBody = space.world.createBody(groundBodyDef);
            PolygonShape groundBox = new PolygonShape();

            groundBox.setAsBox(size/2f,size/2f);
            FixtureDef def = new FixtureDef();
            def.shape = groundBox;
            Fixture fixture =groundBody.createFixture(def);

            groundBox.dispose();
            Filter f = new Filter();
            f.categoryBits = PhysicFilters.CATEGORY_MAP;
            f.maskBits = (short) (PhysicFilters.CATEGORY_LIGHT_PLAYER | PhysicFilters.CATEGORY_PLAYER);
            fixture.setFilterData(f);

        }

    }

    public void addLine(Vector2 v1,Vector2 v2){
        lines.add(new Vector2[]{v1,v2});
    }
    public void addTriangle(Vector2 v1, Vector2 v2, Vector2 v3){
        triangles.add(new Vector2[]{v1,v2,v3});
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
