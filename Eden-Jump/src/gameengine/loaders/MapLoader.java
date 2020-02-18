package gameengine.loaders;

import java.io.BufferedReader;
import java.io.FileReader;

import gamelogic.tiledMap.Map;
import gamelogic.tiledMap.SolidTile;
import gamelogic.tiledMap.Tile;

public class MapLoader {

	public static Map loadMap(String filePath) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		
		int width = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int height = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int tileSize = Integer.parseInt(bufferedReader.readLine().split("=")[1]) * 2;
		Tile[][] tiles = new Tile[width][height];
		
		for (int y = 0; y < height; y++) {
			String[] values = bufferedReader.readLine().split(",");
			for (int x = 0; x < width; x++) {
				if(values[x].equals("0")) tiles[x][y] = new Tile(x*tileSize, y*tileSize, tileSize);
				else if(values[x].equals("1")) tiles[x][y] = new SolidTile(x*tileSize, y*tileSize, tileSize);
			}
		}
		bufferedReader.close();
		
		Map map = new Map(width, height, tileSize, tiles);
		
		return map;
	}
	
}
