package dDev.tech.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import dDev.tech.tools.Shaper;

public class Map extends Actor implements Disposable {

    private Pixmap mapImage;
    private Tile[][]map;
    private Shaper shaper;
    private int tileSize =50;
    private OrthographicCamera camera;
    public Map(String imagePath,OrthographicCamera camera){
        super();
        this.camera = camera;
        Gdx.app.log("MAP","Loading map...");
        mapImage = new Pixmap(Gdx.files.internal(imagePath));
        map = new Tile[mapImage.getHeight()][mapImage.getWidth()];
        for(int i=0;i<mapImage.getHeight();i++){
            for(int j=0;j<mapImage.getWidth();j++){
                Color c=new Color(mapImage.getPixel(i,j));
                Tile tile = new Tile(j*tileSize,i*tileSize,tileSize,c);
                map[i][j]= tile;
            }
        }


        Gdx.app.log("MAP","Map loaded");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(shaper==null)shaper= new Shaper(batch);

        //Calculate matrix array coordinates in camera bounds
        float xCorner=(camera.position.x-camera.viewportWidth/2)/tileSize;
        float yCorner=(camera.position.y-camera.viewportHeight/2)/tileSize;
        float xFinal=xCorner+camera.viewportWidth/tileSize;
        float yFinal=yCorner+camera.viewportHeight/tileSize;

        //Get only tiles that are visible to camera viewport
        if(xCorner<0)xCorner=0;
        if(yCorner<0)yCorner=0;
        if(xFinal>map[0].length) xFinal = map[0].length;
        if(yFinal>map.length) yFinal = map.length;

        for(int i=(int)yCorner;i<yFinal;i++){
            for(int j=(int)xCorner;j<xFinal;j++){
                if(map[i][j].getColor().toIntBits()==Color.WHITE.toIntBits())map[i][j].setColor(Color.BLACK);//FIXME continue;
                shaper.getShaper().setColor(map[i][j].getColor());
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
