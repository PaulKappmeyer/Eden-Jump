package gamelogic.tiledMap;

import java.awt.Graphics;

import gamelogic.Main;

public class Map {
	
	private int width; //size in number of tiles;
	private int height; //size in number of tiles;
	private Tile[][] tiles;
	
	public Map(int width, int height, Tile[][]tiles) {
		this.width = width;
		this.height = height;
		this.tiles = tiles;
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile tile = tiles[i][j];
				if(tile == null) continue;
				if(Main.camera.isVisibleOnCamera(tile.getX(), tile.getY(), tile.getSize(), tile.getSize())) tile.draw(g);
			}
		}
	}
	
	//-----------------------------------------------------Getters
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
}
