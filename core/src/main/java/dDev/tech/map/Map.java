package dDev.tech.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import dDev.tech.tools.Shaper;

public class Map extends Actor implements Disposable {

    private Pixmap mapImage;
    private Tile[][]map;
    private Shaper shaper;
    private int tileSize =50;
    private float perspectiveInclination=0.25f;
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
                Tile.TILE_TYPE type= Tile.TILE_TYPE.BLOCK;
                if(c.toIntBits()==Color.WHITE.toIntBits())type= Tile.TILE_TYPE.NONE;
                Tile tile = new Tile(j*tileSize,i*tileSize,tileSize,c, type);
                map[i][j]= tile;
            }
        }
        prepare3d();

        Gdx.app.log("MAP","Map loaded");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(shaper==null)shaper= new Shaper(batch);

        //Calculate matrix array coordinates in camera bounds
        float xCorner=(camera.position.x-camera.viewportWidth/2)/tileSize;
        float yCorner=(camera.position.y-camera.viewportHeight/2)/tileSize;
        float xFinal=2+xCorner+camera.viewportWidth/tileSize;
        float yFinal=2+yCorner+camera.viewportHeight/tileSize;

        shaper.getShaper().setColor(Color.BLACK);
        shaper.getShaper().filledRectangle(camera.position.x-camera.viewportWidth/2,camera.position.y-camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);

        //Get only tiles that are visible to camera viewport
        if(xCorner<0)xCorner=0;
        if(yCorner<0)yCorner=0;
        if(xFinal>map[0].length) xFinal = map[0].length;
        if(yFinal>map.length) yFinal = map.length;


        //3d effect
        for(int y=(int)(yFinal-1);y>=(int)yCorner;y--){
            for(int x=(int)(xFinal-1);x>=(int)xCorner;x--){
                if(map[y][x].getType()== Tile.TILE_TYPE.NONE)continue;
                Color color =new Color(map[y][x].getColor());
                color.a=0.5f;
                shaper.getShaper().setColor(color);
                for(Vector2[]vertices:map[y][x].getTriangles()){
                    shaper.getShaper().filledTriangle(vertices[0],vertices[1],vertices[2]);
                }

            }
        }

        //normal block
        for(int y=(int)yCorner;y<yFinal;y++){
            for(int x=(int)xCorner;x<xFinal;x++){
                if(map[y][x].getType()== Tile.TILE_TYPE.NONE)continue;
                Color c =map[y][x].getColor();
                shaper.getShaper().setColor(c);
                shaper.getShaper().filledRectangle(x*tileSize,y*tileSize,tileSize,tileSize);

                //draw lines
                c = new Color(Color.WHITE);
                c.a=0.6f;
                shaper.getShaper().setColor(c);

                for(Vector2[]vertices:map[y][x].getLines()){
                    shaper.getShaper().line(vertices[0],vertices[1]);
                }


            }
        }


    }
    private void prepare3d(){
        //3d effect
        for(int i=map.length-1;i>=0;i--) {
            for (int j = map[0].length-1; j >= 0; j--) {
                if (map[i][j].getType() == Tile.TILE_TYPE.NONE) continue;

                if (j + 1 < map[0].length && map[i][j + 1].getType() == Tile.TILE_TYPE.BLOCK && i - 1 >= 0 && map[i - 1][j].getType() == Tile.TILE_TYPE.BLOCK) {
                    create3dBox(i, j, false, false);
                } else if (j + 1 < map[0].length && map[i][j + 1].getType() == Tile.TILE_TYPE.BLOCK) {
                    create3dBox(i, j, false, true);
                } else if (i - 1 >= 0 && map[i - 1][j].getType() == Tile.TILE_TYPE.BLOCK) {
                    create3dBox(i, j, true, false);
                } else {
                    create3dBox(i, j, true, true);
                }

            }
        }
    }
    private void create3dBox(int y,int x,boolean  drawRight,boolean drawBottom){

        int tileX = (x)*tileSize;
        int tileY = (y)*tileSize;
        int nextTileX = (x+1)*tileSize;
        int nextTileY = (y+1)*tileSize;
        if(drawRight) {
            map[y][x].addTriangle(new Vector2(nextTileX, tileY),new Vector2(nextTileX + perspectiveInclination * tileSize , nextTileY - perspectiveInclination * tileSize), new Vector2(nextTileX + perspectiveInclination * tileSize, tileY - perspectiveInclination * tileSize));
            map[y][x].addTriangle(new Vector2(nextTileX, tileY),
                    new Vector2(nextTileX, nextTileY),
                    new Vector2(nextTileX + perspectiveInclination * tileSize, nextTileY - perspectiveInclination * tileSize));
        }

        if(drawBottom) {
            map[y][x].addTriangle(new Vector2(nextTileX + perspectiveInclination * tileSize, tileY - perspectiveInclination * tileSize),
                    new Vector2(nextTileX, tileY),
                    new Vector2(tileX + perspectiveInclination * tileSize, tileY - perspectiveInclination * tileSize));
            map[y][x].addTriangle(new Vector2(tileX, tileY),
                    new Vector2(nextTileX, tileY),
                    new Vector2(tileX + perspectiveInclination * tileSize, tileY - perspectiveInclination * tileSize));
        }

        if( y-1>=0 && map[y-1][x].getType()== Tile.TILE_TYPE.NONE){
            map[y][x].addLine(new Vector2(x*tileSize,y*tileSize),new Vector2((x+1)*tileSize,y*tileSize));
        }
        if( y+1<map.length && map[y+1][x].getType()== Tile.TILE_TYPE.NONE){
            map[y][x].addLine(new Vector2(x*tileSize,(y+1)*tileSize),new Vector2((x+1)*tileSize,(y+1)*tileSize));
        }

        if( x+1<map[0].length && map[y][x+1].getType()== Tile.TILE_TYPE.NONE){
            map[y][x].addLine(new Vector2((x+1)*tileSize,(y)*tileSize),new Vector2((x+1)*tileSize,(y+1)*tileSize));
        }
        if( x-1>=0 && map[y][x-1].getType()== Tile.TILE_TYPE.NONE){
            map[y][x].addLine(new Vector2(x*tileSize,(y)*tileSize),new Vector2(x*tileSize,(y+1)*tileSize));
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
