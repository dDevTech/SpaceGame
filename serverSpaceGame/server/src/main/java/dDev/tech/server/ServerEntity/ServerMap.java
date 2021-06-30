package dDev.tech.server.ServerEntity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dDev.tech.map.Map;
import dDev.tech.map.SettingsGame;
import dDev.tech.map.SpaceWorld;
import dDev.tech.map.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ServerMap extends Map {
    private BufferedImage serverImage;
    public ServerMap(String imagePath,SpaceWorld space,boolean server){
        super();

        try {
            serverImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        map = new Tile[serverImage.getHeight()][serverImage.getWidth()];
        for(int i=0;i<serverImage.getHeight();i++){
            for(int j=0;j<serverImage.getWidth();j++){
                Color c=new Color(serverImage.getRGB(i,j));
                Tile.TILE_TYPE type= Tile.TILE_TYPE.BLOCK;
                if(c.toIntBits()==Color.WHITE.toIntBits())type= Tile.TILE_TYPE.NONE;
                Tile tile = new Tile(j* SettingsGame.tileSize,i*SettingsGame.tileSize,SettingsGame.tileSize,c, type,space,camera);
                map[i][j]= tile;
            }
        }


    }
}
