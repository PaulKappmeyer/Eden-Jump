package mapeditor;

import java.awt.Graphics;

class Tile {
	private int x;
	private int y;
	private int size;
	private int value;
	
	public Tile(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.value = TiledMap.AIR;
	}
	
	public void drawOutline(Graphics g) {
		g.drawRect(x, y, size, size);
	}
	
	//-------------------------------------Getters , Setters
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
