package gamelogic.tiledMap;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.hitbox.RectHitbox;
import gamelogic.GameResources;

public class Spikes extends Tile{

	public static final int HORIZONTAL_DOWNWARDS = 0;
	public static final int HORIZONTAL_UPWARDS = 1;
	public static final int VERTICAL_LEFTWARDS = 2;
	public static final int VERTICAL_RIGHTWARDS = 3;
	
	private BufferedImage image;
	
	public Spikes(float x, float y, int size, int orientation) {
		super(x, y, size);
		switch (orientation) {
		case HORIZONTAL_UPWARDS:
			hitbox = new RectHitbox(x , y, 0, 50, 100, 100);
			image = GameResources.spikes_upwards;
			break;
		case HORIZONTAL_DOWNWARDS:
			hitbox = new RectHitbox(x , y, 0, 0, 100, 50);
			image = GameResources.spikes_downwards;
			break;
		case VERTICAL_LEFTWARDS:
			hitbox = new RectHitbox(x , y, 50, 0, 100, 100);
			image = GameResources.spikes_leftwards;
			break;
		case VERTICAL_RIGHTWARDS:
			hitbox = new RectHitbox(x , y, 50, 0, 50, 100);
			image = GameResources.spikes_rightwards;
			break;

		default:
			break;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, (int)position.x, (int)position.y, size, size, null);
		
		hitbox.draw(g);
	}
	
}
