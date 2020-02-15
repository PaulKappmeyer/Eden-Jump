package gameengine;

import java.awt.Graphics;

import gameengine.maths.Vector2D;

public abstract class GameObject {

	protected Vector2D position;
	
	public GameObject() {
		this.position = new Vector2D();
	}
	
	public GameObject(float x, float y) {
		this.position = new Vector2D(x, y);
	}
	
	public abstract void update (float tslf);
	
	public abstract void draw (Graphics g);
	
	
	//------------------------------------Getters
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
}
