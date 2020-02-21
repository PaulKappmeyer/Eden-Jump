package gamelogic.tiles;

import java.awt.image.BufferedImage;

import gameengine.hitbox.RectHitbox;

public class SolidTile extends Tile{
	
	public SolidTile(float x, float y, int size, BufferedImage image) {
		super(x, y, size, image, true);
		this.hitbox = new RectHitbox(x , y, 0, 10, size, size);
	}
}
