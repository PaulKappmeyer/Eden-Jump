package gamelogic.tiledMap;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.hitbox.RectHitbox;

public class SolidTile extends Tile{

	private RectHitbox hitbox;
	
	public SolidTile(float x, float y, int size) {
		super(x, y, size);
		this.hitbox = new RectHitbox(x , y, 0, 20, size, size+20);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int)position.x, (int)position.y, size, size);
		g.setColor(Color.BLACK);
		g.drawRect((int)position.x, (int)position.y, size, size);
		
		hitbox.draw(g);
	}
	
	//-----------------------------------Getters
	public RectHitbox getHitbox() {
		return hitbox;
	}
}
