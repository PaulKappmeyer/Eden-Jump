package gamelogic;

import java.awt.image.BufferedImage;

import gameengine.loaders.ImageLoader;
import gameengine.loaders.Tileset;
import gameengine.loaders.TilesetLoader;

public final class GameResources {

	public static Tileset tileset;
	
	public static BufferedImage enemy;
	
	public static void load() {
		try {
			tileset = TilesetLoader.loadTileset("./Eden-Jump/gfx/tileset.txt", ImageLoader.loadImage("./Eden-Jump/gfx/tileset.png"));
			
			enemy = ImageLoader.loadImage("./Eden-Jump/gfx/enemy.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
