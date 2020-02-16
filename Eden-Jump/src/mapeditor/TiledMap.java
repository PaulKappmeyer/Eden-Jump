package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import gameengine.input.MouseInputManager;

class TiledMap {
	public static final Color MOUSE_OVER = new Color(0, 255, 0, 100);
	public static final int AIR = 0;
	public static final int SOLID = 1;

	private Tile[][] tiles;
	private int width; //width of the map in number of tiles
	private int height; //height of the map in number of tiles
	private int size; //the size of one tile;

	private Tile mouseOver;

	public TiledMap(int width, int height, int tileSize) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = new Tile(x * tileSize, y * tileSize, tileSize);
			}
		}
		this.size = tileSize;
	}

	public void update(float tslf) {
		float mouseX = MouseInputManager.getMouseX();
		float mouseY = MouseInputManager.getMouseY();

		mouseOver = null;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				float tileX = x * size - MapEditor.camera.getX();
				float tileY = y * size - MapEditor.camera.getY();
				if(mouseX >= tileX && mouseX <= tileX + size && mouseY >= tileY && mouseY <= tileY + size) {
					mouseOver = tiles[x][y];
					break;
				}
			}
		}
		if(mouseOver != null) {
			if(MouseInputManager.isButtonDown(MouseEvent.BUTTON1)) {
				mouseOver.setValue(SOLID);
			}
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile tile = tiles[x][y];
				if(tile.getValue() == SOLID) g.fillRect(tile.getX(), tile.getY(), size, size);
			}
		}

		if(mouseOver != null) {
			g.setColor(MOUSE_OVER);
			g.fillRect(mouseOver.getX(), mouseOver.getY(), size, size);
		}

		drawOutlines(g);
	}

	public void drawOutlines(Graphics g) {
		g.setColor(Color.BLACK);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y].drawOutline(g);
			}
		}
	}
}
