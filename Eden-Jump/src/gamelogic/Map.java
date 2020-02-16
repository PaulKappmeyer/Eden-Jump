package gamelogic;

import java.awt.Graphics;

import gameengine.hitbox.RectHitbox;

public class Map {
	
	int width; //size in number of tiles;
	int height; //size in number of tiles;
	private RectHitbox[][] obstacles;
	
	public Map(int width, int height, RectHitbox[][]obstacles) {
		this.width = width;
		this.height = height;
		this.obstacles = obstacles;
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
