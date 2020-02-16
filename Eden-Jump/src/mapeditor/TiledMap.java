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
	private int tileSize; //the size of one tile;

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
		this.tileSize = tileSize;
	}

	public void update(float tslf) {
		float mouseX = MouseInputManager.getMouseX();
		float mouseY = MouseInputManager.getMouseY();

		mouseOver = null;
		int tileX = (int) ((mouseX + MapEditor.camera.getX()) / tileSize);
		int tileY = (int) ((mouseY + MapEditor.camera.getY()) / tileSize);
		if(tileX >= 0 && tileX < width && tileY >= 0 && tileY < height) mouseOver = tiles[tileX][tileY];

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
				if(tile.getValue() == SOLID) g.fillRect(tile.getX(), tile.getY(), tileSize, tileSize);
			}
		}

		if(mouseOver != null) {
			g.setColor(MOUSE_OVER);
			g.fillRect(mouseOver.getX(), mouseOver.getY(), tileSize, tileSize);
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
	
	//------------------------------------------Getters
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
}
