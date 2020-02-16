package gameengine.hitbox;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.GameObject;
import gameengine.maths.Vector2D;

public class RectHitbox extends Hitbox{

	private GameObject object; //This is the object the hitbox is attached to;
	private Vector2D position; 
	private float offsetX;
	private float offsetY;
	private int width;
	private int height;
	
	public RectHitbox(GameObject object, float offsetX, float offsetY, int width, int height) {
		this.object = object;
		this.position = new Vector2D(object.getX() + offsetX, object.getY() + offsetY);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void update() {
		position.x = object.getX() + offsetX;
		position.y = object.getY() + offsetY;
	}
	
	@Override
	public void draw(Graphics g) {
		if(!SHOW_HITBOXES) return;
		g.setColor(Color.GREEN);
		g.drawRect((int)position.x, (int)position.y, width, height);
	}
	
	//-------------------------------------------------------------Getters
	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
