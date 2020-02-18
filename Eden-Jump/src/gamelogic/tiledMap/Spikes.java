package gamelogic.tiledMap;

import java.awt.Graphics;

import gameengine.hitbox.RectHitbox;

public class Spikes extends Tile{

	public static final int HORIZONTAL_DOWNWARDS = 0;
	public static final int HORIZONTAL_UPWARDS = 1;
	public static final int VERTICAL_LEFTWARDS = 2;
	public static final int VERTICAL_RIGHTWARDS = 3;
	
	public Spikes(float x, float y, int orientation) {
		super(x, y);
		switch (orientation) {
		case HORIZONTAL_UPWARDS:
			hitbox = new RectHitbox(x , y, 0, 50, 100, 100);
			break;
		case HORIZONTAL_DOWNWARDS:
			hitbox = new RectHitbox(x , y, 0, 0, 100, 50);
			break;
		case VERTICAL_LEFTWARDS:
			hitbox = new RectHitbox(x , y, 50, 0, 100, 100);
			break;
		case VERTICAL_RIGHTWARDS:
			hitbox = new RectHitbox(x , y, 50, 0, 50, 100);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		hitbox.draw(g);
	}
	
}
