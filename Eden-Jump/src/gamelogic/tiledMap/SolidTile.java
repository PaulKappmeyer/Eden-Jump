package gamelogic.tiledMap;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.hitbox.RectHitbox;

public class SolidTile extends Tile{
	
	private BufferedImage image;
	
	public SolidTile(float x, float y, int size, BufferedImage image) {
		super(x, y, size);
		this.hitbox = new RectHitbox(x , y, 0, 10, size, size);
		this.image = image;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, (int)position.x, (int)position.y, size, size, null);
		
		hitbox.draw(g);
	}
}
