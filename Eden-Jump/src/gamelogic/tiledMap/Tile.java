package gamelogic.tiledMap;

import java.awt.Graphics;

import gameengine.maths.Vector2D;

public class Tile {

	protected Vector2D position;
	protected int size;
	
	public Tile() {
		this.position = new Vector2D();
	}
	
	public Tile(float x, float y) {
		this.position = new Vector2D(x, y);
	}
	
	public Tile(float x, float y, int size) {
		this.position = new Vector2D(x, y);
		this.size = size;
	}
	
	public void update (float tslf) {};
	
	public void draw (Graphics g) {};
	
	
	//------------------------------------Getters
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
