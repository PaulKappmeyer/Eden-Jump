package gamelogic.tiles;

import gameengine.hitbox.RectHitbox;
import gamelogic.GameResources;

public class Spikes extends Tile{

	public static final int HORIZONTAL_DOWNWARDS = 0;
	public static final int HORIZONTAL_UPWARDS = 1;
	public static final int VERTICAL_LEFTWARDS = 2;
	public static final int VERTICAL_RIGHTWARDS = 3;
	
	private int offsetX = 25;
	private int offsetY = 70;
	
	public Spikes(float x, float y, int size, int orientation) {
		super(x, y, size, null, true);
		switch (orientation) {
		case HORIZONTAL_UPWARDS:
			hitbox = new RectHitbox(x , y, offsetX, offsetY, size - offsetX, size);
			image = GameResources.spikes_upwards;
			break;
		case HORIZONTAL_DOWNWARDS:
			hitbox = new RectHitbox(x , y, offsetX, 0, size - offsetX, size - offsetY);
			image = GameResources.spikes_downwards;
			break;
		case VERTICAL_LEFTWARDS:
			hitbox = new RectHitbox(x , y, offsetY, offsetX, size, size - offsetX);
			image = GameResources.spikes_leftwards;
			break;
		case VERTICAL_RIGHTWARDS:
			hitbox = new RectHitbox(x , y, 0, offsetX, size - offsetY, size - offsetX);
			image = GameResources.spikes_rightwards;
			break;

		default:
			break;
		}
	}
}
