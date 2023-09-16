package gameengine.loaders;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

public class TilesetLoader {

	public static Tileset loadTileset(String filePath, BufferedImage tilesetImage) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		
		bufferedReader.readLine(); //header
		int tileSize = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		
		Tileset tileset = new Tileset();
		
		String line = bufferedReader.readLine();
		while (line != null) {
			
			String[] values = line.split(",");
			int x = Integer.parseInt(values[1]);
			int y = Integer.parseInt(values[2]);
			tileset.addImage(values[0], tilesetImage.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize));
			
			line = bufferedReader.readLine();
		}
		
		bufferedReader.close();
		
		return tileset;
	}
	
}
