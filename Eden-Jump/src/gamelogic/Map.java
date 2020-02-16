package gamelogic;

import java.awt.Graphics;

import gameengine.GameObject;
import gameengine.hitbox.RectHitbox;

public class Map {
	
	int width; //size in number of tiles;
	int height; //size in number of tiles;
	private RectHitbox[][] obstacles;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		obstacles = new RectHitbox[width][height];
	}
	
	public void init() {
		obstacles = new RectHitbox[width][height];
		int size = 100;
		int x = 0;
		int y = obstacles[0].length-1;
		for (x = 0; x < obstacles.length; x++) {
			obstacles[x][y] = new RectHitbox(new GameObject(x*size, y*size), 0, 0, size, size);
		}
		x = 0;
		for (y = 0; y < obstacles[x].length; y++) {
			obstacles[x][y] = new RectHitbox(new GameObject(x*size, y*size), 0, 0, size, size);
		}
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < obstacles.length; i++) {
			for (int j = 0; j < obstacles[i].length; j++) {
				RectHitbox ob = obstacles[i][j];
				if(ob == null) continue;
				if(Main.camera.isVisibleOnCamera(ob.getX(), ob.getY(), ob.getWidth(), ob.getHeight())) ob.draw(g);
			}
		}
	}
	
	//-----------------------------------------------------Getters
	public RectHitbox[][] getObstacles() {
		return obstacles;
	}
}
