package dDev.tech.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;



public class Shaper{
    Pixmap pixmap;
    Texture texture;
    TextureRegion region;

    public ShapeDrawer getShaper() {
        return shaper;
    }



    ShapeDrawer shaper;
    public Shaper(Batch batch){

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);
        shaper = new ShapeDrawer(batch,region);
    }
}
