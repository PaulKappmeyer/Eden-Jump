package gameengine.loaders;

import java.io.BufferedReader;
import java.io.FileReader;

import gameengine.GameObject;
import gameengine.hitbox.RectHitbox;
import gamelogic.Map;

public class MapLoader {

	public static Map loadMap(String filePath) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		
		int width = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int height = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int tileSize = Integer.parseInt(bufferedReader.readLine().split("=")[1]) * 2;
		RectHitbox[][] obstacles = new RectHitbox[width][height];
		
		for (int y = 0; y < height; y++) {
			String[] values = bufferedReader.readLine().split(",");
			for (int x = 0; x < width; x++) {
				if(values[x].equals("0")) continue;
				else if(values[x].equals("1")) obstacles[x][y] = new RectHitbox(new GameObject(x*tileSize, y*tileSize, tileSize, tileSize), 0, 0, tileSize, tileSize);
			}
		}
		bufferedReader.close();
		
		Map map = new Map(width, height, obstacles);
		
		return map;
	}
	
}
