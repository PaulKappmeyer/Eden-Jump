package mapeditor;

import java.awt.Color;
import java.awt.Graphics;

class EditorTile {
	private int x;
	private int y;
	private int size;
	private int value;
	private Color color;
	
	public EditorTile(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.value = 0;
		this.color = Color.WHITE;
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
	
	public int getSize() {
		return size;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
