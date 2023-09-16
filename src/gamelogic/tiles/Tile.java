package gamelogic.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.hitbox.RectHitbox;
import gameengine.maths.Vector2D;
import gamelogic.level.Level;

public class Tile {

	protected Vector2D position;
	protected int size;
	protected RectHitbox hitbox;
	protected BufferedImage image;
	protected boolean solid;
	protected Level level;
	
	public Tile(float x, float y, int size, BufferedImage image, boolean solid, Level level) {
		this.position = new Vector2D(x, y);
		this.size = size;
		this.image = image;
		this.solid = solid;
		this.level = level;
	}
	
	public void update (float tslf) {};
	
	public void draw (Graphics g) {
		if(image != null) g.drawImage(image, (int)position.x, (int)position.y, size, size, null);
		
		if(hitbox != null) hitbox.draw(g);		
	}
	
	
	
	//------------------------------------Getters
	public boolean isSolid() {
		return solid;
	}
	
	public RectHitbox getHitbox() {
		return hitbox;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public int getSize() {
		return size;
	}
}
