package gamelogic;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.maths.Vector2D;

public class ScreenTransition {

	private boolean isActive = false;
	private boolean isActivating = false;
	private boolean isDeactivating = false;
	private Vector2D position;
	private float width;
	private float height;
	private float velocity = Main.SCREEN_WIDTH * 4;

	public ScreenTransition() {
		this.position = new Vector2D();
		this.width = 0;
		this.height = Main.SCREEN_HEIGHT;
	}

	public void update(float tslf) {
		if(isActive) {
			//Activating
			if(isActivating) {
				width += velocity * tslf;
				if(Main.SCREEN_WIDTH < width) {
					width = Main.SCREEN_WIDTH;
					isActivating = false;
				}
			}
			//Deactivating
			else if(isDeactivating) {
				position.x += velocity * tslf;
				if(Main.SCREEN_WIDTH < position.x) {
					width = 0;
					position.x = 0;
					
					isDeactivating = false;
					isActive = false;
				}
			}
		}
	}

	public void draw(Graphics g) {
		if(isActive) {
			g.setColor(Color.BLACK);
			g.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
		}
	}

	public void activate() {
		isActive = true;
		isActivating = true;
	}

	public void deactivate() {
		isDeactivating = true;
	}

	//---------------------------------------Getters
	public boolean isActive() {
		return isActive;
	}

	public boolean isActivating() {
		return isActivating;
	}

	public boolean isDeactivating() {
		return isDeactivating;
	}
}
