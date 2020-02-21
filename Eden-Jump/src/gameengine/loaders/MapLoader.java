package gameengine.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import gamelogic.GameResources;
import gamelogic.Leveldata;
import gamelogic.enemies.Enemy;
import gamelogic.tiledMap.Map;
import gamelogic.tiles.Flag;
import gamelogic.tiles.SolidTile;
import gamelogic.tiles.Spikes;
import gamelogic.tiles.Tile;

public class MapLoader {

	public static Leveldata loadMap(String filePath) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		
		int width = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int height = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int tileSize = Integer.parseInt(bufferedReader.readLine().split("=")[1]) * 2;
		Tile[][] tiles = new Tile[width][height];
		ArrayList<Enemy> enemiesList = new ArrayList<>();
		
		for (int y = 0; y < height; y++) {
			int yPosition = y*tileSize;
			
			String[] values = bufferedReader.readLine().split(",");
			for (int x = 0; x < width; x++) {
				int xPosition = x*tileSize;
				
				tiles[x][y] = new Tile(xPosition, yPosition, tileSize, null, false);
				
				if(values[x].equals("0")) tiles[x][y] = new Tile(xPosition, yPosition, tileSize, null, false); //Air
				else if(values[x].equals("1")) tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, GameResources.solid);
				else if(values[x].equals("2")) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.HORIZONTAL_DOWNWARDS);
				else if(values[x].equals("3")) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.HORIZONTAL_UPWARDS);
				else if(values[x].equals("4")) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.VERTICAL_LEFTWARDS);
				else if(values[x].equals("5")) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.VERTICAL_RIGHTWARDS);
				else if(values[x].equals("6")) tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, GameResources.dirt);
				else if(values[x].equals("7")) tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, GameResources.gras);
				else if(values[x].equals("8")) enemiesList.add(new Enemy(xPosition, yPosition)); //TODO: objects vs tiles
				else if(values[x].equals("9")) tiles[x][y] = new Flag(xPosition, yPosition, tileSize, GameResources.flag);
			}
		}
		String[] playerPos = bufferedReader.readLine().split("=")[1].split(",");
		int playerX = Integer.parseInt(playerPos[0]);
		int playerY = Integer.parseInt(playerPos[1]);
		bufferedReader.close();
		
		Map map = new Map(width, height, tileSize, tiles);
		
		Enemy[] enemies = new Enemy[enemiesList.size()];
		enemies = enemiesList.toArray(enemies);
		
		Leveldata leveldata = new Leveldata(map, playerX, playerY, enemies);
		
		return leveldata;
	}
	
}
