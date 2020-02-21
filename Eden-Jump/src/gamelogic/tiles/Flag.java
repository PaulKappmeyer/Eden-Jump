package gamelogic.tiles;

import java.awt.image.BufferedImage;

import gameengine.hitbox.RectHitbox;
import gamelogic.Main;

public class Flag extends Tile{

	public Flag(float x, float y, int size, BufferedImage image) {
		super(x, y, size, image, false);
		hitbox = new RectHitbox(x, y, 30, 0, size-30, size);
	}

	@Override
	public void update(float tslf) {
		if(hitbox.isIntersecting(Main.player.getHitbox())) Main.win();
	}
	
}
