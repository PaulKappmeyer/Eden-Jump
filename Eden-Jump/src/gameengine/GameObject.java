package gameengine;

import java.awt.Graphics;

import gameengine.maths.Vector2D;

public abstract class GameObject {

	private Vector2D position;
	
	public abstract void update (float tslf);
	
	public abstract void draw (Graphics graphics);
	
	
	//------------------------------------Getters
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
}
