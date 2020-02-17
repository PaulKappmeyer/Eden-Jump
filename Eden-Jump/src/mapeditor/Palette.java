package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import gameengine.input.MouseInputManager;
import gameengine.maths.Vector2D;

class Palette {

	private Vector2D position;
	private PaletteTile[] paletteTiles;
	private int tileSize = 50;
	private int tilesPerRow = 5;

	private int selectedIndex = 0;

	public Palette(float x, float y) {
		this.position = new Vector2D(x, y);
		this.paletteTiles = new PaletteTile[35];
		paletteTiles[0] = new PaletteTile("Air", 0, Color.WHITE);
		for (int i = 1; i < paletteTiles.length; i++) {
			paletteTiles[i] = new PaletteTile("Solid", 1, Color.LIGHT_GRAY);
		}
	}

	public void update(float tslf) {
		float mouseX = MouseInputManager.getMouseX();
		float mouseY = MouseInputManager.getMouseY();

		//Highlight the tile the mouse is on
		if(position.x < mouseX && position.y < mouseY) {
			
			int tileX = (int) ((mouseX - position.x) / tileSize);
			int tileY = (int) ((mouseY - position.y) / tileSize);
			int index = tileY * tilesPerRow + tileX;
			if(0 <= index && index < paletteTiles.length) {
				if(MouseInputManager.isButtonDown(MouseEvent.BUTTON1)) {
					selectedIndex = index;
				}
			}
		}
	}

	public void draw(Graphics g) {
		g.translate((int)position.x, (int)position.y);
		//fill
		for (int i = 0; i < paletteTiles.length; i++) {
			g.setColor(paletteTiles[i].getColor());
			g.fillRect(i % tilesPerRow * tileSize, (int)(i/tilesPerRow) * tileSize, tileSize, tileSize);
		}

		//outlines
		g.setColor(Color.BLACK);
		for (int i = 0; i < paletteTiles.length; i++) {
			g.drawRect(i % tilesPerRow * tileSize, (int)(i/tilesPerRow) * tileSize, tileSize, tileSize);
		}

		//selected
		g.setColor(Color.RED);
		g.drawRect(selectedIndex % tilesPerRow * tileSize, (int)(selectedIndex/tilesPerRow) * tileSize, tileSize, tileSize);
	}
	
	//--------------------------------------Getters
	public PaletteTile getSelectedPaletteTile() {
		return paletteTiles[selectedIndex];
	}
}