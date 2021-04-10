package dDev.tech.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import dDev.tech.tools.Shaper;

public class Map extends Actor implements Disposable {

    private Pixmap mapImage;
    private Color[][]map;
    private Shaper shaper;
    private int tileSize =50;
    public Map(String imagePath){
        super();
        Gdx.app.log("MAP","Loading map...");
        mapImage = new Pixmap(Gdx.files.internal(imagePath));
        map = new Color[mapImage.getHeight()][mapImage.getWidth()];
        for(int i=0;i<mapImage.getHeight();i++){
            for(int j=0;j<mapImage.getWidth();j++){
                map[i][j]=new Color(mapImage.getPixel(i,j));
            }
        }


        Gdx.app.log("MAP","Map loaded");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(shaper==null)shaper= new Shaper(batch);

        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j].toIntBits()==Color.WHITE.toIntBits())continue;
                shaper.getShaper().setColor(map[i][j]);
                shaper.getShaper().filledRectangle(j*tileSize,i*tileSize,tileSize,tileSize);
            }
        }


    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void dispose() {
        mapImage.dispose();
    }
}
