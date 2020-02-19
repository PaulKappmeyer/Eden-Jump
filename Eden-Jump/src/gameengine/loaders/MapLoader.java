package gameengine.loaders;

import java.io.BufferedReader;
import java.io.FileReader;

import gamelogic.Leveldata;
import gamelogic.tiledMap.Map;
import gamelogic.tiledMap.SolidTile;
import gamelogic.tiledMap.Spikes;
import gamelogic.tiledMap.Tile;

public class MapLoader {

	public static Leveldata loadMap(String filePath) throws Exception {
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
				else if(values[x].equals("2")) tiles[x][y] = new Spikes(x*tileSize, y*tileSize, tileSize, Spikes.HORIZONTAL_DOWNWARDS);
				else if(values[x].equals("3")) tiles[x][y] = new Spikes(x*tileSize, y*tileSize, tileSize, Spikes.HORIZONTAL_UPWARDS);
				else if(values[x].equals("4")) tiles[x][y] = new Spikes(x*tileSize, y*tileSize, tileSize, Spikes.VERTICAL_LEFTWARDS);
				else if(values[x].equals("5")) tiles[x][y] = new Spikes(x*tileSize, y*tileSize, tileSize, Spikes.VERTICAL_RIGHTWARDS);
				else tiles[x][y] = new Tile(x*tileSize, y*tileSize, tileSize);
			}
		}
		String[] playerPos = bufferedReader.readLine().split("=")[1].split(",");
		int playerX = Integer.parseInt(playerPos[0]);
		int playerY = Integer.parseInt(playerPos[1]);
		bufferedReader.close();
		
		Map map = new Map(width, height, tileSize, tiles);
		
		Leveldata leveldata = new Leveldata(map, playerX, playerY);
		
		return leveldata;
	}
	
}
