package mapeditor;

import java.awt.Color;

class PaletteTile {

	private String name;
	private int value;
	private Color color;
	
	public PaletteTile(String name, int value, Color color) {
		this.name = name;
		this.value = value;
		this.color = color;
	}
	
	
	//-----------------------------------Getters
	public String getName() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	public Color getColor() {
		return color;
	}
}
