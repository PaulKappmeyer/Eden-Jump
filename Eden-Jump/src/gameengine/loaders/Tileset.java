package gameengine.loaders;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Tileset {
	private Map<String, BufferedImage> images;
	
	public Tileset() {
		images = new HashMap<String, BufferedImage>();
	}
	
	public void addImage(String name, BufferedImage image) {
		images.put(name, image);
	}
	
	public BufferedImage getImage(String name) {
		return images.get(name);
	}
	
	public Map<String, BufferedImage> getImages() {
		return images;
	}
}
